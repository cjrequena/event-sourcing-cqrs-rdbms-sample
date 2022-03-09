package com.cjrequena.sample.service;

import com.cjrequena.sample.aggregate.BankAccountAggregate;
import com.cjrequena.sample.command.Command;
import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
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
  private BankAccountMapper bankAccountMapper;

  @Autowired
  public BankAccountCommandService(ApplicationEventPublisher applicationEventPublisher, BankAccountEventStoreService bankAccountEventStoreService, BankAccountMapper bankAccountMapper) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.bankAccountEventStoreService = bankAccountEventStoreService;
    this.bankAccountMapper = bankAccountMapper;
  }

  @Transactional
  public void handler(Command command) throws AggregateNotFoundServiceException, OptimisticConcurrencyAggregateVersionServiceException, DuplicatedAggregateServiceException {
    log.debug("Command type: {} Command aggregate_id: {}", command.getType(), command.getAggregateId());
    // Retrieve the whole event history by a specific aggregate id
    List<Event> events = this.bankAccountEventStoreService.retrieveEventsByAggregateId(command.getAggregateId());
    // Recreate the last aggregate snapshot replaying the whole event history by a specific aggregate id
    BankAccountAggregate bankAccountAggregate = new BankAccountAggregate(command.getAggregateId(), events);

    switch (command.getType()) {
      case CREATE_BANK_ACCOUNT_COMMAND:
        this.process((CreateBankAccountCommand) command);
        break;
      case WITHDRAW_BANK_ACCOUNT_COMMAND:
        this.process((WithdrawBankAccountCommand) command);
        break;
      case DEPOSIT_BANK_ACCOUNT_COMMAND:
        this.process((DepositBankAccountCommand) command);
        break;
    }
  }

  @Transactional
  public void process(CreateBankAccountCommand command)
    throws OptimisticConcurrencyAggregateVersionServiceException, DuplicatedAggregateServiceException, AggregateNotFoundServiceException {
    BankAccountCratedEvent event = this.bankAccountMapper.toEvent(command);
    bankAccountEventStoreService.appendEvent(event);
  }

  @Transactional
  public void process(DepositBankAccountCommand command)
    throws OptimisticConcurrencyAggregateVersionServiceException, DuplicatedAggregateServiceException, AggregateNotFoundServiceException {

    BankAccountDepositedEvent event = this.bankAccountMapper.toEvent(command);
    bankAccountEventStoreService.appendEvent(event);
  }

  @Transactional
  public void process(WithdrawBankAccountCommand command)
    throws OptimisticConcurrencyAggregateVersionServiceException, DuplicatedAggregateServiceException, AggregateNotFoundServiceException {
    BankAccountWithdrawnEvent event = this.bankAccountMapper.toEvent(command);
    bankAccountEventStoreService.appendEvent(event);
  }
}
