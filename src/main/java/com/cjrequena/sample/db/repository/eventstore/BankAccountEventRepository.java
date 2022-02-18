package com.cjrequena.sample.db.repository.eventstore;

import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.entity.eventstore.EventEntity;
import org.springframework.data.repository.CrudRepository;
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
public interface BankAccountEventRepository extends CrudRepository<EventEntity, UUID>, QueryByExampleExecutor<BankAccountCratedEventEntity> {
  List<BankAccountCratedEventEntity> findByAggregateId(UUID aggregateId);
}
