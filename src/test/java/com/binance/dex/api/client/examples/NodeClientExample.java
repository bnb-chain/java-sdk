package com.binance.dex.api.client.examples;


import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.BlockMeta;
import com.binance.dex.api.client.domain.Infos;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class NodeClientExample {

    private BinanceDexApiNodeClient binanceDexNodeApi = null;
    private Wallet wallet =
            new Wallet("0f652de68eb8048951c753303f6a3a22114ff7d4418ecbe1711d5d6a02b50522",
                    BinanceDexEnvironment.TEST_NET);

    @Before
    public void setup() {
        binanceDexNodeApi = BinanceDexApiClientFactory.newInstance().newNodeRpcClient();
    }

    @Test
    public void testAccount() {
        String address = "tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2";
        Account account = binanceDexNodeApi.getAccount(address);
        Assert.assertEquals(address, account.getAddress());
    }

    @Test
    public void testBlockTransactions() {
        Long height = 7794210L;
        List<Transaction> transactions = binanceDexNodeApi.getBlockTransactions(height);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.size() == 1);
    }

    @Test
    public void testCommitTransfer() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        transfer.setAmount("1.1");
        TransactionOption options = new TransactionOption("test1g2g", 1, null);
        List<TransactionMetadata> resp = binanceDexNodeApi.transfer(transfer, wallet, options, true);

        Assert.assertEquals(1, resp.size());
        Assert.assertTrue(resp.get(0).isOk());
    }

    @Test
    public void testAsyncTransfer() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        transfer.setAmount("1.2");
        TransactionOption options = new TransactionOption("test23gg", 1, null);
        List<TransactionMetadata> resp = binanceDexNodeApi.transfer(transfer, wallet, options, false);

        Assert.assertEquals(1, resp.size());
        Assert.assertTrue(resp.get(0).isOk());
    }

    @Test
    public void testAsyncMultiTransfer() throws IOException, NoSuchAlgorithmException {
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
        TransactionOption options = new TransactionOption("test4", 1, null);
        List<TransactionMetadata> resp = binanceDexNodeApi.multiTransfer(multiTransfer, wallet, options, false);

        Assert.assertEquals(1, resp.size());
        Assert.assertTrue(resp.get(0).isOk());
    }

    @Test
    public void testCommitMultiTransfer() throws IOException, NoSuchAlgorithmException {
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
        TransactionOption options = new TransactionOption("test5", 1, null);
        List<TransactionMetadata> resp = binanceDexNodeApi.multiTransfer(multiTransfer, wallet, options, true);

        Assert.assertEquals(1, resp.size());
        Assert.assertTrue(resp.get(0).isOk());
    }

    @Test
    public void testNodeInfo() {
        Infos nodeInfo = binanceDexNodeApi.getNodeInfo();
        Assert.assertNotNull(nodeInfo);
    }

    @Test
    public void testGetTransaction() {
        Transaction transaction = binanceDexNodeApi.getTransaction("9A4EFAEAC70A9115AD88B540FED7515D5DDBECF2BB19C0A7CBDC4E3F5053ECB8");
        Assert.assertNotNull(transaction);
        Assert.assertEquals("9A4EFAEAC70A9115AD88B540FED7515D5DDBECF2BB19C0A7CBDC4E3F5053ECB8", transaction.getHash());
        Assert.assertEquals(0, transaction.getCode().intValue());
    }

    @Test
    public void testGetBlockMetaByHeight() {
        BlockMeta blockMeta = binanceDexNodeApi.getBlockMetaByHeight(8454149L);
        Assert.assertNotNull(blockMeta);
        Assert.assertEquals(8454149L, blockMeta.getHeader().getHeight().longValue());
    }

    @Test
    public void testGetBlockMetaByHash() {
        BlockMeta blockMeta = binanceDexNodeApi.getBlockMetaByHash("6FACD5586859B67AD5B5BABF0A0DC971AF91215DEBB9EF3D2F3847E4D2D97DDD");
        Assert.assertNotNull(blockMeta);
        Assert.assertEquals(8665773L, blockMeta.getHeader().getHeight().longValue());
    }
}
