package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.WithdrawBankAccountDataConverter;
import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
import com.cjrequena.sample.event.ESchemaType;
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
  @Convert(converter = WithdrawBankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected WithdrawBankAccountDTO data;

  public BankAccountWithdrawnEventEntity(
    UUID id,
    String source,
    String specVersion,
    String type,
    String dataContentType,
    String subject,
    OffsetDateTime time,
    String dataBase64,
    UUID aggregateId,
    Integer aggregateVersion,
    Integer offset,
    WithdrawBankAccountDTO data) {
    super(id, source, specVersion, type, dataContentType, ESchemaType.BANK_ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1.getValue(), subject, time, dataBase64, aggregateId, aggregateVersion, offset);
    this.data = data;
  }

}
