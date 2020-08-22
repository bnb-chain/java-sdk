package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.crosschain.Package;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.bridge.TransferIn;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.oracle.ClaimMsg;
import com.binance.dex.api.client.domain.oracle.Prophecy;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.bridge.BindStatus;
import com.binance.dex.api.client.encoding.message.bridge.ClaimMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.ClaimTypes;
import com.binance.dex.api.client.encoding.message.bridge.RefundReason;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class BridgeNodeExample {

    private BinanceDexApiNodeClient nodeClient = null;
    private Wallet wallet = null;

    @Before
    public void setup() throws IOException {
        nodeClient = BinanceDexApiClientFactory.newInstance().newNodeRpcClient("http://dex-qa-s1-bsc-dev-validator-alb-501442930.ap-northeast-1.elb.amazonaws.com:27147",BinanceDexEnvironment.TEST_NET.getHrp(), BinanceDexEnvironment.TEST_NET.getValHrp());
        String mnemonic = "ten spring excite fluid pizza amused goat equal language cinnamon change drive alien second table onion obscure culture void science renew scrub capable wet";
        wallet = Wallet.createWalletFromMnemonicCode(Arrays.asList(mnemonic.split(" ")), BinanceDexEnvironment.TEST_NET);
    }

    @Test
    public void t() throws JsonProcessingException {
        System.out.println();
        Transaction transaction = nodeClient.getTransaction("0757F090A88F99436A6E1B138EA5ED1A5F2A8CC46BF03912F663119A5924DF62");
        ClaimMsg msg = (ClaimMsg)transaction.getRealTx();
        List<Package> packages = msg.getPayload();
        System.out.println(EncodeUtils.toJsonStringSortKeys(packages));

        System.out.println();

        transaction = nodeClient.getTransaction("59619D76EAB7B5D3FCE17589B6735869A49B77B5A059168731EA31ACE5A3119D");
        msg = (ClaimMsg)transaction.getRealTx();
        packages = msg.getPayload();
        System.out.println(EncodeUtils.toJsonStringSortKeys(packages));

        System.out.println();

        transaction = nodeClient.getTransaction("35568AB9D81409A54B38C11924C8B3BB374B8A57A4FF2070EF71BB393E94EE02");
        msg = (ClaimMsg)transaction.getRealTx();
        packages = msg.getPayload();
        System.out.println(EncodeUtils.toJsonStringSortKeys(packages));

        System.out.println();

        transaction = nodeClient.getTransaction("29FE859C0B40F73996346FBD9261B4F8B28C8381F13F3D3DCAE87ED3C6EE0A72");
        msg = (ClaimMsg)transaction.getRealTx();
        packages = msg.getPayload();
        System.out.println(EncodeUtils.toJsonStringSortKeys(packages));

        System.out.println();

        transaction = nodeClient.getTransaction("362BA6ACD9A831AC30D1C83AAD02F954C38940DCB91610DA4EA43CF7DCEAE0EA");
        msg = (ClaimMsg)transaction.getRealTx();
        packages = msg.getPayload();
        System.out.println(EncodeUtils.toJsonStringSortKeys(packages));
    }

    @Test
    public void transferOut() throws IOException, NoSuchAlgorithmException {
        String toAddress = "0x9fB29AAc15b9A4B7F17c3385939b007540f4d791";
        Token token = new Token("BNB", 100000L);
        long expireTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3;

        TransactionOption options = new TransactionOption("", 0, null);

        List<TransactionMetadata> results = nodeClient.transferOut(toAddress, token, expireTime / 1000, wallet, options, true);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void bind() throws IOException, NoSuchAlgorithmException {
        String symbol = "BNB";
        long amount = 1000000L;
        String contractAddress = "0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08";
        int contractDecimal = 2;
        long expireTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3;

        TransactionOption options = new TransactionOption("", 0, null);

        List<TransactionMetadata> results = nodeClient.bind(symbol, amount, contractAddress, contractDecimal, expireTime / 1000, wallet, options, true);
        Assert.assertEquals(1, results.size());
    }

    @Test
    public void unBind() throws IOException, NoSuchAlgorithmException {
        String symbol = "ABC-196";
        TransactionOption options = new TransactionOption("", 0, null);
        List<TransactionMetadata> results = nodeClient.unBind(symbol, wallet, options, true);
        System.out.println(EncodeUtils.toJsonStringSortKeys(results));
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isOk());
    }

    @Test
    public void claim() throws IOException, NoSuchAlgorithmException {
        TransactionOption options = new TransactionOption("", 0, null);
        List<TransactionMetadata> results = nodeClient.claim(2, "test claim message message message message message".getBytes(), 0, wallet, options, true);
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isOk());
    }

    @Test
    public void getProphecy() throws IOException {
        Prophecy prophecy = nodeClient.getProphecy(ClaimTypes.ClaimTypeTransferIn, 1);
        if (prophecy != null){
            Assert.assertNotNull(prophecy.getClaimValidators());
        }
    }

    @Test
    public void getCurrentSequence(){
        long sequence = nodeClient.getCurrentSequence(ClaimTypes.ClaimTypeTransferIn);
        Assert.assertTrue(sequence >= 0);
    }

}
