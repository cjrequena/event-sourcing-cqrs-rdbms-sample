package com.cjrequena.sample.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("subscription")
public class SubscriptionConfiguration {

  private boolean enabled;
  private String consumerGroup;
  private String initialDelayMs;
  private String fixedDelayMs;
}
