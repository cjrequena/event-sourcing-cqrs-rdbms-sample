package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.db.converter.DepositBankAccountDataConverter;
import com.cjrequena.sample.dto.DepositBankAccountDTO;
import lombok.*;

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
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_account_event")
public class BankAccountDepositedEventEntity extends EventEntity {
  // Payload
  @Convert(converter = DepositBankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected DepositBankAccountDTO data;

}
