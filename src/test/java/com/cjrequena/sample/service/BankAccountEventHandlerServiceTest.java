package com.cjrequena.sample.service;

import com.cjrequena.sample.configuration.SubscriptionConfiguration;
import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.db.repository.eventstore.SubscriptionRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class BankAccountEventHandlerServiceTest {

  @InjectMocks
  BankAccountEventHandlerService bankAccountEventHandlerService;
  @Mock
  SubscriptionRepository subscriptionRepository;
  @Mock
  BankAccountEventStoreService bankAccountEventStoreService;
  @Mock
  BankAccountRepository bankAccountRepository;
  @Mock
  BankAccountMapper bankAccountMapper;
  @Mock
  SubscriptionConfiguration subscriptionConfiguration;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void listener() {
  }

  @Test
  void process() {
  }
}
