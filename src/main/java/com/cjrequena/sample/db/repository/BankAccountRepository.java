package com.cjrequena.sample.db.repository;


import com.cjrequena.sample.db.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID>, PagingAndSortingRepository<BankAccountEntity, UUID> {
}
