package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class Bech32AddressValueToStringSerializer extends JsonSerializer<Bech32AddressValue> {

    @Override
    public void serialize(Bech32AddressValue addressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (addressValue != null && addressValue.getHrp() != null && addressValue.getRaw() != null){
            jsonGenerator.writeString(Crypto.encodeAddress(addressValue.getHrp(), addressValue.getRaw()));
        }
    }
}