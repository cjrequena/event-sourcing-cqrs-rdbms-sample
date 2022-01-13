package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.db.converter.BankAccountDataConverter;
import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Entity
@Table(name = "bank_account_event")
@Data
public class BankAccountEventEntity extends EventEntity {

  // Payload
  @Convert(converter = BankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected BankAccountDTO data;
}
