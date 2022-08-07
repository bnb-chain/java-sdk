package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.HashTimerLockTransfer;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FHtlTransferConverter extends TransactionConverter<HashTimerLockTransfer> {

    @Override
    public TxType getType() {
        return TxType.HTL_TRANSFER;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));
        transaction.setToAddr(transactionV2.getToAddr());

        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        map.put("from", transactionV2.getFromAddr());
        map.put("to", transactionV2.getToAddr());
        transaction.setData(JsonUtil.toJson(map));
    }
}
