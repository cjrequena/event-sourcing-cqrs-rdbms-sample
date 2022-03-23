package com.cjrequena.sample.db.entity.eventstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "consumer_group")
  private String consumerGroup;

  @Column(name = "offset_event")
  private int offsetEvent;
}
