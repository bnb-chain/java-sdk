package com.binance.dex.api.client.encoding.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class ByteArrayToStringSerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] bytes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(bytes != null){
            jsonGenerator.writeString(new String(bytes));
        }
    }

}
