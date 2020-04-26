package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.bridge.TransferIn;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.oracle.Prophecy;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.bridge.BindStatus;
import com.binance.dex.api.client.encoding.message.bridge.ClaimTypes;
import com.binance.dex.api.client.encoding.message.bridge.RefundReason;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class BridgeNodeExample {

    private BinanceDexApiNodeClient nodeClient = null;
    private final Wallet wallet = new Wallet("66d08fc209b31090ea21a8cf9a6e2680b008e2b905b2358ae31e0f42b140de99", BinanceDexEnvironment.TEST_NET);

    @Before
    public void setup() {
        nodeClient = BinanceDexApiClientFactory.newInstance().newNodeRpcClient(BinanceDexEnvironment.TEST_NET.getNodeUrl(),BinanceDexEnvironment.TEST_NET.getHrp(), BinanceDexEnvironment.TEST_NET.getValHrp());
    }

    @Test
    public void transferIn(){
        TransferIn transferIn = new TransferIn();
        transferIn.setContractAddress("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        List<String> refundAddresses = new ArrayList<>();
        refundAddresses.add("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791");
        transferIn.setRefundAddresses(refundAddresses);

        List<String> receiverAddresses = new ArrayList<>();
        receiverAddresses.add(wallet.getAddress());
        transferIn.setReceiverAddresses(receiverAddresses);

        List<Long> amounts = new ArrayList<>();
        amounts.add(100000L);
        transferIn.setAmounts(amounts);

        transferIn.setSymbol("BNB");

        transferIn.setRelayFee(new Token("BNB", 10L));

        transferIn.setExpireTime((System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3) / 1000);

        TransactionOption options = new TransactionOption("", 0, null);

        long sequence = nodeClient.getCurrentSequence(ClaimTypes.ClaimTypeTransferIn);

        try {
            List<TransactionMetadata> results = nodeClient.transferIn(sequence, transferIn, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferOut(){
        String toAddress = "0x9fB29AAc15b9A4B7F17c3385939b007540f4d791";
        Token token = new Token("BNB", 100000L);
        long expireTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3;

        TransactionOption options = new TransactionOption("", 0, null);

        try {
            List<TransactionMetadata> results = nodeClient.transferOut(toAddress, token, expireTime / 1000, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bind(){
        String symbol = "BNB";
        long amount = 1000000L;
        String contractAddress = "0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08";
        int contractDecimal = 2;
        long expireTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3;

        TransactionOption options = new TransactionOption("", 0, null);

        try {
            List<TransactionMetadata> results = nodeClient.bind(symbol, amount, contractAddress, contractDecimal, expireTime / 1000, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTransferOut(){
        long sequence = nodeClient.getCurrentSequence(ClaimTypes.ClaimTypeUpdateTransferOut);
        String refundAddress = wallet.getAddress();
        Token amount = new Token("BNB", 100000L);
        int refundReason = RefundReason.timeout;

        TransactionOption options = new TransactionOption("", 0, null);

        try {
            List<TransactionMetadata> results = nodeClient.updateTransferOut(sequence, refundAddress, amount, refundReason, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBind(){
        long sequence = nodeClient.getCurrentSequence(ClaimTypes.ClaimTypeUpdateBind);
        String symbol = "BNB";
        String contractAddress = "0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08";
        int status = BindStatus.bindStatusSuccess;

        TransactionOption options = new TransactionOption("", 0, null);

        try {
            List<TransactionMetadata> results = nodeClient.updateBind(sequence, symbol, contractAddress, status, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void claim(){
        TransactionOption options = new TransactionOption("", 0, null);
        try {
            List<TransactionMetadata> results = nodeClient.claim(ClaimTypes.ClaimTypeUpdateBind, "claim message", 1, wallet, options, true);
            if (results != null){
                for (TransactionMetadata result : results) {
                    System.out.println(result.toString());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProphecy(){
        try {
            Prophecy prophecy = nodeClient.getProphecy(ClaimTypes.ClaimTypeTransferIn, 1);
            System.out.println(prophecy.toString());
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCurrentSequence(){
        long sequence = nodeClient.getCurrentSequence(ClaimTypes.ClaimTypeTransferIn);
        System.out.println(sequence);
    }

}
