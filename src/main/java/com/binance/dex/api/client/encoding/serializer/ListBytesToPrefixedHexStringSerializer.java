package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.EncodeUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListBytesToPrefixedHexStringSerializer extends JsonSerializer<List<byte[]>> {

    @Override
    public void serialize(List<byte[]> bytesList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bytesList != null){
            List<String> list = bytesList.stream().map(item -> {
                if (item != null) {
                    return EncodeUtils.bytesToPrefixHex(item);
                }
                return "";
            }).collect(Collectors.toList());
            jsonGenerator.writeObject(list);
        }
    }
}
