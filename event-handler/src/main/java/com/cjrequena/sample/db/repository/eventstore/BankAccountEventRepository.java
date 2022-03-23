package com.cjrequena.sample.db.repository.eventstore;

import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Repository
public interface BankAccountEventRepository extends CrudRepository<EventEntity, UUID> {

  @Query(value = "SELECT * FROM EVENT WHERE AGGREGATE_ID = :aggregateId ORDER BY VERSION ASC", nativeQuery = true)
  List<EventEntity> retrieveEventsByAggregateId(@Param("aggregateId") UUID aggregateId);

  @Query(value = "SELECT * FROM EVENT WHERE OFFSET_EVENT > :offset ORDER BY OFFSET_EVENT ASC", nativeQuery = true)
  List<EventEntity> retrieveEventsAfterOffset(@Param("offset") Integer offset);


}
