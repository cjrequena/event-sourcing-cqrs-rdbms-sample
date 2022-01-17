package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.BankAccountDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
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
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class AccountCreatedEvent extends Event<BankAccountDTO> implements Serializable {

  public AccountCreatedEvent() {
  }

  @Builder
  public AccountCreatedEvent(
    @NotBlank String id,
    @NotBlank String source,
    @NotBlank String service,
    @NotBlank String specVersion,
    @NotNull EEventType type,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull BankAccountDTO data,
    String dataBase64,
    ESchemaType dataSchema,
    @NotBlank UUID aggregateId,
    @NotBlank int version) {
    super(id, source, service, specVersion, type, dataContentType, subject, time, data, dataBase64, dataSchema, aggregateId, version);
  }

}
