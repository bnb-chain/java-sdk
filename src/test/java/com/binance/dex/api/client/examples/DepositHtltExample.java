package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.encoding.message.Token;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-10-09
 */
public class DepositHtltExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String yourMnemonicCode = "";
        Wallet wallet = Wallet.createWalletFromMnemonicCode(Arrays.asList(yourMnemonicCode.split(" ")),BinanceDexEnvironment.TEST_NET);

        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        Token token = new Token();
        token.setDenom("CNN-210");
        token.setAmount(100L);
        List<Token> amount = Collections.singletonList(token);

        String swapId = ""; // the swap to be deposit

        List<TransactionMetadata> resp = client.depositHtlt(swapId,amount,wallet, TransactionOption.DEFAULT_INSTANCE,true);
        System.out.println(resp.get(0).toString());
    }

}
