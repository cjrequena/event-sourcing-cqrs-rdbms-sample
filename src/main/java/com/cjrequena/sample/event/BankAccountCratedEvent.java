package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.BankAccountDTO;
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
public class BankAccountCratedEvent extends Event<BankAccountDTO> implements Serializable {

  protected EEventType type = EEventType.BANK_ACCOUNT_CREATED_EVENT_V1;
  protected ESchemaType schemaType = ESchemaType.BANK_ACCOUNT_CREATED_EVENT_SCHEMA_V1;

  @Builder
  public BankAccountCratedEvent(
    String source,
    String specVersion,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull BankAccountDTO data,
    String dataBase64,
    @NotNull UUID aggregateId,
    @NotNull int version) {
    super(UUID.randomUUID(), source, specVersion, EEventType.BANK_ACCOUNT_CREATED_EVENT_V1, dataContentType, subject, time, data, dataBase64, ESchemaType.BANK_ACCOUNT_CREATED_EVENT_SCHEMA_V1, aggregateId, version);
  }
}
