package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.DepositBankAccountDataConverter;
import com.cjrequena.sample.dto.DepositBankAccountDTO;
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
@DiscriminatorValue(Constants.BANK_ACCOUNT_DEPOSITED_EVENT_V1)
public class BankAccountDepositedEventEntity extends EventEntity {

  // Payload
  @Convert(converter = DepositBankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected DepositBankAccountDTO data;

  public BankAccountDepositedEventEntity(UUID id,
    String source,
    String specVersion,
    String type,
    String dataContentType,
    String subject,
    OffsetDateTime time,
    String dataBase64,
    UUID aggregateId,
    Long version,
    Integer offset,
    DepositBankAccountDTO data) {
    super(id, source, specVersion, type, dataContentType, ESchemaType.BANK_ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1.getValue(), subject, time, dataBase64, aggregateId, version, offset);
    this.data = data;
  }

}
