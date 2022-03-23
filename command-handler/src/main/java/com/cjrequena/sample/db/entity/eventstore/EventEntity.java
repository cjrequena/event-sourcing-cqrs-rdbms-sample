package com.cjrequena.sample.db.entity.eventstore;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "event")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EventEntity{

  // Unique id for the specific message. This id is globally unique
  @Id
  @Column(name = "id")
  protected UUID id;

  // Identifies the context in which an event happened.
  @Column(name = "source")
  protected String source;

  // The version of the CloudEvents specification which the event uses.
  @Column(name = "spec_version")
  protected String specVersion;

  // Type of message
  @Column(name = "type", insertable = false, updatable = false)
  protected String type;

  // Content type of the data value. Must adhere to RFC 2046 format.
  @Column(name = "data_content_type")
  protected String dataContentType;

  // Identifies the schema that data adheres to.
  @Column(name = "data_schema")
  protected String dataSchema;

  // Describes the subject of the event in the context of the event producer (identified by source).
  @Column(name = "subject")
  protected String subject;

  // Date and time for when the message was published
  @Column(name = "OFFSET_DATE_TIME")
  protected OffsetDateTime time;

  // The event payload.
  // @Column(name = "data")
  // protected T data;

  // Base64 encoded event payload. Must adhere to RFC4648.
  @Column(name = "data_base64")
  protected String dataBase64;

  // Unique aggregateId for the specific message. This id is globally unique
  @Column(name = "aggregate_id")
  protected UUID aggregateId;

  @Column(name = "version")
  protected Long version;

  @Column(name = "offset_event", insertable = false, updatable = false)
  protected Integer offset;

}
