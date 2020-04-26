package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.message.common.Dec;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class DecToStringSerializer extends JsonSerializer<Dec> {
    @Override
    public void serialize(Dec dec, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (dec != null) {
            jsonGenerator.writeString(String.valueOf(dec.getValue()));
        }
    }
}
