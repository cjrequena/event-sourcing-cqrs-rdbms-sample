package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.dto.BankAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class BankAccountDataConverter implements AttributeConverter<BankAccountDTO, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void BankAccountDataConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(BankAccountDTO dto) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(dto);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public BankAccountDTO convertToEntityAttribute(String data) {
    BankAccountDTO dto = new BankAccountDTO();
    try {
      dto = objectMapper.readValue(data, BankAccountDTO.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return dto;
  }
}
