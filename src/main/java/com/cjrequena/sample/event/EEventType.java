package com.cjrequena.sample.event;

import com.cjrequena.sample.common.Constants;
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

  ACCOUNT_CREATED_EVENT_V1(Constants.ACCOUNT_CREATED_EVENT_V1, ESchemaType.ACCOUNT_CREATED_EVENT_SCHEMA_V1),
  ACCOUNT_DEPOSITED_EVENT_V1(Constants.ACCOUNT_DEPOSITED_EVENT_V1, ESchemaType.ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1),
  ACCOUNT_WITHDRAWN_EVENT_V1(Constants.ACCOUNT_WITHDRAWN_EVENT_V1, ESchemaType.ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1);

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
