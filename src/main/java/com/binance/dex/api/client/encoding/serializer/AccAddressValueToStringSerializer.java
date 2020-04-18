package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.message.sidechain.value.AddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class AccAddressValueToStringSerializer extends JsonSerializer<AddressValue> {

    @Override
    public void serialize(AddressValue addressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (addressValue != null && addressValue.getAddress() != null){
            jsonGenerator.writeString(addressValue.getAddress());
        }
    }
}