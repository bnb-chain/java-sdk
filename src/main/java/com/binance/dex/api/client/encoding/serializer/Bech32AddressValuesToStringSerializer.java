package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Bech32AddressValuesToStringSerializer extends JsonSerializer<List<Bech32AddressValue>> {

    @Override
    public void serialize(List<Bech32AddressValue> bech32AddressValues, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bech32AddressValues != null) {
            List<String> msg = bech32AddressValues.stream().map(value -> {
                if (value.getRaw() != null && StringUtils.isNotBlank(value.getHrp())) {
                    return Crypto.encodeAddress(value.getHrp(), value.getRaw());
                } else {
                    throw new RuntimeException("raw and hrp must not be empty");
                }
            }).collect(Collectors.toList());
            jsonGenerator.writeObject(msg);
        }
    }
}
