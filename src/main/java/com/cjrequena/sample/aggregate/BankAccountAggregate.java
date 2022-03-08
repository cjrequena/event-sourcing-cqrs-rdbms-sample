package com.cjrequena.sample.aggregate;

import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountDepositedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.BankAccountWithdrawnEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import static com.cjrequena.sample.common.Constants.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Slf4j
public class BankAccountAggregate extends Aggregate {

  private BankAccountDTO bankAccountDTO;

  public BankAccountAggregate(UUID aggregateId, List<EventEntity> events) {
    super(aggregateId, BANK_ACCOUNT_AGGREGATE_NAME, events);
  }

  @Override
  public void replayEvents(List<EventEntity> events) {
    events.forEach(
      event -> {
        switch (event.getType()) {
          case ACCOUNT_CREATED_EVENT_V1:
            this.apply((BankAccountCratedEventEntity) event);
            break;
          case ACCOUNT_DEPOSITED_EVENT_V1:
            this.apply((BankAccountDepositedEventEntity) event);
            break;
          case ACCOUNT_WITHDRAWN_EVENT_V1:
            this.apply((BankAccountWithdrawnEventEntity) event);
            break;
        }
        version = event.getVersion();
      });
  }

  public void apply(BankAccountCratedEventEntity event) {
    this.bankAccountDTO = new BankAccountDTO();
    this.bankAccountDTO.setId(event.getData().getId());
    this.bankAccountDTO.setOwner(event.getData().getOwner());
    this.bankAccountDTO.setOwner(event.getData().getOwner());
    this.bankAccountDTO.setVersion(event.getData().getVersion());
    this.bankAccountDTO.setBalance(event.getData().getBalance());
  }

  public void apply(BankAccountDepositedEventEntity event) {
    this.bankAccountDTO.setBalance(this.bankAccountDTO.getBalance().add(event.getData().getAmount()));
  }

  public void apply(BankAccountWithdrawnEventEntity event) {
    this.bankAccountDTO.setBalance(this.bankAccountDTO.getBalance().subtract(event.getData().getAmount()));
  }
}
