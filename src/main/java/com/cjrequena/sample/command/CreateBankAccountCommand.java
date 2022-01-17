package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@Data
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class CreateBankAccountCommand extends Command<BankAccountDTO> implements Serializable {

  @Builder
  public CreateBankAccountCommand(BankAccountDTO bankAccountDTO) {
    super(UUID.randomUUID(), 1, bankAccountDTO);
    bankAccountDTO.setId(super.aggregateId);
    bankAccountDTO.setVersion(super.version);
  }
}
