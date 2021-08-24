package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.SetAccountFlag;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.HashMap;
import java.util.Map;


public class FSetAccountFlagsConverter extends TransactionConverter<SetAccountFlag> {

    @Override
    public TxType getType() {
        return TxType.SetAccountFlag;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        Long flags = ((Number) map.get("flags")).longValue();

        Map<String, Object> newMap = new HashMap<>();
        newMap.put("fromAddr", transactionV2.getFromAddr());
        newMap.put("flags", flags);
        transaction.setData(JsonUtil.toJson(newMap));
    }
}
