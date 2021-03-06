package com.cjrequena.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class CommandHandlerMainApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(CommandHandlerMainApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  }
}
