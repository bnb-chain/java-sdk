package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.bridge.Bind;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FCrossBindConverter extends TransactionConverter<Bind> {

    @Override
    public TxType getType() {
        return TxType.BIND;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));

        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        map.put("symbol", transactionV2.getAsset());
        map.put("from", transactionV2.getFromAddr());
        map.put("amount", transactionV2.getAmount());

        transaction.setData(JsonUtil.toJson(map));

    }
}
