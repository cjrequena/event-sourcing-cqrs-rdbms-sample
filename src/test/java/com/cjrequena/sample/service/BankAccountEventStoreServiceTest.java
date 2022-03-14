package com.cjrequena.sample.service;

import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class BankAccountEventStoreServiceTest {

  @InjectMocks
  BankAccountEventStoreService bankAccountEventStoreService;
  @Mock
  AggregateRepository aggregateRepository;
  @Mock
  BankAccountEventRepository bankAccountEventRepository;
  @Mock
  BankAccountMapper bankAccountMapper;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void appendEvent() {
  }

  @Test
  void retrieveEventsByAggregateId() {
  }

  @Test
  void retrieveEventsAfterOffset() {
  }
}
