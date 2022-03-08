package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.event.BankAccountCratedEvent;
import com.cjrequena.sample.event.BankAccountDepositedEvent;
import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
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

import static com.cjrequena.sample.common.Constants.*;

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

    // Append new Event
    switch (event.getType()) {
      case BANK_ACCOUNT_CREATED_EVENT_V1:
        if (this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())){
          throw new DuplicatedAggregateServiceException("Aggregate :: " + aggregateEntity.getId() + " :: Already Exists");
        }
        // Create the Aggregate
        this.aggregateRepository.save(aggregateEntity);
        BankAccountCratedEvent bankAccountCratedEvent = (BankAccountCratedEvent) event;
        this.bankAccountEventRepository.save(this.bankAccountMapper.toEntity(bankAccountCratedEvent));
        break;
      case BANK_ACCOUNT_DEPOSITED_EVENT_V1:
        //Check and increment the aggregate version
        this.checkAndIncrementAggregateVersion(aggregateEntity);
        event.setVersion(aggregateEntity.getVersion());
        BankAccountDepositedEvent bankAccountDepositedEvent = (BankAccountDepositedEvent) event;
        this.bankAccountEventRepository.save(this.bankAccountMapper.toEntity(bankAccountDepositedEvent));
        break;
      case BANK_ACCOUNT_WITHDRAWN_EVENT_V1:
        //Check and increment the aggregate version
        this.checkAndIncrementAggregateVersion(aggregateEntity);
        event.setVersion(aggregateEntity.getVersion());
        BankAccountWithdrawnEvent bankAccountWithdrawnEvent = (BankAccountWithdrawnEvent) event;
        this.bankAccountEventRepository.save(this.bankAccountMapper.toEntity(bankAccountWithdrawnEvent));
        break;
    }
  }

  @Transactional(readOnly = true)
  List<Event> retrieveEvents(UUID aggregateId) {
    return this.bankAccountEventRepository.retrieveEvents(aggregateId).stream().map(entity -> {
      Event event = null;
      switch (entity.getType()) {
        case BANK_ACCOUNT_CREATED_EVENT_V1:
          event = this.bankAccountMapper.toEvent((BankAccountCratedEventEntity) entity);
          break;
        case BANK_ACCOUNT_DEPOSITED_EVENT_V1:
          event = this.bankAccountMapper.toEvent((BankAccountDepositedEventEntity) entity);
          break;
        case BANK_ACCOUNT_WITHDRAWN_EVENT_V1:
          event = this.bankAccountMapper.toEvent((BankAccountWithdrawnEventEntity) entity);
          break;
      }
      return event;
    }).collect(Collectors.toList());
  }

  public void checkAndIncrementAggregateVersion(AggregateEntity aggregateEntity) throws OptimisticConcurrencyAggregateVersionServiceException, AggregateNotFoundServiceException {
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
