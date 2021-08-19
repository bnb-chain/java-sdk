package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TinyTokenIssue;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FTinyTokenIssueConverter extends TransactionConverter<TinyTokenIssue> {

    @Override
    public TxType getType() {
        return TxType.TINY_TOKEN_ISSUE;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        Long totalSupply = (Long) map.get("totalSupply");
        transaction.setValue(NumberUtil.longToBigDecimalString(totalSupply));

        transaction.setData(null);
    }
}
