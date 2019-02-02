package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class OrderBookEntrySerializer extends JsonSerializer<OrderBookEntry> {
    @Override
    public void serialize(OrderBookEntry orderBookEntry, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(orderBookEntry.getPrice());
        gen.writeString(orderBookEntry.getQuantity());
        gen.writeEndArray();
    }
}
