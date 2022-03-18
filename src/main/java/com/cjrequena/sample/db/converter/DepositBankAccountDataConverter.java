package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.dto.DepositBankAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class DepositBankAccountDataConverter implements AttributeConverter<DepositBankAccountDTO, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void BankAccountDataConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(DepositBankAccountDTO dto) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(dto);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public DepositBankAccountDTO convertToEntityAttribute(String data) {
    DepositBankAccountDTO dto = new DepositBankAccountDTO();
    try {
      dto = objectMapper.readValue(data, DepositBankAccountDTO.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return dto;
  }
}
