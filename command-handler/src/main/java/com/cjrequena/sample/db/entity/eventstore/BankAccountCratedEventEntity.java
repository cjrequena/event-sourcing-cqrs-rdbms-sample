package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.converter.BankAccountCratedEventConverter;
import com.cjrequena.sample.event.BankAccountCratedEvent;
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
public class BankAccountCratedEventEntity extends EventEntity {

  // Payload
  @Convert(converter = BankAccountCratedEventConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountCratedEvent data;

  @Builder
  public BankAccountCratedEventEntity(
    UUID id,
    UUID aggregateId,
    Long version,
    String type,
    String dataContentType,
    OffsetDateTime time,
    String dataBase64,
    Integer offset,
    BankAccountCratedEvent data) {
    super(id, aggregateId, version, type, dataContentType, time, dataBase64, offset);
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
