package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.exception.service.AggregateNotFoundServiceException;
import com.cjrequena.sample.exception.service.DuplicatedAggregateServiceException;
import com.cjrequena.sample.exception.service.OptimisticConcurrencyAggregateVersionServiceException;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
@Transactional
public class BankAccountEventStoreService {

  private AggregateRepository aggregateRepository;
  private BankAccountEventRepository bankAccountEventRepository;
  private BankAccountMapper bankAccountMapper;

  @Autowired
  public BankAccountEventStoreService(BankAccountEventRepository bankAccountEventRepository, AggregateRepository aggregateRepository, BankAccountMapper bankAccountMapper) {
    this.bankAccountEventRepository = bankAccountEventRepository;
    this.aggregateRepository = aggregateRepository;
    this.bankAccountMapper = bankAccountMapper;
  }

  public void appendEvent(Event event) throws OptimisticConcurrencyAggregateVersionServiceException, DuplicatedAggregateServiceException, AggregateNotFoundServiceException {
    Objects.requireNonNull(event);
    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(Constants.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());
    EventEntity eventEntity = this.bankAccountMapper.toEntity(event);

    // Append new Event
    switch (event.getType()) {
      case BANK_ACCOUNT_CREATED_EVENT_V1:
        if (this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())){
          throw new DuplicatedAggregateServiceException("Aggregate :: " + aggregateEntity.getId() + " :: Already Exists");
        }
        // Create the Aggregate
        this.aggregateRepository.save(aggregateEntity);
        break;
      case BANK_ACCOUNT_DEPOSITED_EVENT_V1:
      case BANK_ACCOUNT_WITHDRAWN_EVENT_V1:
        //Check and increment the aggregate version
        this.checkAndIncrementAggregateVersion(aggregateEntity);
        event.setVersion(aggregateEntity.getVersion());
        break;
    }
    this.bankAccountEventRepository.save(this.bankAccountMapper.toEntity(event));
  }

  @Transactional(readOnly = true)
  List<Event> retrieveEventsByAggregateId(UUID aggregateId) {
    return this.bankAccountEventRepository.retrieveEventsByAggregateId(aggregateId).stream().map(entity -> bankAccountMapper.toEvent(entity)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  List<Event> retrieveEventsAfterOffset(Integer offset) {
    return this.bankAccountEventRepository.retrieveEventsAfterOffset(offset).stream().map(entity -> bankAccountMapper.toEvent(entity)).collect(Collectors.toList());
  }

  private void checkAndIncrementAggregateVersion(AggregateEntity aggregateEntity) throws OptimisticConcurrencyAggregateVersionServiceException, AggregateNotFoundServiceException {
    if (this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())) {
      // Check aggregate version.
      if (this.aggregateRepository.checkAggregateVersion(aggregateEntity)) {
        // Increment version
        Integer version = aggregateEntity.getVersion() + 1;
        aggregateEntity.setVersion(version);
        // Update aggregate version
        this.aggregateRepository.save(aggregateEntity);
      } else {
        log.debug(
          "Optimistic concurrency control error in aggregate {}: actual version doesn't match expected version {}",
          aggregateEntity.getId(),
          aggregateEntity.getVersion());
        throw new OptimisticConcurrencyAggregateVersionServiceException("Optimistic concurrency control error in aggregate :: " + aggregateEntity.getId() + " actual version doesn't match expected version :: " + aggregateEntity.getVersion() );
      }
    }else{
      throw new AggregateNotFoundServiceException("Aggregate :: " + aggregateEntity.getId() + " :: Not Found");
    }
  }
}
