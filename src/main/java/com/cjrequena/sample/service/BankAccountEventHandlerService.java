package com.cjrequena.sample.service;

import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.db.entity.eventstore.SubscriptionEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.db.repository.eventstore.SubscriptionRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 *
 */
@Slf4j
@Component
@Transactional
public class BankAccountEventHandlerService {

  private AggregateRepository aggregateRepository;
  private BankAccountEventRepository bankAccountEventRepository;
  private SubscriptionRepository subscriptionRepository;
  private BankAccountMapper bankAccountMapper;

  @Autowired
  public BankAccountEventHandlerService(BankAccountEventRepository bankAccountEventRepository, AggregateRepository aggregateRepository,
    SubscriptionRepository subscriptionRepository, BankAccountMapper bankAccountMapper) {
    this.bankAccountEventRepository = bankAccountEventRepository;
    this.aggregateRepository = aggregateRepository;
    this.subscriptionRepository = subscriptionRepository;
    this.bankAccountMapper = bankAccountMapper;
  }

  @Scheduled(
    fixedDelayString = "10000",
    initialDelayString = "10000")
  public void listener() {
    log.debug(Thread.currentThread().getName());
    SubscriptionEntity entity = new SubscriptionEntity();
    entity.setId(UUID.randomUUID());
    entity.setConsumerGroup("CONSUMER-GROUP-1");
    entity.setAggregateName(Constants.BANK_ACCOUNT_AGGREGATE_NAME);
    entity.setOffsetEvent(0);
    if (!this.subscriptionRepository.existsByConsumerGroupAndAggregateName(entity.getConsumerGroup(), entity.getAggregateName())) {
      this.subscriptionRepository.createSubscription(entity);
    }
  }
}
