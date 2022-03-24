package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.event.BankAccountWithdrawnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class BankAccountWithdrawnEventConverter implements AttributeConverter<BankAccountWithdrawnEvent, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void BankAccountWithdrawnEventConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
  @Override
  public String convertToDatabaseColumn(BankAccountWithdrawnEvent event) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public BankAccountWithdrawnEvent convertToEntityAttribute(String data) {
    BankAccountWithdrawnEvent event = new BankAccountWithdrawnEvent();
    try {
      event = objectMapper.readValue(data, BankAccountWithdrawnEvent.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return event;
  }
}
