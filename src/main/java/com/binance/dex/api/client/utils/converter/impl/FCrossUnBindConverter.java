package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.bridge.Unbind;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.HashMap;
import java.util.Map;


public class FCrossUnBindConverter extends TransactionConverter<Unbind> {

    @Override
    public TxType getType() {
        return TxType.UNBIND;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("symbol", transactionV2.getAsset());
        newMap.put("from", transactionV2.getFromAddr());
        transaction.setData(JsonUtil.toJson(newMap));
    }
}
