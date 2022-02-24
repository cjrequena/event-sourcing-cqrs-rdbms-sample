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
  public static final String VND_SAMPLE_SERVICE_V1 = "vnd.sample-service.v1";

  /** EVENTS */
  public static final String ACCOUNT_CREATED_EVENT_V1 = "com.cjrequena.sample.accountcreatedevent.v1";
  public static final String ACCOUNT_DEPOSITED_EVENT_V1 = "com.cjrequena.sample.accountdepositedevent.v1";
  public static final String ACCOUNT_WITHDRAWN_EVENT_V1 = "com.cjrequena.sample.accountwithdrawnevent.v1";

  /** SCHEMAS */
  public static final String ACCOUNT_CREATED_EVENT_SCHEMA_V1 = "https://schemas.cjrequena.com/account-created/version/1";
  public static final String ACCOUNT_DEPOSITED_EVENT_SCHEMA_V1 ="https://schemas.cjrequena.com/account-deposited/version/1";
  public static final String ACCOUNT_WITHDRAWN_EVENT_SCHEMA_V1 = "https://schemas.cjrequena.com/account-withdrawn/version/1";

  /** AGGREGATES */
  public static final String BANK_ACCOUNT_AGGREGATE_NAME = "BANK_ACCOUNT_AGGREGATE";

  /** KAFKA CHANNELS */
  public static final String EVENT_CHANNEL_OUT = "event-channel-out";
}
