package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.BankAccountWithdrawnEventConverter;
import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
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
@DiscriminatorValue(Constants.BANK_ACCOUNT_WITHDRAWN_EVENT_V1)
public class BankAccountWithdrawnEventEntity extends EventEntity {

  // Payload
  @Convert(converter = BankAccountWithdrawnEventConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountWithdrawnEvent data;

  public BankAccountWithdrawnEventEntity(
    UUID id,
    UUID aggregateId,
    Long version,
    String type,
    String dataContentType,
    OffsetDateTime time,
    String dataBase64,
    Integer offset,
    BankAccountWithdrawnEvent data) {
    super(id, aggregateId, version, type, dataContentType, time, dataBase64, offset);
    this.data = data;
  }

}
