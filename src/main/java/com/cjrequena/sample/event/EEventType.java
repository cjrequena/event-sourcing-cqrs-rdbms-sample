package com.cjrequena.sample.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public enum EEventType {

  ACCOUNT_CREATED_EVENT("com.cjrequena.samample.accountcreatedevent.v1", ESchemaType.ACCOUNT_CREATED_EVENT_SCHEMA_V1),
  ACCOUNT_CREDITED_EVENT("com.cjrequena.samample.accountcreditedevent.v1", ESchemaType.ACCOUNT_CREDITED_EVENT_SCHEMA_V1),
  ACCOUNT_DEBITED_EVENT("com.cjrequena.samample.accountdebitedevent.v1", ESchemaType.ACCOUNT_DEBITED_EVENT_SCHEMA_V1);

  @JsonValue
  @Getter
  private final String value;

  @Getter
  private final ESchemaType schema;

  EEventType(String value, ESchemaType schema) {
    this.value = value;
    this.schema = schema;
  }

  @JsonCreator
  public static EEventType parse(String value) {
    return Arrays.stream(EEventType.values()).filter(e -> e.getValue().equals(value)).findFirst().orElse(null);
  }

}
