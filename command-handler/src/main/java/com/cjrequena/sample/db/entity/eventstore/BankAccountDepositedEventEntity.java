package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.BankAccountDepositedEventConverter;
import com.cjrequena.sample.event.BankAccountDepositedEvent;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(Constants.BANK_ACCOUNT_DEPOSITED_EVENT_V1)
public class BankAccountDepositedEventEntity extends EventEntity {

  // Payload
  @Convert(converter = BankAccountDepositedEventConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountDepositedEvent data;

  public BankAccountDepositedEventEntity(
    UUID id,
    UUID aggregateId,
    Long version,
    String type,
    String dataContentType,
    OffsetDateTime time,
    String dataBase64,
    Integer offset,
    BankAccountDepositedEvent data) {
    super(id, aggregateId, version, type, dataContentType, time, dataBase64, offset);
    this.data = data;
  }

}
