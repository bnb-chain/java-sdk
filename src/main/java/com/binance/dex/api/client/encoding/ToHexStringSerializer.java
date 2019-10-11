package com.binance.dex.api.client.encoding;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author: fletcher.fan
 * @create: 2019-10-09
 */
public class ToHexStringSerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] bytes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(bytes != null){
            jsonGenerator.writeString(EncodeUtils.bytesToHex(bytes));
        }

    }
}
