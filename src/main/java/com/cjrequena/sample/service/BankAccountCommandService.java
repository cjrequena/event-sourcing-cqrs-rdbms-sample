package com.cjrequena.sample.service;

import com.cjrequena.sample.command.Command;
import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.common.Constant;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.event.*;
import com.cjrequena.sample.exception.service.AggregateVersionServiceException;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
public class BankAccountCommandService {

  private ApplicationEventPublisher applicationEventPublisher;
  private BankAccountRepository bankAccountRepository;
  @Autowired
  private BankAccountEventStoreService bankAccountEventStoreService;

  @Autowired
  public BankAccountCommandService(ApplicationEventPublisher applicationEventPublisher, BankAccountRepository bankAccountRepository) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.bankAccountRepository = bankAccountRepository;
  }

  @Transactional
  public void handler(Command command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    log.debug("Command type: {} Command aggregate_id: {}",command.getCommandType(), command.getAggregateId());
    switch (command.getCommandType()) {
      case "CreateBankAccountCommand":
        this.process((CreateBankAccountCommand) command);
        break;
      case "WithdrawBankAccountCommand":
        this.process((WithdrawBankAccountCommand) command);
        break;
      case "DepositBankAccountCommand":
        this.process((DepositBankAccountCommand) command);
        break;
    }
  }

  @Transactional
  public void process(CreateBankAccountCommand command) {
    AccountCreatedEvent event = AccountCreatedEvent.builder()
      .type(EEventType.ACCOUNT_CREATED_EVENT)
      .data(command.getData())
      .aggregateId(command.getAggregateId())
      .version(command.getVersion())
      .build();

    bankAccountEventStoreService.appendBankAccountCreatedEvent(event);

    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREATED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(DepositBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    AccountDepositedEvent event = AccountDepositedEvent.builder()
      .type(EEventType.ACCOUNT_DEPOSITED_EVENT)
      .data(command.getData())
      .aggregateId(command.getAggregateId())
      .version(command.getVersion() + 1) // TODO
      .build();

    bankAccountEventStoreService.appendBankAccountDepositedEvent(event);

    KafkaEvent<AccountDepositedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_DEPOSITED_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(WithdrawBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    this.validateAggregateState(command.getAggregateId(), command.getVersion());

    AccountWithdrawnEvent event = AccountWithdrawnEvent.builder()
      .type(EEventType.ACCOUNT_WITHDRAWN_EVENT)
      .data(command.getData())
      .aggregateId(command.getAggregateId())
      .version(command.getVersion() + 1) // TODO
      .build();

    KafkaEvent<AccountDepositedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_WITHDRAWN_EVENT.getValue());
    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  private BankAccountEntity validateAggregateState(UUID aggregateId, int version) throws AggregateVersionServiceException, BankAccountNotFoundServiceException {
    final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(aggregateId);
    if (bankAccountEntityOptional.isPresent()) {
      BankAccountEntity bankAccountEntity = bankAccountEntityOptional.get();
      if (bankAccountEntity.getVersion() != version) {
        throw new AggregateVersionServiceException("Current aggregate version do not match with command aggregate version");
      }
      return bankAccountEntity;
    } else {
      throw new BankAccountNotFoundServiceException("Bank account " + aggregateId + " not found");
    }
  }
}
