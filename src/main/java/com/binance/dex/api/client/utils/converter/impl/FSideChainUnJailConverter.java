package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.slash.SideChainUnJail;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FSideChainUnJailConverter extends TransactionConverter<SideChainUnJail> {

    @Override
    public TxType getType() {
        return TxType.SIDECHAIN_UNJAIL;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        return;
    }
}
