package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.stake.sidechain.CreateSideChainValidator;
import com.binance.dex.api.client.domain.stake.sidechain.CreateSideChainValidatorWithVoteAddr;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;


public class FCreateSideChainValidatorWithVoteAddrConverter extends TransactionConverter<CreateSideChainValidatorWithVoteAddr> {

    @Override
    public TxType getType() {
        return TxType.CREATE_SIDECHAIN_VALIDATOR_WITH_VOTE_ADDR;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));
    }
}
