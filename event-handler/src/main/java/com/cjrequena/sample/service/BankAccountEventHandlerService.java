package com.cjrequena.sample.service;

import com.cjrequena.sample.aggregate.BankAccountAggregate;
import com.cjrequena.sample.configuration.SubscriptionConfiguration;
import com.cjrequena.sample.db.entity.BankAccountEntity;
import com.cjrequena.sample.db.entity.eventstore.SubscriptionEntity;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.db.repository.eventstore.SubscriptionRepository;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BankAccountEventHandlerService {

  private final SubscriptionRepository subscriptionRepository;
  private final BankAccountEventStoreService bankAccountEventStoreService;
  private final BankAccountRepository bankAccountRepository;
  private final BankAccountMapper bankAccountMapper;
  private final SubscriptionConfiguration subscriptionConfiguration;

  @Scheduled(
    fixedDelayString = "${subscription.fixed-delay-ms}",
    initialDelayString = "${subscription.initial-delay-ms}")
  public void listener() {
    if (!this.subscriptionRepository.checkSubscriptions(subscriptionConfiguration.getConsumerGroup())) {
      SubscriptionEntity entity = new SubscriptionEntity();
      entity.setId(UUID.randomUUID());
      entity.setConsumerGroup(subscriptionConfiguration.getConsumerGroup());
      entity.setOffsetEvent(0);
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
    bankAccountEntity.setVersion(bankAccountAggregate.getVersion());
    this.bankAccountRepository.save(bankAccountEntity);
  }
}
