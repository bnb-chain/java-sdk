package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.ClaimHashTimerLock;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FClaimHtlConverter extends TransactionConverter<ClaimHashTimerLock> {

    @Override
    public TxType getType() {
        return TxType.CLAIM_HTL;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setData(null);
    }
}
