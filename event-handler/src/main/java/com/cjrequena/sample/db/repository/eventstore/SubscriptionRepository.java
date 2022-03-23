package com.cjrequena.sample.db.repository.eventstore;

import com.cjrequena.sample.db.entity.eventstore.SubscriptionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, UUID> {

  @Modifying
  @Query(value = "INSERT INTO SUBSCRIPTION "
    + " (ID, CONSUMER_GROUP, OFFSET_EVENT) "
    + " VALUES (:#{#subscription.id}, :#{#subscription.consumerGroup}, :#{#subscription.offsetEvent})"
    , nativeQuery = true)
  void createSubscription(SubscriptionEntity subscription);

  @Modifying
  @Query(value = "UPDATE SUBSCRIPTION "
    + " SET OFFSET_EVENT = :offset "
    + " WHERE CONSUMER_GROUP = :consumerGroup",
    nativeQuery = true)
  void updateSubscription(@Param("consumerGroup") String consumerGroup, @Param("offset") Integer offset);

  @Query(value = "SELECT OFFSET_EVENT "
    + " FROM SUBSCRIPTION "
    + " WHERE CONSUMER_GROUP = :consumerGroup "
    + " FOR UPDATE NOWAIT",
    nativeQuery = true)
  Integer retrieveAndLockSubscriptionOffset(@Param("consumerGroup") String consumerGroup);

  @Query(value = "SELECT CASE "
    + " WHEN COUNT(S) > 0 THEN true ELSE false END "
    + " FROM SUBSCRIPTION S "
    + " WHERE S.CONSUMER_GROUP = :consumerGroup",
    nativeQuery = true
  )
  boolean checkSubscriptions(@Param("consumerGroup") String consumerGroup);
}
