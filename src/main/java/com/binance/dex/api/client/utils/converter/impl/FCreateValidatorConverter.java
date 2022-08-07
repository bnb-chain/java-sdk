package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.CreateValidator;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FCreateValidatorConverter extends TransactionConverter<CreateValidator> {

    @Override
    public TxType getType() {
        return TxType.CREATE_VALIDATOR;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));
        transaction.setData(transactionV2.getData());
    }
}
