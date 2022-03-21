package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.BankAccountDataConverter;
import com.cjrequena.sample.dto.BankAccountDTO;
import com.cjrequena.sample.event.ESchemaType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.OffsetDateTime;
import java.util.Objects;
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
@Entity
@DiscriminatorValue(value = Constants.BANK_ACCOUNT_CREATED_EVENT_V1)
public class BankAccountCratedEventEntity extends EventEntity{

  // Payload
  @Convert(converter = BankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountDTO data;

  @Builder
  public BankAccountCratedEventEntity(
    UUID id,
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
    BankAccountDTO data) {
    super(id, source, specVersion, type, dataContentType, ESchemaType.BANK_ACCOUNT_CREATED_EVENT_SCHEMA_V1.getValue(), subject, time, dataBase64, aggregateId, version, offset);
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    BankAccountCratedEventEntity that = (BankAccountCratedEventEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
