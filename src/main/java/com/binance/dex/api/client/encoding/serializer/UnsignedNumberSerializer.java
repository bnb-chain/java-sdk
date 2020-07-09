package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UnsignedNumberSerializer extends StdSerializer<UnsignedNumber> {

    protected UnsignedNumberSerializer() {
        this(null);
    }


    protected UnsignedNumberSerializer(Class<UnsignedNumber> t) {
        super(t);
    }

    @Override
    public void serialize(UnsignedNumber unsignedNumber, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(unsignedNumber.getNumber());
    }
}
