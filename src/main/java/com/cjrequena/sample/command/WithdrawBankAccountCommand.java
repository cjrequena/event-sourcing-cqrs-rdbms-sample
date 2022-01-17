package com.cjrequena.sample.command;

import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
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
public class WithdrawBankAccountCommand extends Command<WithdrawBankAccountDTO> implements Serializable {

  @Builder
  public WithdrawBankAccountCommand(WithdrawBankAccountDTO withdrawBankAccountDTO) {
    super(withdrawBankAccountDTO.getAccountId(), withdrawBankAccountDTO.getVersion(), withdrawBankAccountDTO);
  }
}
