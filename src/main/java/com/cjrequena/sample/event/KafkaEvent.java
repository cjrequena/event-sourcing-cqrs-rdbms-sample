package com.cjrequena.sample.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.kafka.support.KafkaNull;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public class KafkaEvent<T> extends ApplicationEvent {

  private final Map<String, Object> headers;
  private String channel;

  public KafkaEvent(T source) {
    super(source != null ? source : KafkaNull.INSTANCE);
    this.headers = new HashMap<>();
  }

  public KafkaEvent(T source, String channel) {
    super(source != null ? source : KafkaNull.INSTANCE);
    this.headers = new HashMap<>();
    this.channel = channel;
  }

  public void addHeader(String key, Object value) {
    headers.put(key, value);
  }

  public Map<String, Object> getHeaders() {
    return new HashMap<>(headers);
  }

  public String getChannel() {
    return this.channel;
  }
}
