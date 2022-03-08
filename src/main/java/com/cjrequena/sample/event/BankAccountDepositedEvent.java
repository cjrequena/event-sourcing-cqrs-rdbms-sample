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

  protected EEventType type = EEventType.ACCOUNT_DEPOSITED_EVENT_V1;
  protected ESchemaType schemaType = ESchemaType.ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1;

  @Builder
  public BankAccountDepositedEvent(
    String source,
    String specVersion,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull DepositBankAccountDTO data,
    String dataBase64,
    @NotNull UUID aggregateId,
    @NotNull int version) {
    super(UUID.randomUUID(), source, specVersion, EEventType.ACCOUNT_DEPOSITED_EVENT_V1, dataContentType, subject, time, data, dataBase64, ESchemaType.ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1, aggregateId, version);
  }
}
