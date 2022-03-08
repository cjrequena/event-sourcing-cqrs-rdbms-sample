package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(callSuper = true)
public class BankAccountWithdrawnEvent extends Event<WithdrawBankAccountDTO> implements Serializable {

  private static final EEventType type = EEventType.ACCOUNT_WITHDRAWN_EVENT_V1;
  protected static final ESchemaType schemaType = ESchemaType.ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1;

  @Builder
  public BankAccountWithdrawnEvent(
    String source,
    String specVersion,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull WithdrawBankAccountDTO data,
    String dataBase64,
    @NotNull UUID aggregateId,
    @NotNull int version) {
    super(UUID.randomUUID(), source, specVersion, type, dataContentType, subject, time, data, dataBase64, schemaType, aggregateId, version);
  }
}
