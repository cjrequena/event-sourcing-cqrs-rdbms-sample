package com.cjrequena.sample.db.entity.eventstore;

import com.cjrequena.sample.event.ESchemaType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class EventEntity {

  // Unique id for the specific message. This id is globally unique
  @Id
  @Column(name = "id")
  protected UUID id = UUID.randomUUID();

  // Identifies the context in which an event happened.
  @Column(name = "source")
  protected String source = "https://event-sourcing-cqrs-sample.sample.cjrequena.com";

  // The version of the CloudEvents specification which the event uses.
  @Column(name = "spec_version")
  protected String specVersion = "1.0";

  // Type of message
  @Column(name = "type")
  protected String type;

  // Content type of the data value. Must adhere to RFC 2046 format.
  @Column(name = "data_content_type")
  protected String dataContentType = "application/json";

  // Identifies the schema that data adheres to.
  @Column(name = "data_schema")
  protected String dataSchema = ESchemaType.BANK_ACCOUNT_EVENT_SCHEMA_SPEC_V1.getValue();

  // Describes the subject of the event in the context of the event producer (identified by source).
  @Column(name = "subject")
  protected String subject;

  // Date and time for when the message was published
  @Column(name = "OFFSET_DATE_TIME")
  protected OffsetDateTime time = OffsetDateTime.now();

  // Base64 encoded event payload. Must adhere to RFC4648.
  @Column(name = "data_base64")
  protected String dataBase64;

  // Unique aggregateId for the specific message. This id is globally unique
  @Column(name = "aggregate_id")
  protected UUID aggregateId;

  @Column(name = "version")
  protected int version;

  @Column(name = "offset_event")
  protected int offsetEvent;
}
