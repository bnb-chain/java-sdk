package com.binance.dex.api.client.encoding;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class VoteOptionSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer option, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String optionV = "";
        if(option != null){
            switch (option){
                case 1 : optionV = "Yes";break;
                case 2 : optionV = "Abstain";break;
                case 3 : optionV = "No";break;
                case 4 : optionV = "NoWithVeto";break;
                default : optionV = "";
            }
        }
        jsonGenerator.writeString(optionV);
    }
}
