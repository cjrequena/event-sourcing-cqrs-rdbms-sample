package com.cjrequena.sample.common;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
public class Constants {

  /** EVENTS */
  public static final String BANK_ACCOUNT_CREATED_EVENT_V1 = "com.cjrequena.sample.bankaccountcreated.event.v1";
  public static final String BANK_ACCOUNT_DEPOSITED_EVENT_V1 = "com.cjrequena.sample.bankaccountdeposited.event.v1";
  public static final String BANK_ACCOUNT_WITHDRAWN_EVENT_V1 = "com.cjrequena.sample.bankaccountwithdrawn.event.v1";

  /** SCHEMAS */
  public static final String BANK_ACCOUNT_CREATED_EVENT_SCHEMA_V1 = "https://schemas.cjrequena.com/bank-account-created/version/1";
  public static final String BANK_ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1 ="https://schemas.cjrequena.com/bank-account-deposited/version/1";
  public static final String BANK_ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1 = "https://schemas.cjrequena.com/bank-account-withdrawn/version/1";

  /***/
  public static final String CLOUD_EVENTS_SOURCE = "https://event-sourcing-cqrs-sample.sample.cjrequena.com";
}
