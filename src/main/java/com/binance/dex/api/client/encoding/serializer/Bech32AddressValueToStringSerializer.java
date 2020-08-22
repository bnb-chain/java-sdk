package com.binance.dex.api.client.encoding.serializer;

import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class Bech32AddressValueToStringSerializer extends StdSerializer<Bech32AddressValue> {

    protected Bech32AddressValueToStringSerializer(){
        this(null);
    }

    protected Bech32AddressValueToStringSerializer(Class<Bech32AddressValue> t) {
        super(t);
    }

    @Override
    public void serialize(Bech32AddressValue addressValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (addressValue != null && addressValue.getRaw() != null) {
            if (StringUtils.isNotBlank(addressValue.getHrp())){
                jsonGenerator.writeString(Crypto.encodeAddress(addressValue.getHrp(), addressValue.getRaw()));
            } else {
                jsonGenerator.writeString(EncodeUtils.bytesToHex(addressValue.getRaw()));
            }
        }
    }
}