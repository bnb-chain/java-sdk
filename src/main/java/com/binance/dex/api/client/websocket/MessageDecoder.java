package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public class MessageDecoder implements Decoder.Text<JsonRpcResponse> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonRpcResponse decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s,JsonRpcResponse.class);
        } catch (IOException e) {
            throw new DecodeException(s,e.getMessage(),e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
