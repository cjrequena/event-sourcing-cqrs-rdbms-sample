package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.event.BankAccountCratedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class BankAccountCratedEventConverter implements AttributeConverter<BankAccountCratedEvent, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void BankAccountCratedEventConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(BankAccountCratedEvent event) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public BankAccountCratedEvent convertToEntityAttribute(String data) {
    BankAccountCratedEvent event = new BankAccountCratedEvent();
    try {
      event = objectMapper.readValue(data, BankAccountCratedEvent.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return event;
  }
}
