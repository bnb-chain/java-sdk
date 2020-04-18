package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.Bech32;
import com.binance.dex.api.client.encoding.message.sidechain.value.AddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class ValAddressValueToStringSerializer extends JsonSerializer<AddressValue> {

    @Override
    public void serialize(AddressValue addressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (addressValue != null && addressValue.getAddress() != null){
            Bech32.Bech32Data bech32Data = Bech32.decode(addressValue.getAddress());
            jsonGenerator.writeString(Bech32.encode("bva", bech32Data.getData()));
        }
    }
}
