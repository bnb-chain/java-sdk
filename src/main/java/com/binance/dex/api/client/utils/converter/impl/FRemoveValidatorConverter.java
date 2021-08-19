package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.RemoveValidator;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FRemoveValidatorConverter extends TransactionConverter<RemoveValidator> {

    @Override
    public TxType getType() {
        return TxType.REMOVE_VALIDATOR;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setData(null);
    }
}
