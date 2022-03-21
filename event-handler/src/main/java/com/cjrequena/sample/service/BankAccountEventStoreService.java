package com.cjrequena.sample.service;

import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.event.Event;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountEventStoreService {

  private final BankAccountEventRepository bankAccountEventRepository;
  private final BankAccountMapper bankAccountMapper;


  @Transactional(readOnly = true)
  public List<Event> retrieveEventsByAggregateId(UUID aggregateId) {
    return this.bankAccountEventRepository.retrieveEventsByAggregateId(aggregateId).stream().map(entity -> bankAccountMapper.toEvent(entity)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<Event> retrieveEventsAfterOffset(Integer offset) {
    return this.bankAccountEventRepository.retrieveEventsAfterOffset(offset).stream().map(entity -> bankAccountMapper.toEvent(entity)).collect(Collectors.toList());
  }

}
