package com.cjrequena.sample.db.repository;

import com.cjrequena.sample.db.entity.eventstore.AggregateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Repository
public interface AggregateRepository extends JpaRepository<AggregateEntity, UUID> {
}
