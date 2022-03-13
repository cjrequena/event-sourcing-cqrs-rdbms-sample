package com.cjrequena.sample;

import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@SpringBootApplication
@EnableScheduling
public class MainApplication implements CommandLineRunner {

  @Autowired
  BankAccountEventRepository bankAccountEventRepository;

  @Autowired
  AggregateRepository aggregateRepository;

  @Autowired
  private BankAccountMapper mapper;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  }
}
