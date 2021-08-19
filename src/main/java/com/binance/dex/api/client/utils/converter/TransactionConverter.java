package com.binance.dex.api.client.utils.converter;


import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;

import java.util.Map;


public abstract class TransactionConverter<T> {

    // construct tx data information based on different tx types
    public abstract void doConvert(TransactionV2 transactionV2, Transaction transaction);

    public abstract com.binance.dex.api.client.domain.broadcast.TxType getType();

    protected Token getToken(Map<String, Object> map, String key) {
        Map<String, Object> m = (Map<String, Object>) map.get(key);
        String denom = m.get("denom").toString();
        Long amount = ((Number) m.get("amount")).longValue();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(amount);
        return token;
    }

}