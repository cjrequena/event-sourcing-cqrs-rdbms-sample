package com.cjrequena.sample.mapper;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.command.DepositBankAccountCommand;
import com.cjrequena.sample.command.WithdrawBankAccountCommand;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.event.BankAccountCratedEvent;
import com.cjrequena.sample.event.BankAccountDepositedEvent;
import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
import com.cjrequena.sample.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

  @Mapping(source = "id", target = "data.id")
  @Mapping(source = "aggregateId", target = "data.aggregateId")
  @Mapping(source = "version", target = "data.version")
  @Mapping(source = "dataContentType", target = "data.dataContentType")
  @Mapping(source = "source", target = "data.source")
  @Mapping(source = "data", target = "data.data")
  BankAccountCratedEventEntity toEntity(BankAccountCratedEvent event);

  @Mapping(source = "id", target = "data.id")
  @Mapping(source = "aggregateId", target = "data.aggregateId")
  @Mapping(source = "version", target = "data.version")
  @Mapping(source = "dataContentType", target = "data.dataContentType")
  @Mapping(source = "source", target = "data.source")
  @Mapping(source = "data", target = "data.data")
  BankAccountDepositedEventEntity toEntity(BankAccountDepositedEvent event);

  @Mapping(source = "id", target = "data.id")
  @Mapping(source = "aggregateId", target = "data.aggregateId")
  @Mapping(source = "version", target = "data.version")
  @Mapping(source = "dataContentType", target = "data.dataContentType")
  @Mapping(source = "source", target = "data.source")
  @Mapping(source = "data", target = "data.data")
  BankAccountWithdrawnEventEntity toEntity(BankAccountWithdrawnEvent event);

  @Mapping(target = "id", source = "data.id")
  @Mapping(target = "aggregateId", source = "data.aggregateId")
  @Mapping(target = "version", source = "data.version")
  @Mapping(target = "dataContentType", source = "data.dataContentType")
  @Mapping(target = "source", source = "data.source")
  @Mapping(target = "data", source = "data.data")
  BankAccountCratedEvent toEvent(BankAccountCratedEventEntity entity);

  @Mapping(target = "id", source = "data.id")
  @Mapping(target = "aggregateId", source = "data.aggregateId")
  @Mapping(target = "version", source = "data.version")
  @Mapping(target = "dataContentType", source = "data.dataContentType")
  @Mapping(target = "source", source = "data.source")
  @Mapping(target = "data", source = "data.data")
  BankAccountDepositedEvent toEvent(BankAccountDepositedEventEntity entity);

  @Mapping(target = "id", source = "data.id")
  @Mapping(target = "aggregateId", source = "data.aggregateId")
  @Mapping(target = "version", source = "data.version")
  @Mapping(target = "dataContentType", source = "data.dataContentType")
  @Mapping(target = "source", source = "data.source")
  @Mapping(target = "data", source = "data.data")
  BankAccountWithdrawnEvent toEvent(BankAccountWithdrawnEventEntity entity);

}
