package com.cjrequena.sample.db.repository.eventstore;

import com.cjrequena.sample.db.entity.eventstore.BankAccountEventEntity;
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
public interface BankAccountEventRepository extends JpaRepository<BankAccountEventEntity, UUID>{
}
