package com.cjrequena.sample.mapper;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.event.BankAccountCratedEvent;
import com.cjrequena.sample.event.BankAccountDepositedEvent;
import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
import com.cjrequena.sample.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BankAccountMapper {

  default EventEntity toEntity(Event event) {
    if (event != null) {
      if (event instanceof BankAccountCratedEvent) {
        return toEntity((BankAccountCratedEvent) event);
      } else if (event instanceof BankAccountDepositedEvent) {
        return toEntity((BankAccountDepositedEvent) event);
      } else if (event instanceof BankAccountWithdrawnEvent) {
        return toEntity((BankAccountWithdrawnEvent) event);
      }
    }
    return null;
  }

  default Event toEvent(EventEntity entity) {
    if (entity != null) {
      if (entity instanceof BankAccountCratedEventEntity) {
        return toEvent((BankAccountCratedEventEntity) entity);
      } else if (entity instanceof BankAccountDepositedEventEntity) {
        return toEvent((BankAccountDepositedEventEntity) entity);
      } else if (entity instanceof BankAccountWithdrawnEventEntity) {
        return toEvent((BankAccountWithdrawnEventEntity) entity);
      }
    }
    return null;
  }

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

  BankAccountEntity toEntity(BankAccountDTO dto);

}
