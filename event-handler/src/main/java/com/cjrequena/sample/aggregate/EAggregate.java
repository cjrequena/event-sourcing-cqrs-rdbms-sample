package com.cjrequena.sample.aggregate;

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
public enum EAggregate {

  BANK_ACCOUNTS("bank_accounts");

  @JsonValue
  @Getter
  private final String value;

  EAggregate(String value) {
    this.value = value;
  }

  @JsonCreator
  public static EAggregate parse(String name) {
    return Arrays.stream(EAggregate.values()).filter(e -> e.getValue().equals(name)).findFirst().orElse(null);
  }

}
