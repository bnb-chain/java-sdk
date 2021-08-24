package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TimeLock;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FTimeLockConverter extends TransactionConverter<TimeLock> {

    @Override
    public TxType getType() {
        return TxType.TimeLock;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));

        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);

        map.remove("amount");
        transaction.setData(JsonUtil.toJson(map));
    }
}
