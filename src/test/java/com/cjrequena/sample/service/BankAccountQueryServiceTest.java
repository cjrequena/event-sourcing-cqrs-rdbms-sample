package com.cjrequena.sample.service;

import com.cjrequena.sample.db.repository.BankAccountRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class BankAccountQueryServiceTest {

  @InjectMocks
  BankAccountQueryService bankAccountQueryService;
  @Mock
  BankAccountRepository bankAccountRepository;
  @Mock
  BankAccountMapper bankAccountMapper;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void retrieveById() {
  }

  @Test
  void retrieve() {
  }
}
