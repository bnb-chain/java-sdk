package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.encoding.EncodeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class TransactionRequestAssemblerTest {
    @Test
    public void testWallet() {
        Wallet wallet = new Wallet(
                "95949f757db1f57ca94a5dff23314accbe7abee89597bf6a3c7382c84d7eb832",
                BinanceDexEnvironment.TEST_NET);
        Assert.assertEquals("tbnb1grpf0955h0ykzq3ar5nmum7y6gdfl6lx8xu7hm", wallet.getAddress());
        Assert.assertEquals("40c2979694bbc961023d1d27be6fc4d21a9febe6",
                EncodeUtils.bytesToHex(wallet.getAddressBytes()));
        Assert.assertEquals("eb5ae98721026a35920088d98c3888ca68c53dfc93f4564602606cbb87f0fe5ee533db38e502",
                EncodeUtils.bytesToHex(wallet.getPubKeyForSign()));
    }

    @Test
    public void testTransfer() throws IOException, NoSuchAlgorithmException {
        List<String> words =
                Arrays.asList("slot live best metal mandate page hover tank bronze code salad hill hen salad train inmate autumn nut home city shield level board measure".split(" "));

        Wallet wallet = Wallet.createWalletFromMnemonicCode(words, BinanceDexEnvironment.TEST_NET);

        Assert.assertEquals("tbnb12n2p6zcvgcpumyexqhnp3q9tc2327l39ycfnyk", wallet.getAddress());
        wallet.setAccountNumber(0);
        wallet.setSequence(11L);
        wallet.setChainId("test-chain-n4b735");

        TransactionOption options = new TransactionOption("", 0, null);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);

        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(wallet.getAddress());
        transfer.setToAddress("tbnb1rqa5gxmgzjhepvkhdtvkuxd4yqyv2w7sm8p78g");
        transfer.setAmount("1");

        TransferMessage msgBean = assembler.createTransferMessage(transfer);
        byte[] encodedMsg = assembler.encodeTransferMessage(msgBean);
        Assert.assertEquals(
                "2a2c87fa0a220a1454d41d0b0c4603cd932605e61880abc2a2af7e25120a0a03424e421080c2d72f12220a14183b441b6814af90b2d76ad96e19b52008c53bd0120a0a03424e421080c2d72f",
                EncodeUtils.bytesToHex(encodedMsg));
        byte[] signature = assembler.sign(msgBean);
        Assert.assertEquals(
                "8ad9bc7fd3ebf41a1a8874d643873affdc3ef77cb40cfba72795815574b4cc720dcef0da455afbcd97b9cf70d88ecb1f060610f0b48eda2752b213f6fa553bd6",
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);
        Assert.assertEquals(
                "0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a12408ad9bc7fd3ebf41a1a8874d643873affdc3ef77cb40cfba72795815574b4cc720dcef0da455afbcd97b9cf70d88ecb1f060610f0b48eda2752b213f6fa553bd6200b",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "c001f0625dee0a4c2a2c87fa0a220a1454d41d0b0c4603cd932605e61880abc2a2af7e25120a0a03424e421080c2d72f12220a14183b441b6814af90b2d76ad96e19b52008c53bd0120a0a03424e421080c2d72f126c0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a12408ad9bc7fd3ebf41a1a8874d643873affdc3ef77cb40cfba72795815574b4cc720dcef0da455afbcd97b9cf70d88ecb1f060610f0b48eda2752b213f6fa553bd6200b",
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }

    @Test
    public void testNewOrder() throws IOException, NoSuchAlgorithmException {
        List<String> words =
                Arrays.asList("slot live best metal mandate page hover tank bronze code salad hill hen salad train inmate autumn nut home city shield level board measure".split(" "));

        Wallet wallet = Wallet.createWalletFromMnemonicCode(words, BinanceDexEnvironment.TEST_NET);

        Assert.assertEquals("tbnb12n2p6zcvgcpumyexqhnp3q9tc2327l39ycfnyk", wallet.getAddress());
        wallet.setAccountNumber(0);
        wallet.setSequence(14L);
        wallet.setChainId("test-chain-n4b735");

        TransactionOption options = new TransactionOption("", 0, null);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);

        NewOrder no = new NewOrder();
        no.setSymbol("NNB-274_BNB");
        no.setSide(OrderSide.SELL);
        no.setOrderType(OrderType.LIMIT);
        no.setPrice("2");
        no.setQuantity("15");
        no.setTimeInForce(TimeInForce.GTE);

        NewOrderMessage msgBean = assembler.createNewOrderMessage(no);

        byte[] encodedMsg = assembler.encodeNewOrderMessage(msgBean);
        Assert.assertEquals(
                "ce6dc0430a1454d41d0b0c4603cd932605e61880abc2a2af7e25122b353444343144304230433436303343443933323630354536313838304142433241324146374532352d31351a0b4e4e422d3237345f424e4220022802308084af5f3880dea0cb054001",
                EncodeUtils.bytesToHex(encodedMsg));
        byte[] signature = assembler.sign(msgBean);
        Assert.assertEquals(
                "44B2B9293EC4867FC2C77C822E13F090E8C6502ECBBC3349AF794E45C6FC8A9823728BCC3B482BF82B4A954F8A7BC1981E1BE4877B62311084C50FD95CAE06AE".toLowerCase(),
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);

        Assert.assertEquals(
                "0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a124044b2b9293ec4867fc2c77c822e13f090e8c6502ecbbc3349af794e45c6fc8a9823728bcc3b482bf82b4a954f8a7bc1981e1be4877b62311084c50fd95cae06ae200e",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "d901f0625dee0a65ce6dc0430a1454d41d0b0c4603cd932605e61880abc2a2af7e25122b353444343144304230433436303343443933323630354536313838304142433241324146374532352d31351a0b4e4e422d3237345f424e4220022802308084af5f3880dea0cb054001126c0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a124044b2b9293ec4867fc2c77c822e13f090e8c6502ecbbc3349af794e45c6fc8a9823728bcc3b482bf82b4a954f8a7bc1981e1be4877b62311084c50fd95cae06ae200e",
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }

    @Test
    public void testCancelOrder() throws IOException, NoSuchAlgorithmException {
        List<String> words =
                Arrays.asList("slot live best metal mandate page hover tank bronze code salad hill hen salad train inmate autumn nut home city shield level board measure".split(" "));

        Wallet wallet = Wallet.createWalletFromMnemonicCode(words, BinanceDexEnvironment.TEST_NET);

        Assert.assertEquals("tbnb12n2p6zcvgcpumyexqhnp3q9tc2327l39ycfnyk", wallet.getAddress());
        wallet.setAccountNumber(0);
        wallet.setSequence(14L);
        wallet.setChainId("test-chain-n4b735");

        TransactionOption options = new TransactionOption("", 0, null);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);

        CancelOrder co = new CancelOrder();
        co.setSymbol("NNB-274_BNB");
        co.setRefId("54D41D0B0C4603CD932605E61880ABC2A2AF7E25-14");

        CancelOrderMessage msgBean = assembler.createCancelOrderMessage(co);

        byte[] encodedMsg = assembler.encodeCancelOrderMessage(msgBean);
        Assert.assertEquals(
                "166e681b0a1454d41d0b0c4603cd932605e61880abc2a2af7e25120b4e4e422d3237345f424e421a2b353444343144304230433436303343443933323630354536313838304142433241324146374532352d3134",
                EncodeUtils.bytesToHex(encodedMsg));
        byte[] signature = assembler.sign(msgBean);
        Assert.assertEquals(
                "c0d9a95bf30e74d0701e4033c419020dbbac4b282356183caba98af0d57fdc583ddb3ada2a9961d75d175cbc7ced39225c0e14a0c0667b805f522a3c498d38a7".toLowerCase(),
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);

        Assert.assertEquals(
                "0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a1240c0d9a95bf30e74d0701e4033c419020dbbac4b282356183caba98af0d57fdc583ddb3ada2a9961d75d175cbc7ced39225c0e14a0c0667b805f522a3c498d38a7200e",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "c801f0625dee0a54166e681b0a1454d41d0b0c4603cd932605e61880abc2a2af7e25120b4e4e422d3237345f424e421a2b353444343144304230433436303343443933323630354536313838304142433241324146374532352d3134126c0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a1240c0d9a95bf30e74d0701e4033c419020dbbac4b282356183caba98af0d57fdc583ddb3ada2a9961d75d175cbc7ced39225c0e14a0c0667b805f522a3c498d38a7200e",
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }

    @Test
    public void testTokenFreeze() throws IOException, NoSuchAlgorithmException {
        List<String> words =
                Arrays.asList("trial raw kiss bench silent crystal clever cloud chapter obvious error income mechanic attend army outer found cube tribe sort south possible scene fox".split(" "));

        Wallet wallet = Wallet.createWalletFromMnemonicCode(words, BinanceDexEnvironment.TEST_NET);

        Assert.assertEquals("tbnb1mrslq6lhglm3jp7pxzlk8u4549pmtp9sgvn2rc", wallet.getAddress());
        wallet.setAccountNumber(0);
        wallet.setSequence(9L);
        wallet.setChainId("test-chain-n4b735");

        TransactionOption options = new TransactionOption("", 0, null);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);

        TokenFreeze tf = new TokenFreeze();
        tf.setAmount("1");
        tf.setSymbol("NNB-C3F");

        TokenFreezeMessage msgBean = assembler.createTokenFreezeMessage(tf);
        byte[] encodedMsg = assembler.encodeTokenFreezeMessage(msgBean);
        Assert.assertEquals(
                "e774b32d0a14d8e1f06bf747f71907c130bf63f2b4a943b584b012074e4e422d4333461880c2d72f",
                EncodeUtils.bytesToHex(encodedMsg));
        byte[] signature = assembler.sign(msgBean);
        Assert.assertEquals(
                "9ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c2".toLowerCase(),
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);

        Assert.assertEquals(
                "0a26eb5ae987210280ec8943329305e43b2e6112728423ef9f9a7e7125621c3545c2f30ce08bf83c12409ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c22009",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "9c01f0625dee0a28e774b32d0a14d8e1f06bf747f71907c130bf63f2b4a943b584b012074e4e422d4333461880c2d72f126c0a26eb5ae987210280ec8943329305e43b2e6112728423ef9f9a7e7125621c3545c2f30ce08bf83c12409ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c22009",
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }

    @Test
    public void testTokenUnfreeze() throws IOException, NoSuchAlgorithmException {
        List<String> words =
                Arrays.asList("trial raw kiss bench silent crystal clever cloud chapter obvious error income mechanic attend army outer found cube tribe sort south possible scene fox".split(" "));

        Wallet wallet = Wallet.createWalletFromMnemonicCode(words, BinanceDexEnvironment.TEST_NET);

        Assert.assertEquals("tbnb1mrslq6lhglm3jp7pxzlk8u4549pmtp9sgvn2rc", wallet.getAddress());
        wallet.setAccountNumber(0);
        wallet.setSequence(9L);
        wallet.setChainId("test-chain-n4b735");

        TransactionOption options = new TransactionOption("", 0, null);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);

        TokenUnfreeze tu = new TokenUnfreeze();
        tu.setAmount("1");
        tu.setSymbol("NNB-C3F");

        TokenUnfreezeMessage msgBean = assembler.createTokenUnfreezeMessage(tu);
        byte[] encodedMsg = assembler.encodeTokenUnfreezeMessage(msgBean);
        Assert.assertEquals(
                "6515ff0d0a14d8e1f06bf747f71907c130bf63f2b4a943b584b012074e4e422d4333461880c2d72f",
                EncodeUtils.bytesToHex(encodedMsg));
        byte[] signature = assembler.sign(msgBean);
        Assert.assertEquals(
                "9ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c2".toLowerCase(),
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);

        Assert.assertEquals(
                "0a26eb5ae987210280ec8943329305e43b2e6112728423ef9f9a7e7125621c3545c2f30ce08bf83c12409ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c22009",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "9c01f0625dee0a286515ff0d0a14d8e1f06bf747f71907c130bf63f2b4a943b584b012074e4e422d4333461880c2d72f126c0a26eb5ae987210280ec8943329305e43b2e6112728423ef9f9a7e7125621c3545c2f30ce08bf83c12409ceabe0262a75b0da7556303580f56a094486cc9938a728f903a57054061bd833288979fbc8dc5ee07743df5110cb773c25d9974f34158a4f6ed6ac6899740c22009",
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }
}
