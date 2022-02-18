package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constant;
import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.dto.DepositBankAccountDTO;
import com.cjrequena.sample.event.AccountCreatedEvent;
import com.cjrequena.sample.event.AccountDepositedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
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
@Transactional
public class BankAccountEventStoreService {

  private AggregateRepository aggregateRepository;
  private BankAccountEventRepository bankAccountEventRepository;

  @Autowired
  public BankAccountEventStoreService(BankAccountEventRepository bankAccountEventRepository, AggregateRepository aggregateRepository) {
    this.bankAccountEventRepository = bankAccountEventRepository;
    this.aggregateRepository = aggregateRepository;
  }

  public void appendBankAccountCreatedEvent(AccountCreatedEvent event) {
    Objects.requireNonNull(event);
    BankAccountDTO data = event.getData();
    data.setVersion(event.getVersion());
    BankAccountCratedEventEntity bankAccountCratedEventEntity = new BankAccountCratedEventEntity();
    bankAccountCratedEventEntity.setType(event.getType().getValue());
    bankAccountCratedEventEntity.setData(event.getData());
    bankAccountCratedEventEntity.setDataBase64(Base64.getEncoder().encodeToString(event.getData().toString().getBytes()));
    bankAccountCratedEventEntity.setAggregateId(event.getAggregateId());
    bankAccountCratedEventEntity.setVersion(event.getVersion()); // TODO

    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());

    this.aggregateRepository.save(aggregateEntity);
    this.bankAccountEventRepository.save(bankAccountCratedEventEntity);
  }

  public void appendBankAccountDepositedEvent(AccountDepositedEvent event) {
    Objects.requireNonNull(event);
    DepositBankAccountDTO data = event.getData();
    data.setVersion(event.getVersion());
    BankAccountDepositedEventEntity bankAccountDepositedEventEntity = new BankAccountDepositedEventEntity();
    bankAccountDepositedEventEntity.setType(event.getType().getValue());
    bankAccountDepositedEventEntity.setData(data);
    bankAccountDepositedEventEntity.setDataBase64(Base64.getEncoder().encodeToString(event.getData().toString().getBytes()));
    bankAccountDepositedEventEntity.setAggregateId(event.getAggregateId());
    bankAccountDepositedEventEntity.setVersion(event.getVersion()); // TODO

    AggregateEntity aggregateEntity = new AggregateEntity();
    aggregateEntity.setId(event.getAggregateId());
    aggregateEntity.setName(Constant.BANK_ACCOUNT_AGGREGATE_NAME);
    aggregateEntity.setVersion(event.getVersion());

    this.aggregateRepository.save(aggregateEntity);
    this.bankAccountEventRepository.save(bankAccountDepositedEventEntity);
  }

  public void retrieveEventsByAggregateId(UUID aggregateId) {
    List<BankAccountCratedEventEntity> events = this.bankAccountEventRepository.findByAggregateId(aggregateId);
    for (BankAccountCratedEventEntity event : events) {
      log.debug("{}", event.getData());
    }
  }
}
