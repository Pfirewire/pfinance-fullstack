package com.pfinance.pfinancefullstack.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonPrint {
    ObjectMapper mapper;

    public JsonPrint() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public void object(Object object) throws JsonProcessingException {
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
    }

}
