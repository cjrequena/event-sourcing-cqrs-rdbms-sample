package com.cjrequena.sample.service;

import com.cjrequena.sample.command.Command;
import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.event.BankAccountAggregate;
import com.cjrequena.sample.event.EEventType;
import com.cjrequena.sample.exception.service.AggregateVersionServiceException;
import com.cjrequena.sample.exception.service.BankAccountNotFoundServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
  private BankAccountEventStoreService bankAccountEventStoreService;
  private BankAccountAggregate aggregate;

  @Autowired
  public BankAccountCommandService(ApplicationEventPublisher applicationEventPublisher,  BankAccountEventStoreService bankAccountEventStoreService) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.bankAccountEventStoreService = bankAccountEventStoreService;
  }

  @Transactional
  public void handler(Command command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    log.debug("Command type: {} Command aggregate_id: {}", command.getCommandType(), command.getAggregateId());
    // Retrieve the whole event history by a specific aggregate id
    List<EventEntity> eventEntities = this.bankAccountEventStoreService.retrieveEvents(command.getAggregateId());
    // Recreate the last aggregate snapshot replaying the whole event history by a specific aggregate id
    this.aggregate = new BankAccountAggregate(command.getAggregateId(), eventEntities);

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
    BankAccountCratedEventEntity eventEntity = new BankAccountCratedEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_CREATED_EVENT_V1.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);
  }

  @Transactional
  public void process(DepositBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    BankAccountDepositedEventEntity eventEntity = new BankAccountDepositedEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_DEPOSITED_EVENT_V1.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);
  }

  @Transactional
  public void process(WithdrawBankAccountCommand command) throws BankAccountNotFoundServiceException, AggregateVersionServiceException {
    BankAccountWithdrawnEventEntity eventEntity = new BankAccountWithdrawnEventEntity();
    eventEntity.setType(EEventType.ACCOUNT_WITHDRAWN_EVENT_V1.getValue());
    eventEntity.setData(command.getData());
    eventEntity.setAggregateId(command.getAggregateId());
    eventEntity.setVersion(command.getVersion());
    bankAccountEventStoreService.appendEvent(eventEntity);
  }
}
