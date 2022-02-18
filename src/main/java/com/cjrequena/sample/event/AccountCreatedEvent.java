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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AccountCreatedEvent extends Event<BankAccountDTO> implements Serializable {

  public AccountCreatedEvent() {
  }

  @Builder
  public AccountCreatedEvent(
    @NotBlank @NotNull UUID id,
    @NotBlank String source,
    @NotBlank String specVersion,
    @NotNull @NotNull EEventType type,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotBlank @NotNull BankAccountDTO data,
    String dataBase64,
    ESchemaType dataSchema,
    @NotBlank @NotNull UUID aggregateId,
    @NotBlank @NotNull int version) {
    super(id, source, specVersion, type, dataContentType, subject, time, data, dataBase64, dataSchema, aggregateId, version);
  }

}
