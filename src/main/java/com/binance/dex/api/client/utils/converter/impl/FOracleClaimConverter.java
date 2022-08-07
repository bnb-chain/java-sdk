package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.oracle.ClaimMsg;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FOracleClaimConverter extends TransactionConverter<ClaimMsg> {

    @Override
    public TxType getType() {
        return TxType.CLAIM;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setData(transactionV2.getData());
    }
}
