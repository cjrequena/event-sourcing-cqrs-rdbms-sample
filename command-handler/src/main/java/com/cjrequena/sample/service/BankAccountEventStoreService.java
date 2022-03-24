package com.cjrequena.sample.service;

import com.cjrequena.sample.aggregate.EAggregate;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.exception.service.AggregateNotFoundServiceException;
import com.cjrequena.sample.exception.service.DuplicatedAggregateServiceException;
import com.cjrequena.sample.exception.service.OptimisticConcurrencyServiceException;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class BankAccountEventStoreService {

  private final AggregateRepository aggregateRepository;
  private final BankAccountEventRepository bankAccountEventRepository;
  private final BankAccountMapper bankAccountMapper;

  public void appendEvent(Event event) throws OptimisticConcurrencyServiceException, DuplicatedAggregateServiceException, AggregateNotFoundServiceException {
    Objects.requireNonNull(event);
    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(EAggregate.BANK_ACCOUNTS.getValue());
    aggregateEntity.setVersion(event.getVersion());

    // Append new Event
    switch (event.getType()) {
      case BANK_ACCOUNT_CREATED_EVENT_V1:
        if (this.aggregateRepository.checkAggregate(aggregateEntity.getId(), aggregateEntity.getName())){
          throw new DuplicatedAggregateServiceException("Aggregate :: " + aggregateEntity.getId() + " :: Already Exists");
        }
        // Create the Aggregate
        this.aggregateRepository.save(aggregateEntity);
        break;
      case BANK_ACCOUNT_DEPOSITED_EVENT_V1:
      case BANK_ACCOUNT_WITHDRAWN_EVENT_V1:
        //Check and increment the aggregate version
        this.checkAndIncrementVersion(aggregateEntity);
        event.setVersion(aggregateEntity.getVersion());
        break;
    }
    EventEntity eventEntity = this.bankAccountMapper.toEntity(event);
    this.bankAccountEventRepository.save(eventEntity);
  }

  @Transactional(readOnly = true)
  public List<Event> retrieveEventsByAggregateId(UUID aggregateId) {
    return this.bankAccountEventRepository.retrieveEventsByAggregateId(aggregateId).stream().map(bankAccountMapper::toEvent).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<Event> retrieveEventsAfterOffset(Integer offset) {
    return this.bankAccountEventRepository.retrieveEventsAfterOffset(offset).stream().map(bankAccountMapper::toEvent).collect(Collectors.toList());
  }

  private void checkAndIncrementVersion(AggregateEntity aggregateEntity) throws OptimisticConcurrencyServiceException, AggregateNotFoundServiceException {
    if (this.aggregateRepository.checkAggregate(aggregateEntity.getId(), aggregateEntity.getName())) {
      // Check aggregate version.
      if (this.aggregateRepository.checkVersion(aggregateEntity)) {
        // Increment version
        Long version = aggregateEntity.getVersion() + 1;
        aggregateEntity.setVersion(version);
        // Update aggregate version
        this.aggregateRepository.save(aggregateEntity);
      } else {
        log.debug(
          "Optimistic concurrency control error in aggregate {}: actual version doesn't match expected version {}",
          aggregateEntity.getId(),
          aggregateEntity.getVersion());
        throw new OptimisticConcurrencyServiceException("Optimistic concurrency control error in aggregate :: " + aggregateEntity.getId() + " actual version doesn't match expected version :: " + aggregateEntity.getVersion() );
      }
    }else{
      throw new AggregateNotFoundServiceException("Aggregate :: " + aggregateEntity.getId() + " :: Not Found");
    }
  }
}
