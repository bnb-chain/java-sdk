package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TimeUnlock;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FTimeUnlockConverter extends TransactionConverter<TimeUnlock> {

    @Override
    public TxType getType() {
        return TxType.TimeUnlock;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        return;
    }
}
