package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class TokenSymbolSerializer extends StdSerializer<TokenSymbol> {

    protected TokenSymbolSerializer(){
        this(null);
    }

    protected TokenSymbolSerializer(Class<TokenSymbol> t) {
        super(t);
    }

    @Override
    public void serialize(TokenSymbol tokenSymbol, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (tokenSymbol != null && StringUtils.isNotBlank(tokenSymbol.getSymbol())){
            jsonGenerator.writeString(tokenSymbol.getSymbol());
        }
    }
}
