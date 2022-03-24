package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.event.BankAccountDepositedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class BankAccountDepositedEventConverter implements AttributeConverter<BankAccountDepositedEvent, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void BankAccountDepositedEventConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(BankAccountDepositedEvent event) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public BankAccountDepositedEvent convertToEntityAttribute(String data) {
    BankAccountDepositedEvent event = new BankAccountDepositedEvent();
    try {
      event = objectMapper.readValue(data, BankAccountDepositedEvent.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return event;
  }
}
