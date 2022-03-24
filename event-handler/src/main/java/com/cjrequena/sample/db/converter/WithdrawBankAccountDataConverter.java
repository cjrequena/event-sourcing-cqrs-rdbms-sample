package com.cjrequena.sample.db.converter;

import com.cjrequena.sample.dto.WithdrawBankAccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class WithdrawBankAccountDataConverter implements AttributeConverter<WithdrawBankAccountDTO, String> {

  private static ObjectMapper objectMapper;

  @Autowired
  public void WithdrawBankAccountDataConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(WithdrawBankAccountDTO dto) {
    String json = "";
    try {
      json = objectMapper.writeValueAsString(dto);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }
    return json;
  }

  @Override
  public WithdrawBankAccountDTO convertToEntityAttribute(String data) {
    WithdrawBankAccountDTO dto = new WithdrawBankAccountDTO();
    try {
      dto = objectMapper.readValue(data, WithdrawBankAccountDTO.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return dto;
  }
}
