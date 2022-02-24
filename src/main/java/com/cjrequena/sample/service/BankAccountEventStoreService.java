package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 *
 */
@Slf4j
@Service
public class BankAccountEventStoreService {

  private AggregateRepository aggregateRepository;
  private BankAccountEventRepository bankAccountEventRepository;

  @Autowired
  public BankAccountEventStoreService(BankAccountEventRepository bankAccountEventRepository, AggregateRepository aggregateRepository) {
    this.bankAccountEventRepository = bankAccountEventRepository;
    this.aggregateRepository = aggregateRepository;
  }

  @Transactional
  public void appendEvent(EventEntity event) {
    Objects.requireNonNull(event);
    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(Constants.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());

    if (this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())) {
      // Check aggregate version.
      if (this.aggregateRepository.checkAggregateVersion(aggregateEntity)) {
        // Increment aggregate version
        aggregateEntity.setVersion(aggregateEntity.getVersion() + 1);
        event.setVersion(aggregateEntity.getVersion());
      } else {
        log.debug(
          "Optimistic concurrency control error in aggregate {}: actual version doesn't match expected version {}",
          aggregateEntity.getId(),
          aggregateEntity.getVersion());
        throw new RuntimeException("Optimistic concurrency control error in aggregate");
      }
    }

    // Create or Update the Aggregate
    this.aggregateRepository.save(aggregateEntity);
    // Append new Event
    this.bankAccountEventRepository.save(event);
  }

  @Transactional
  List<EventEntity> retrieveEvents(UUID aggregateId) {
    return this.bankAccountEventRepository.retrieveEvents(aggregateId);
  }

}
