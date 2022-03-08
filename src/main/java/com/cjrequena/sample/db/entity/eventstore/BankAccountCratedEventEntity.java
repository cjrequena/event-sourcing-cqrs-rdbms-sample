package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.dto.BankAccountDTO;
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
@DiscriminatorValue(value = Constants.BANK_ACCOUNT_CREATED_EVENT_V1)
@TypeDef(
  name = "json", typeClass = JsonType.class
)
public class BankAccountCratedEventEntity extends EventEntity {

  // Identifies the schema that data adheres to.
  @Column(name = "data_schema")
  protected String dataSchema = ESchemaType.BANK_ACCOUNT_CREATED_EVENT_SCHEMA_V1.getValue();

  // Payload
  @Type(type = "json")
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountDTO data;

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
