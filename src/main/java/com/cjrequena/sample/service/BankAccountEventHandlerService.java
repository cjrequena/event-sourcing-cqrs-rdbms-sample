package com.cjrequena.sample.service;

import com.cjrequena.sample.aggregate.BankAccountAggregate;
import com.cjrequena.sample.common.Constants;
import com.cjrequena.sample.configuration.SubscriptionConfiguration;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.entity.eventstore.SubscriptionEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.db.repository.eventstore.SubscriptionRepository;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@ConditionalOnProperty(name = "subscription.enabled", havingValue = "true")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BankAccountEventHandlerService {

  private SubscriptionRepository subscriptionRepository;
  private BankAccountEventStoreService bankAccountEventStoreService;
  private BankAccountRepository bankAccountRepository;
  private BankAccountMapper bankAccountMapper;
  private final SubscriptionConfiguration subscriptionConfiguration;

  @Scheduled(
    fixedDelayString = "${subscription.fixed-delay-ms}",
    initialDelayString = "${subscription.initial-delay-ms}")
  public void listener() {
    SubscriptionEntity entity = new SubscriptionEntity();
    entity.setId(UUID.randomUUID());
    entity.setConsumerGroup(subscriptionConfiguration.getConsumerGroup());
    entity.setAggregateName(Constants.BANK_ACCOUNT_AGGREGATE_NAME);
    entity.setOffsetEvent(0);
    if (!this.subscriptionRepository.existsByConsumerGroupAndAggregateName(entity.getConsumerGroup(), entity.getAggregateName())) {
      this.subscriptionRepository.createSubscription(entity);
    }
    Integer offset = Optional.ofNullable(this.subscriptionRepository.retrieveAndLockSubscriptionOffset(subscriptionConfiguration.getConsumerGroup())).orElse(0);
    List<Event> events = this.bankAccountEventStoreService.retrieveEventsAfterOffset(offset);
    for (Event event : events) {
      process(event);
      this.subscriptionRepository.updateSubscription(subscriptionConfiguration.getConsumerGroup(), event.getOffset());
    }
  }

  public void process(Event event) {
    Objects.requireNonNull(event);
    log.debug("Processing event {}", event);
    List<Event> events = bankAccountEventStoreService.retrieveEventsByAggregateId(event.getAggregateId());
    BankAccountAggregate bankAccountAggregate = new BankAccountAggregate(event.getAggregateId(), events);
    BankAccountEntity bankAccountEntity = bankAccountMapper.toEntity(bankAccountAggregate.getBankAccountDTO());
    this.bankAccountRepository.save(bankAccountEntity);
  }
}
