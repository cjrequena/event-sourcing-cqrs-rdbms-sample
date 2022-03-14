package com.cjrequena.sample.web.controller;

import com.cjrequena.sample.service.BankAccountCommandService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class BankAccountCommandControllerTest {

  @InjectMocks
  BankAccountCommandController bankAccountCommandController;
  @Mock
  BankAccountCommandService bankAccountCommandService;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void create() {
  }

  @Test
  void deposit() {
  }

  @Test
  void withdraw() {
  }
}
