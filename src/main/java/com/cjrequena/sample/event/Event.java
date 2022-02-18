package com.cjrequena.sample.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public abstract class Event<T> {

  // Unique id for the specific message. This id is globally unique
  @NotBlank @NotNull protected UUID id;

  // Identifies the context in which an event happened.
  @NotBlank protected String source;

  // The version of the CloudEvents specification which the event uses.
  @NotBlank protected String specVersion = "1.0";

  // Type of message
  @NotBlank @NotNull protected EEventType type;

  // Content type of the data value. Must adhere to RFC 2046 format.
  @NotBlank protected String dataContentType = "application/json";

  // Describes the subject of the event in the context of the event producer (identified by source).
  protected String subject;

  // Date and time for when the message was published
  @NotNull protected OffsetDateTime time = OffsetDateTime.now();

  // Payload
  @NotNull protected T data;

  // Base64 encoded event payload. Must adhere to RFC4648.
  @JsonProperty(value = "data_base64") protected String dataBase64;

  // Identifies the schema that data adheres to.
  protected ESchemaType dataSchema;

  // Unique aggregateId for the specific message. This id is globally unique
  @NotBlank protected UUID aggregateId;

  @NotBlank protected int version;

  /**
   * Get Headers from event
   * @return
   */
  @JsonIgnore public Map<String, Object> getHeaders() {
    return new HashMap<>(Map.of("event-id", id, "event-timestamp", time.toString(), "event-type", type.getValue(), "event-version", type.getSchema().getValue()));
  }
}
