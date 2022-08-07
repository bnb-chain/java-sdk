package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.TransferTokenOwnership;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.HashMap;
import java.util.Map;


public class FTransferOwnershipConverter extends TransactionConverter<TransferTokenOwnership> {

    @Override
    public TxType getType() {
        return TxType.TRANSFER_TOKEN_OWNERSHIP;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = new HashMap<>();
        map.put("from", transactionV2.getFromAddr());
        map.put("newOwner", transactionV2.getToAddr());
        map.put("symbol", transactionV2.getAsset());

        transaction.setData(JsonUtil.toJson(map));
    }
}

