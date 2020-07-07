package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.crosschain.Bep2TokenSymbol;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class Bep2TokenSymbolSerializer extends StdSerializer<Bep2TokenSymbol> {

    protected Bep2TokenSymbolSerializer(){
        this(null);
    }

    protected Bep2TokenSymbolSerializer(Class<Bep2TokenSymbol> t) {
        super(t);
    }

    @Override
    public void serialize(Bep2TokenSymbol bep2TokenSymbol, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bep2TokenSymbol != null && StringUtils.isNotBlank(bep2TokenSymbol.getSymbol())){
            jsonGenerator.writeString(bep2TokenSymbol.getSymbol());
        }
    }
}
