package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Vote;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VoteExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Wallet wallet = Wallet.createRandomWallet(BinanceDexEnvironment.TEST_NET);
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        Vote vote = new Vote();
        vote.setOption(1);
        vote.setProposalId(347L);
        List<TransactionMetadata> resp = client.vote(vote,wallet,options,true);
        System.out.println(resp.get(0));
    }

}
