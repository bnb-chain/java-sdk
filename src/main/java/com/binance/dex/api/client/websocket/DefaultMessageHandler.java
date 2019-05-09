package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.TransactionConverter;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.proto.Send;
import com.binance.dex.api.proto.StdTx;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.MessageHandler;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMessageHandler implements MessageHandler.Whole<String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private TransactionConverter transactionConverter;
    DefaultMessageHandler(String hrp){
        this.transactionConverter = new TransactionConverter(hrp);
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonRpcResponse response = objectMapper.readValue(message, JsonRpcResponse.class);
            if(response.getId().startsWith("TX")){
                BlockInfoResult blockInfoResult =  objectMapper.readValue(objectMapper.writeValueAsString(response.getResult()),BlockInfoResult.class);
                List<Transaction> transactions = blockInfoResult.getTxs().stream()
                        .map(transactionConverter::convert)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
            }else{
                System.out.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
