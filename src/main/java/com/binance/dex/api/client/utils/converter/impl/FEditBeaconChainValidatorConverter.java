package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.stake.beaconchain.EditBeaconChainValidator;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FEditBeaconChainValidatorConverter extends TransactionConverter<EditBeaconChainValidator> {

    @Override
    public TxType getType() {
        return TxType.EDIT_BEACONCHAIN_VALIDATOR;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        return;
    }
}
