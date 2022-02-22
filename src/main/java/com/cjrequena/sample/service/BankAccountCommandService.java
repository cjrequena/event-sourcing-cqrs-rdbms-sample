package com.cjrequena.sample.service;

import com.cjrequena.sample.command.Command;
import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.event.EEventType;
import com.cjrequena.sample.exception.service.AggregateVersionServiceException;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  //@Transactional
  public void process(CreateBankAccountCommand command) {

    BankAccountCratedEventEntity eventEntity = new BankAccountCratedEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_CREATED_EVENT.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);

//    AccountCreatedEvent event = AccountCreatedEvent.builder()
//      .type(EEventType.ACCOUNT_CREATED_EVENT)
//      .data(command.getData())
//      .aggregateId(command.getAggregateId())
//      .version(command.getVersion())
//      .build();
//    KafkaEvent<AccountCreatedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
//    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_CREATED_EVENT.getValue());
//    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(DepositBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    BankAccountDepositedEventEntity eventEntity = new BankAccountDepositedEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_DEPOSITED_EVENT.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);

//    AccountDepositedEvent event = AccountDepositedEvent.builder()
//      .type(EEventType.ACCOUNT_DEPOSITED_EVENT)
//      .data(command.getData())
//      .aggregateId(command.getAggregateId())
//      .version(command.getVersion() + 1) // TODO
//      .build();
//    KafkaEvent<AccountDepositedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
//    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_DEPOSITED_EVENT.getValue());
//    applicationEventPublisher.publishEvent(kafkaEvent);
  }

  @Transactional
  public void process(WithdrawBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    BankAccountWithdrawnEventEntity eventEntity = new BankAccountWithdrawnEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_WITHDRAWN_EVENT.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);

//    AccountWithdrawnEvent event = AccountWithdrawnEvent.builder()
//      .type(EEventType.ACCOUNT_WITHDRAWN_EVENT)
//      .data(command.getData())
//      .aggregateId(command.getAggregateId())
//      .version(command.getVersion() + 1) // TODO
//      .build();
//    KafkaEvent<AccountDepositedEvent> kafkaEvent = new KafkaEvent(event, Constant.EVENT_CHANNEL_OUT);
//    kafkaEvent.addHeader("operation", EEventType.ACCOUNT_WITHDRAWN_EVENT.getValue());
//    applicationEventPublisher.publishEvent(kafkaEvent);
  }

//  private BankAccountEntity validateAggregateState(UUID aggregateId, int version) throws AggregateVersionServiceException, BankAccountNotFoundServiceException {
//    final Optional<BankAccountEntity> bankAccountEntityOptional = this.bankAccountRepository.findById(aggregateId);
//    if (bankAccountEntityOptional.isPresent()) {
//      BankAccountEntity bankAccountEntity = bankAccountEntityOptional.get();
//      if (bankAccountEntity.getVersion() != version) {
//        throw new AggregateVersionServiceException("Current aggregate version do not match with command aggregate version");
//      }
//      return bankAccountEntity;
//    } else {
//      throw new BankAccountNotFoundServiceException("Bank account " + aggregateId + " not found");
//    }
//  }
}
