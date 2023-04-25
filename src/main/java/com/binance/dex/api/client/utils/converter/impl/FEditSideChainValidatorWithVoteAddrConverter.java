package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.stake.sidechain.EditSideChainValidator;
import com.binance.dex.api.client.domain.stake.sidechain.EditSideChainValidatorWithVoteAddr;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FEditSideChainValidatorWithVoteAddrConverter extends TransactionConverter<EditSideChainValidatorWithVoteAddr> {

    @Override
    public TxType getType() {
        return TxType.EDIT_SIDECHAIN_VALIDATOR_WITH_VOTE_ADDR;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        return;
    }
}
