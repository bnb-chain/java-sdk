package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.TxType;
import org.junit.Assert;

import java.util.List;

public class LocalEnvironmentExample {
    public static void main(String[] args) {
        BinanceDexEnvironment env = new BinanceDexEnvironment(
                "http://localhost:26657",
                "",
                "",
                "",
                "tbnb");


        BinanceDexApiNodeClient binanceDexNodeApi = BinanceDexApiClientFactory.newInstance().newNodeRpcClient(env.getNodeUrl(), env.getHrp());
        Long height = 33079L;
        List<Transaction> transactions = binanceDexNodeApi.getBlockTransactions(height);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.size() == 1);
        Assert.assertEquals(transactions.get(0).getTxType(), TxType.MINI_TOKEN_ISSUE);


        height = 34356L;
        transactions = binanceDexNodeApi.getBlockTransactions(height);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.size() == 1);
        Assert.assertEquals(transactions.get(0).getTxType(), TxType.MINI_TOKEN_SET_URI);


        height = 55588L;
        transactions = binanceDexNodeApi.getBlockTransactions(height);
        Assert.assertNotNull(transactions);
        Assert.assertTrue(transactions.size() == 1);
        Assert.assertEquals(transactions.get(0).getTxType(), TxType.MINI_TOKEN_LIST);
    }
}
