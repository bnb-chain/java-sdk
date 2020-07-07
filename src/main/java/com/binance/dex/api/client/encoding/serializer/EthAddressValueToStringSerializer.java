package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class EthAddressValueToStringSerializer extends StdSerializer<EthAddressValue> {

    protected EthAddressValueToStringSerializer(){
        this(null);
    }

    protected EthAddressValueToStringSerializer(Class<EthAddressValue> t) {
        super(t);
    }

    @Override
    public void serialize(EthAddressValue ethAddressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (ethAddressValue != null && ethAddressValue.getAddress() != null){
            jsonGenerator.writeString(ethAddressValue.getAddress());
        }
    }
}
