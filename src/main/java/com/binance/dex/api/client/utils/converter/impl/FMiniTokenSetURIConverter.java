package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.MiniTokenSetURI;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FMiniTokenSetURIConverter extends TransactionConverter<MiniTokenSetURI> {

    @Override
    public TxType getType() {
        return TxType.MINI_TOKEN_SET_URI;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setData(null);
    }
}
