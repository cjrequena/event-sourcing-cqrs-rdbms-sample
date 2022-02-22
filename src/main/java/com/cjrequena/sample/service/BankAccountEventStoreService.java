package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constant;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void appendEvent(EventEntity event) {
    Objects.requireNonNull(event);
    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());

    if (this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())) {
      // Check aggregate version.
      if (this.aggregateRepository.checkAggregateVersion(aggregateEntity)) {
        // Increment aggregate version
        aggregateEntity.setVersion(aggregateEntity.getVersion()+1);
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

  private void createAggregateIfNotExists(EventEntity event) {
    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(UUID.fromString("70f968b9-136c-45e7-9717-09272807e276"));
    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());
    if (!this.aggregateRepository.existsByIdAndName(aggregateEntity.getId(), aggregateEntity.getName())) {
      this.aggregateRepository.save(aggregateEntity);
    }
  }

  //  public void appendBankAccountCreatedEvent(AccountCreatedEvent event) {
  //    Objects.requireNonNull(event);
  //    BankAccountDTO data = event.getData();
  //    data.setVersion(event.getVersion());
  //    BankAccountCratedEventEntity bankAccountCratedEventEntity = new BankAccountCratedEventEntity();
  //    bankAccountCratedEventEntity.setType(event.getType().getValue());
  //    bankAccountCratedEventEntity.setData(event.getData());
  //    bankAccountCratedEventEntity.setDataBase64(Base64.getEncoder().encodeToString(event.getData().toString().getBytes()));
  //    bankAccountCratedEventEntity.setAggregateId(event.getAggregateId());
  //    bankAccountCratedEventEntity.setVersion(event.getVersion()); // TODO
  //
  //    AggregateEntity aggregateEntity = new AggregateEntity();
  //    aggregateEntity.setId(event.getAggregateId());
  //    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
  //    aggregateEntity.setVersion(event.getVersion());
  //
  //    this.aggregateRepository.save(aggregateEntity);
  //    this.bankAccountEventRepository.save(bankAccountCratedEventEntity);
  //  }
  //
  //  public void appendBankAccountDepositedEvent(AccountDepositedEvent event) {
  //    Objects.requireNonNull(event);
  //    DepositBankAccountDTO data = event.getData();
  //    data.setVersion(event.getVersion());
  //    BankAccountDepositedEventEntity bankAccountDepositedEventEntity = new BankAccountDepositedEventEntity();
  //    bankAccountDepositedEventEntity.setType(event.getType().getValue());
  //    bankAccountDepositedEventEntity.setData(data);
  //    bankAccountDepositedEventEntity.setDataBase64(Base64.getEncoder().encodeToString(event.getData().toString().getBytes()));
  //    bankAccountDepositedEventEntity.setAggregateId(event.getAggregateId());
  //    bankAccountDepositedEventEntity.setVersion(event.getVersion()); // TODO
  //
  //    AggregateEntity aggregateEntity = new AggregateEntity();
  //    aggregateEntity.setId(event.getAggregateId());
  //    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
  //    aggregateEntity.setVersion(event.getVersion());
  //
  //    this.aggregateRepository.save(aggregateEntity);
  //    this.bankAccountEventRepository.save(bankAccountDepositedEventEntity);
  //  }
  //
  //  public void retrieveEventsByAggregateId(UUID aggregateId) {
  //    List<BankAccountCratedEventEntity> events = this.bankAccountEventRepository.findByAggregateId(aggregateId);
  //    for (BankAccountCratedEventEntity event : events) {
  //      log.debug("{}", event.getData());
  //    }
  //  }
}
