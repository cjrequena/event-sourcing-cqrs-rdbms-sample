package com.cjrequena.sample.mapper;

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

  BankAccountCratedEvent toEvent(BankAccountCratedEventEntity entity);

  BankAccountDepositedEvent toEvent(BankAccountDepositedEventEntity entity);

  BankAccountWithdrawnEvent toEvent(BankAccountWithdrawnEventEntity entity);

  BankAccountEntity toEntity(BankAccountDTO dto);

}
