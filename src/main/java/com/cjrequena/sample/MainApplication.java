package com.cjrequena.sample;

import com.cjrequena.sample.db.repository.eventstore.AggregateRepository;
import com.cjrequena.sample.db.repository.eventstore.BankAccountEventRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

  @Autowired
  BankAccountEventRepository bankAccountEventRepository;

  @Autowired
  AggregateRepository aggregateRepository;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override public void run(String... args) throws Exception {
    //		UUID aggregateId = UUID.randomUUID();
    //		AggregateEntity aggregateEntity = new AggregateEntity();
    //		aggregateEntity.setId(aggregateId);
    //		aggregateEntity.setName("BankAccount");
    //		aggregateEntity.setVersion(1);
    //		aggregateRepository.save(aggregateEntity);
    //
    //		BankAccountCratedEventEntity entity = new BankAccountCratedEventEntity();
    //		entity.setId(UUID.randomUUID());
    //		entity.setAggregateId(aggregateId);
    //		entity.setType("BankAccountCreated");
    //		BankAccountDTO data = new BankAccountDTO();
    //		data.setId(aggregateId);
    //		data.setVersion(1);
    //		entity.setData(data);
    //		bankAccountEventRepository.save(entity);

  }
}
