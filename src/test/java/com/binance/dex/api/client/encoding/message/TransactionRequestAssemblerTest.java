package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
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
                "641d510c529f8097e232ed6255fa8ac4b52140b4e0719ca81bd647b1e4020a4063edfe13d99db9e192430f4b2e4994065cfc7f83aca4e92fdfef38a84aeb287d",
                EncodeUtils.bytesToHex(signature));
        byte[] encodedSignature = assembler.encodeSignature(signature);
        Assert.assertEquals(
                "0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a1240641d510c529f8097e232ed6255fa8ac4b52140b4e0719ca81bd647b1e4020a4063edfe13d99db9e192430f4b2e4994065cfc7f83aca4e92fdfef38a84aeb287d200b",
                EncodeUtils.bytesToHex(encodedSignature));
        Assert.assertEquals(
                "c001f0625dee0a4c2a2c87fa0a220a1454d41d0b0c4603cd932605e61880abc2a2af7e25120a0a03424e421080c2d72f12220a14183b441b6814af90b2d76ad96e19b52008c53bd0120a0a03424e421080c2d72f126c0a26eb5ae987210216087947712ad02e55bf34a227974644f5a6cca391771b3868b495d62c5f7b1a1240641d510c529f8097e232ed6255fa8ac4b52140b4e0719ca81bd647b1e4020a4063edfe13d99db9e192430f4b2e4994065cfc7f83aca4e92fdfef38a84aeb287d200b",
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
                "ce6dc0430a1454d41d0b0c4603cd932605e61880abc2a2af7e25122b353444343144304230433436303343443933323630354536313838304142433241324146374532352d31351a0b4e4e422d3237345f424e4220042804308084af5f3880dea0cb054002",
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
                "D901F0625DEE0A65CE6DC0430A1454D41D0B0C4603CD932605E61880ABC2A2AF7E25122B353444343144304230433436303343443933323630354536313838304142433241324146374532352D31351A0B4E4E422D3237345F424E4220042804308084AF5F3880DEA0CB054002126C0A26EB5AE987210216087947712AD02E55BF34A227974644F5A6CCA391771B3868B495D62C5F7B1A124044B2B9293EC4867FC2C77C822E13F090E8C6502ECBBC3349AF794E45C6FC8A9823728BCC3B482BF82B4A954F8A7BC1981E1BE4877B62311084C50FD95CAE06AE200E".toLowerCase(),
                EncodeUtils.bytesToHex(assembler.encodeStdTx(encodedMsg, encodedSignature)));
    }

}
