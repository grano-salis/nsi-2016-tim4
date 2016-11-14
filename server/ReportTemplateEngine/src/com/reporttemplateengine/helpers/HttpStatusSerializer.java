package com.reporttemplateengine.helpers;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class HttpStatusSerializer extends JsonSerializer<HttpStatus> {

    @Override
    public void serialize(HttpStatus status, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
			jsonGenerator.writeBoolean(status.equals(HttpStatus.OK));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
