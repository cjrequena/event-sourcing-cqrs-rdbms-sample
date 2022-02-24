package com.cjrequena.sample.db.repository.eventstore;

import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Repository
public interface AggregateRepository extends CrudRepository<AggregateEntity, UUID> {

  boolean existsByIdAndName(@Param("id") UUID id, @Param("name") String name);

  @Query(value = "SELECT CASE WHEN  VERSION = :#{#aggregate.version} THEN True ELSE False END AS TEST FROM AGGREGATE WHERE ID=:#{#aggregate.id}", nativeQuery = true)
  boolean checkAggregateVersion(@Param("aggregate") AggregateEntity aggregateEntity);

}
