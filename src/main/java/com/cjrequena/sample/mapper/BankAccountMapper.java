package com.cjrequena.sample.mapper;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.event.BankAccountCratedEvent;
import com.cjrequena.sample.event.BankAccountDepositedEvent;
import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BankAccountMapper {

  BankAccountCratedEvent toEvent(CreateBankAccountCommand command);

  BankAccountDepositedEvent toEvent(DepositBankAccountCommand command);

  BankAccountWithdrawnEvent toEvent(WithdrawBankAccountCommand command);

  //@Mapping(source = "order.driverId", target = "driverId")
  BankAccountCratedEventEntity toEntity(BankAccountCratedEvent event);

  BankAccountDepositedEventEntity toEntity(BankAccountDepositedEvent event);

  BankAccountWithdrawnEventEntity toEntity(BankAccountWithdrawnEvent event);

  BankAccountCratedEvent toEvent(BankAccountCratedEventEntity entity);

  BankAccountDepositedEvent toEvent(BankAccountDepositedEventEntity entity);

  BankAccountWithdrawnEvent toEvent(BankAccountWithdrawnEventEntity entity);

  BankAccountCratedEventEntity toEntity(CreateBankAccountCommand command);

}
