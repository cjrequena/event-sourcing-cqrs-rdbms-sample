package com.cjrequena.sample;

import com.cjrequena.sample.command.CreateBankAccountCommand;
import com.cjrequena.sample.db.entity.eventstore.BankAccountCratedEventEntity;
import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import com.cjrequena.sample.mapper.BankAccountMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@Log4j2
@SpringBootApplication
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

  @Override public void run(String... args) throws Exception {
    CreateBankAccountCommand command = new CreateBankAccountCommand();
    command.setAggregateId(UUID.randomUUID());
    BankAccountCratedEventEntity event = mapper.toEntity(command);
    log.debug(event);
  }
}
