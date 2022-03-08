package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.DepositBankAccountDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BankAccountDepositedEvent extends Event<DepositBankAccountDTO> implements Serializable {

  // Type of message
  protected EEventType type = EEventType.ACCOUNT_DEPOSITED_EVENT_V1;

  @Builder
  public BankAccountDepositedEvent(
    @NotNull UUID id,
    String source,
    String specVersion,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull DepositBankAccountDTO data,
    String dataBase64,
    ESchemaType dataSchema,
    @NotNull UUID aggregateId,
    @NotNull int version) {
    super(id, source, specVersion, EEventType.ACCOUNT_DEPOSITED_EVENT_V1, dataContentType, subject, time, data, dataBase64, dataSchema, aggregateId, version);
  }
}
