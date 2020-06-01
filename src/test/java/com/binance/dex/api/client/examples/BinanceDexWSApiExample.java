package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.BlockMeta;
import com.binance.dex.api.client.domain.Proposal;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.websocket.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BinanceDexWSApiExample {

    private BinanceDexWSApi binanceDexWSApi;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before(){
        binanceDexWSApi = BinanceDexClientWSFactory.getWSApiImpl(BinanceDexEnvironment.TEST_NET);
    }

    @Test
    public void testBlockAndTx() throws Exception{
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.netInfo()));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.blockByHeight(13513018L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(22703696L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14386667L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14353547L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13080127L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13028378L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14338593L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13712964L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13666599L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14366979L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14322134L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13565567L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14324052L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13712675L)));
        System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txByHash("1A3C0D696371455B2B57154D4CED21FE30C93B349EFA5270CDB044ED217D7B27")));
    }

    @Test
    public void testNetInfo(){
        JsonRpcResponse jsonRpcResponse = binanceDexWSApi.netInfo();
        Assert.assertNotNull(jsonRpcResponse.getResult());
    }

    @Test
    public void testBlockByHeight() {
        BlockMeta.BlockMetaResult result = binanceDexWSApi.blockByHeight(18L);
        Assert.assertNotNull(result);
        Assert.assertEquals(18L,result.getBlockMeta().getHeader().getHeight().longValue());
    }

    @Test
    public void testTxSearch() throws JsonProcessingException {
        //Transfer
        List<Transaction> transactions1 = binanceDexWSApi.txSearch(13513018L);
        Assert.assertNotNull(transactions1);
        Assert.assertEquals(transactions1.get(0).getTxType(), TxType.TRANSFER);

        //New order
        List<Transaction> transactions2 = binanceDexWSApi.txSearch(14386667L);
        Assert.assertNotNull(transactions2);
        Assert.assertEquals(transactions2.get(0).getTxType(), TxType.NEW_ORDER);

        List<Transaction> transactions3 = binanceDexWSApi.txSearch(14353547L);
        Assert.assertNotNull(transactions3);
        Assert.assertEquals(transactions3.get(0).getTxType(), TxType.ISSUE);

        List<Transaction> transactions4 = binanceDexWSApi.txSearch(13080127L);
        Assert.assertNotNull(transactions4);
        Assert.assertEquals(transactions4.get(0).getTxType(), TxType.BURN);

        List<Transaction> transactions5 = binanceDexWSApi.txSearch(13028378L);
        Assert.assertNotNull(transactions5);
        Assert.assertEquals(transactions5.get(0).getTxType(), TxType.LISTING);

        List<Transaction> transactions6 = binanceDexWSApi.txSearch(14338593L);
        Assert.assertNotNull(transactions6);
        Assert.assertEquals(transactions6.get(0).getTxType(), TxType.CANCEL_ORDER);

        List<Transaction> transactions7 = binanceDexWSApi.txSearch(13712964L);
        Assert.assertNotNull(transactions7);
        Assert.assertEquals(transactions7.get(0).getTxType(), TxType.FREEZE_TOKEN);

        List<Transaction> transactions8 = binanceDexWSApi.txSearch(13666599L);
        Assert.assertNotNull(transactions8);
        Assert.assertEquals(transactions8.get(0).getTxType(), TxType.UNFREEZE_TOKEN);

        List<Transaction> transactions9 = binanceDexWSApi.txSearch(14366979L);
        Assert.assertNotNull(transactions9);
        Assert.assertEquals(transactions9.get(0).getTxType(), TxType.TRANSFER);

        List<Transaction> transactions10 = binanceDexWSApi.txSearch(14322134L);
        Assert.assertNotNull(transactions10);
        Assert.assertEquals(transactions10.get(0).getTxType(), TxType.SUBMIT_PROPOSAL);

        List<Transaction> transactions11 = binanceDexWSApi.txSearch(13565567L);
        Assert.assertNotNull(transactions11);
        Assert.assertEquals(transactions11.get(0).getTxType(), TxType.VOTE);

        List<Transaction> transactions12 = binanceDexWSApi.txSearch(14324052L);
        Assert.assertNotNull(transactions12);
        Assert.assertEquals(transactions12.get(0).getTxType(), TxType.DEPOSIT);

        List<Transaction> transactions13 = binanceDexWSApi.txSearch(13712675L);
        Assert.assertNotNull(transactions13);
        Assert.assertEquals(transactions13.get(0).getTxType(), TxType.MINT);

    }

    @Test
    public void testTxByHash() {
        Transaction transaction = binanceDexWSApi.txByHash("1A3C0D696371455B2B57154D4CED21FE30C93B349EFA5270CDB044ED217D7B27");
        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getTxType(), TxType.TRANSFER);
        Assert.assertEquals(14396883L, transaction.getHeight().intValue());
    }

    @Test
    public void testGetProposalById() {
        Proposal proposal = binanceDexWSApi.getProposalByID("1");
        Assert.assertNotNull(proposal);
        Assert.assertEquals("1",proposal.getValue().getProposalId());
    }



}
