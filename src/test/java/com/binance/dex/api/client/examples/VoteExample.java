package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Vote;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VoteExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        List<String> words = Lists.newArrayList("bench","sad","staff","demise","sand","firm",
                "oxygen","term","insane","teach","squeeze","measure",
                "unusual","spirit","entire","grape","juice","need",
                "pair","culture","club","soldier","tank","front");
        Wallet wallet = Wallet.createWalletFromMnemonicCode(words,BinanceDexEnvironment.TEST_NET);

//        Wallet wallet =
//                new Wallet("0f652de68eb8048951c753303f6a3a22114ff7d4418ecbe1711d5d6a02b50522",
//                        BinanceDexEnvironment.TEST_NET);
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        Vote vote = new Vote();
        vote.setOption(3);
        vote.setProposalId(344L);
        List<TransactionMetadata> resp = client.vote(vote,wallet,options,true);
        System.out.println(resp.get(0));
    }

}
