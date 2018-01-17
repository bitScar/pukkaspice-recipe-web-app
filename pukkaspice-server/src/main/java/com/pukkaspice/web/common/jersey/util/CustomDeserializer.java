package com.pukkaspice.web.common.jersey.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class CustomDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
        String tmp = jp.getText(); // {

        return tmp;
	}
}