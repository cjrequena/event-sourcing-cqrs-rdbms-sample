package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.db.converter.WithdrawBankAccountDataConverter;
import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
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
public class BankAccountWithdrawnEventEntity extends EventEntity {
  // Payload
  @Convert(converter = WithdrawBankAccountDataConverter.class)
  @Column(name = "data", columnDefinition = "JSON")
  protected WithdrawBankAccountDTO data;
}
