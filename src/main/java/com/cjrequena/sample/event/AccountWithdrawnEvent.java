package com.cjrequena.sample.event;

import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
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
public class AccountWithdrawnEvent extends Event<WithdrawBankAccountDTO> implements Serializable {

  public AccountWithdrawnEvent() {
  }

  @Builder
  public AccountWithdrawnEvent(
    @NotBlank UUID id,
    @NotBlank String source,
    @NotBlank String service,
    @NotBlank String specVersion,
    @NotNull EEventType type,
    String dataContentType,
    String subject,
    @NotNull OffsetDateTime time,
    @NotNull WithdrawBankAccountDTO data,
    String dataBase64,
    ESchemaType dataSchema,
    @NotBlank UUID aggregateId,
    @NotBlank int version) {
    super(id, source, specVersion, type, dataContentType, subject, time, data, dataBase64, dataSchema, aggregateId, version);
  }
}
