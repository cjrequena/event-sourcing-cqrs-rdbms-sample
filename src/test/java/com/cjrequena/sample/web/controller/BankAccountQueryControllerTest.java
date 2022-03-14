package com.cjrequena.sample.web.controller;

import com.cjrequena.sample.service.BankAccountQueryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class BankAccountQueryControllerTest {

  @InjectMocks
  BankAccountQueryController bankAccountQueryController;
  @Mock
  BankAccountQueryService bankAccountQueryService;


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
