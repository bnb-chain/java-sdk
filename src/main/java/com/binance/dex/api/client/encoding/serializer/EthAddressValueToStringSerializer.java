package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class EthAddressValueToStringSerializer extends JsonSerializer<EthAddressValue> {
    @Override
    public void serialize(EthAddressValue ethAddressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (ethAddressValue != null && ethAddressValue.getAddress() != null){
            jsonGenerator.writeString(ethAddressValue.getAddress());
        }
    }
}
