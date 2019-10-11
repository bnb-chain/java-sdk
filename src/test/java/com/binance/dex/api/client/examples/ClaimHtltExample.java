package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-10-09
 */
public class ClaimHtltExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String yourPrivateKey = "";
        Wallet wallet =
                new Wallet(yourPrivateKey,
                        BinanceDexEnvironment.TEST_NET);
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        String swapId = ""; // the swap to be claimed
        byte[] randomNumber = new byte[32]; // put in your randomNumber

        List<TransactionMetadata> resp = client.claimHtlt(swapId,randomNumber,wallet, TransactionOption.DEFAULT_INSTANCE,true);
        System.out.println(resp.get(0).toString());
    }

}
