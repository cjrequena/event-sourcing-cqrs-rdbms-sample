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
public enum ESchemaType {

  ACCOUNT_CREATED_EVENT_SCHEMA_V1("https://schemas.cjrequena.com/account-created/version/1"),
  ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1("https://schemas.cjrequena.com/account-deposited/version/1"),
  ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1("https://schemas.cjrequena.com/account-withdrawn/version/1");

  @JsonValue
  @Getter
  private final String value;

  ESchemaType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ESchemaType parse(String value) {
    return Arrays.stream(ESchemaType.values()).filter(e -> e.getValue().equals(value)).findFirst().orElse(null);
  }

}

