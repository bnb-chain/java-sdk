package com.binance.dex.api.client.encoding.message;


import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Ignore("Manual run only")
public class TestWallet {

    private Wallet wallet =
            new Wallet("0f652de68eb8048951c753303f6a3a22114ff7d4418ecbe1711d5d6a02b50522", BinanceDexEnvironment.TEST_NET);
    private BinanceDexApiRestClient client =
            BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

    @Test
    public void testNewOrder() throws IOException, NoSuchAlgorithmException {
        String symbol = "ADA.B-F2F_BNB";
        NewOrder no = new NewOrder();
        no.setTimeInForce(TimeInForce.GTE);
        no.setOrderType(OrderType.LIMIT);
        no.setSide(OrderSide.BUY);
        no.setPrice("10.7");
        no.setQuantity("100.0");
        no.setSymbol(symbol);

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        List<TransactionMetadata> resp = client.newOrder(no, wallet, options, true);
        System.out.println(resp.get(0));
    }

    @Test
    public void testCancelOrder() throws IOException, NoSuchAlgorithmException {
        String symbol = "ADA.B-F2F_BNB";
        CancelOrder co = new CancelOrder();
        co.setSymbol(symbol);
        co.setRefId("057685EE0E308A0CFF998BE45992A7FF62BFBE7D-13");
        List<TransactionMetadata> resp = client.cancelOrder(co, wallet, TransactionOption.DEFAULT_INSTANCE, true);
        System.out.println(resp.get(0));
    }

    @Test
    public void testTransfer() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        transfer.setAmount("1.0");

        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = client.transfer(transfer, wallet, options, true);
        System.out.println(resp.get(0));
    }

    @Test
    public void testMultiTransfer() throws IOException, NoSuchAlgorithmException {
        List<Output> outputs = new ArrayList<>();

        List<OutputToken> outputTokens1 = new ArrayList<OutputToken>();
        outputTokens1.add(new OutputToken("BNB", "0.1"));
        outputTokens1.add(new OutputToken("XRP.B-585", "0.1"));
        Output o1 = new Output("tbnb1mrslq6lhglm3jp7pxzlk8u4549pmtp9sgvn2rc", outputTokens1);

        List<OutputToken> outputTokens2 = new ArrayList<OutputToken>();
        outputTokens2.add(new OutputToken("BNB", "0.1"));
        Output o2 = new Output("tbnb1sadf5e0gdpg757zefd6yru086cknjyttudg532", outputTokens2);

        outputs.add(o1);
        outputs.add(o2);

        MultiTransfer multiTransfer = new MultiTransfer(wallet.getAddress(), outputs);
        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = client.multiTransfer(multiTransfer, wallet, options, true);
        System.out.println(resp.get(0));
    }


    @Test
    public void testFreeze() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        TokenFreeze freeze = new TokenFreeze();
        freeze.setSymbol(symbol);
        freeze.setAmount("0.5");

        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = client.freeze(freeze, wallet, options, true);
        System.out.println(resp.get(0));
    }

    @Test
    public void testUnfreeze() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        TokenUnfreeze unfreeze = new TokenUnfreeze();
        unfreeze.setSymbol(symbol);
        unfreeze.setAmount("0.5");

        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = client.unfreeze(unfreeze, wallet, options, true);
        System.out.println(resp.get(0));
    }
}
