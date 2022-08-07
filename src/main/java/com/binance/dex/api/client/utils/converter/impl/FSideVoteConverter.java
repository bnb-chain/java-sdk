package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.SideVote;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.TransactionConverter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FSideVoteConverter extends TransactionConverter<SideVote> {

    @Override
    public TxType getType() {
        return TxType.SIDE_VOTE;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        return;
    }
}
