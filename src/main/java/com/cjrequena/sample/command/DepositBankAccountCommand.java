package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.DepositBankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

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
public class DepositBankAccountCommand extends Command<DepositBankAccountDTO> implements Serializable {

  @Builder
  public DepositBankAccountCommand(DepositBankAccountDTO depositBankAccountDTO) {
    super(depositBankAccountDTO.getAccountId(), depositBankAccountDTO.getVersion(), depositBankAccountDTO);
  }
}
