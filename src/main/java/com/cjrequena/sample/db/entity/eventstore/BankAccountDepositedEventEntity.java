package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.dto.DepositBankAccountDTO;
import com.cjrequena.sample.event.ESchemaType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

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
@DiscriminatorValue(value = "com.cjrequena.sample.accountdepositedevent.v1")
@TypeDef(
  name = "json", typeClass = JsonType.class
)
public class BankAccountDepositedEventEntity extends EventEntity {

  // Identifies the schema that data adheres to.
  @Column(name = "data_schema")
  protected String dataSchema = ESchemaType.ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1.getValue();

  // Payload
  //@Convert(converter = DepositBankAccountDataConverter.class)
  @Type(type = "json")
  @Column(name = "data", columnDefinition = "JSON")
  protected DepositBankAccountDTO data;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    BankAccountDepositedEventEntity that = (BankAccountDepositedEventEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
