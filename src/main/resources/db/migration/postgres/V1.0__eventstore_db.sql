CREATE TABLE AGGREGATE (
  ID UUID PRIMARY KEY,
  NAME VARCHAR NOT NULL,
  VERSION BIGINT NOT NULL,
  CONSTRAINT AGGREGATE_UNIQUE UNIQUE  (ID, NAME, VERSION)
);

CREATE TABLE SUBSCRIPTION (
  ID UUID PRIMARY KEY,
  CONSUMER_GROUP VARCHAR UNIQUE,
  AGGREGATE_NAME VARCHAR NOT NULL,
  OFFSET_EVENT BIGINT NOT NULL,
  CONSTRAINT SUBSCRIPTION_UNIQUE UNIQUE (CONSUMER_GROUP, AGGREGATE_NAME)
);

CREATE TABLE BANK_ACCOUNT_EVENT(
  ID UUID PRIMARY KEY,
  AGGREGATE_ID UUID NOT NULL REFERENCES AGGREGATE(ID),
  AGGREGATE_VERSION BIGINT NOT NULL,
  SOURCE VARCHAR,
  SPEC_VERSION VARCHAR,
  TYPE VARCHAR NOT NULL,
  DATA_CONTENT_TYPE VARCHAR,
  SUBJECT VARCHAR,
  DATA VARCHAR NOT NULL,
  DATA_BASE64 VARCHAR,
  DATA_SCHEMA VARCHAR,
  OFFSET_DATE_TIME TIMESTAMP,
  OFFSET_EVENT BIGSERIAL NOT NULL,
  CONSTRAINT BANK_ACCOUNT_EVENT_UNIQUE UNIQUE (AGGREGATE_ID, AGGREGATE_VERSION)
);

CREATE TABLE BANK_ACCOUNT (
  ID UUID PRIMARY KEY,
  OWNER VARCHAR,
  BALANCE DECIMAL,
  CREATION_DATE TIMESTAMP,
  VERSION BIGINT
);
