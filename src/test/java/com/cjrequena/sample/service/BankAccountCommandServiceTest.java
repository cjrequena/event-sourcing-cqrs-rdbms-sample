package com.cjrequena.sample.service;

import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BankAccountCommandServiceTest {

  @InjectMocks
  BankAccountCommandService bankAccountCommandService;
  @Mock
  BankAccountEventStoreService bankAccountEventStoreService;
  @Mock
  BankAccountMapper bankAccountMapper;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void handler() {
  }

  @Test
  void process() {
    log.debug("--");
  }

  @Test
  void testProcess() {
  }

  @Test
  void testProcess1() {
  }

  @Test
  void testProcess2() {
  }
}
