package com.binance.dex.api.client.encoding;

import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.client.encoding.message.NewOrderMessage;
import com.binance.dex.api.client.encoding.message.SignData;
import com.binance.dex.api.proto.StdSignature;
import com.binance.dex.api.proto.StdTx;
import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EncodeTest {

    public static byte[] toByteArray(int[] ia) {
        byte[] ba = new byte[ia.length];
        for (int i = 0; i < ia.length; i++) {
            ba[i] = (byte) ia[i];
        }
        return ba;
    }

    @Test
    public void testJsonEncodeAndSign() throws Exception {
        NewOrderMessage no = NewOrderMessage.newBuilder()
                .setSender("bnc1hgm0p7khfk85zpz5v0j8wnej3a90w7098fpxyh")
                .setId("BA36F0FAD74D8F41045463E4774F328F4AF779E5-36")
                .setSymbol("NNB-338_BNB")
                .setOrderType(OrderType.fromValue(2L))
                .setSide(OrderSide.fromValue(1L))
                .setPrice("1.3635")
                .setQuantity("1.0")
                .setTimeInForce(TimeInForce.GTE).build();


        SignData sd = new SignData();
        sd.setChainId("chain-bnb");
        sd.setAccountNumber("12");
        sd.setSequence("35");
        sd.setMemo("");
        sd.setMsgs(new BinanceDexTransactionMessage[]{no});
        sd.setSource("1");

        String jsonEncodeHex = EncodeUtils.bytesToHex(EncodeUtils.toJsonEncodeBytes(sd));

        String expectedJsonEncodeHex = "7b226163636f756e745f6e756d626572223a223132222c22636861696e5f6964223a22636861696e2d626e62222c2264617461223a6e756c6c2c226d656d6f223a22222c226d736773223a5b7b226964223a22424133364630464144373444384634313034353436334534373734463332384634414637373945352d3336222c226f7264657274797065223a322c227072696365223a3133363335303030302c227175616e74697479223a3130303030303030302c2273656e646572223a22626e633168676d3070376b68666b38357a707a3576306a38776e656a3361393077373039386670787968222c2273696465223a312c2273796d626f6c223a224e4e422d3333385f424e42222c2274696d65696e666f726365223a317d5d2c2273657175656e6365223a223335222c22736f75726365223a2231227d";
        Assert.assertEquals(expectedJsonEncodeHex, jsonEncodeHex);

        String privateKey = "90335b9d2153ad1a9799a3ccc070bd64b4164e9642ee1dd48053c33f9a3a05e9";
        String expectedSignatureHex = "08bf9c556c1f632e42c4eca3efd72971517a07b07853af3d8f8581ee58209a9771763286cf2859b62c6e3f139ac15c3d46eafd7b1d71763ac45a4b053b23a347";

        byte[] signatureBytes = Crypto.sign(EncodeUtils.toJsonEncodeBytes(sd), privateKey);
        Assert.assertEquals(expectedSignatureHex, EncodeUtils.bytesToHex(signatureBytes));
    }

    @Test
    public void testNewOrder() throws IOException {
        byte[] sender = toByteArray(new int[]{182, 86, 29, 204, 16, 65, 48, 5, 154, 124, 8, 244, 140, 100, 97, 12, 31, 111, 144, 100});
        com.binance.dex.api.proto.NewOrder no = com.binance.dex.api.proto.NewOrder.newBuilder().setSender(ByteString.copyFrom(sender))
                .setId("B6561DCC104130059A7C08F48C64610C1F6F9064-11")
                .setSymbol("BTC-5C4_BNB")
                .setOrdertype(2L)
                .setSide(1L)
                .setPrice(100000000L)
                .setQuantity(1200000000L)
                .setTimeinforce(1L)
                .build();
        byte[] msg = EncodeUtils.aminoWrap(no.toByteArray(), MessageType.NewOrder.getTypePrefixBytes(), false);


        byte[] pubKey = toByteArray(new int[]{235, 90, 233, 135, 33, 3, 186, 245, 61, 20, 36, 248, 234, 131, 208, 58, 130, 246, 209, 87, 181, 64, 28, 78, 165, 127, 251, 131, 23, 135, 46, 21, 161, 159, 201, 183, 173, 123});
        byte[] signature = toByteArray(new int[]{231, 154, 102, 6, 210, 140, 240, 123, 156, 198, 245, 102, 181, 36, 165, 40, 43, 19, 190, 204, 195, 22, 35, 118, 199, 159, 57, 38, 32, 201, 90, 68, 123, 25, 246, 78, 118, 30, 34, 167, 163, 188, 49, 26, 120, 14, 125, 159, 221, 82, 30, 47, 126, 222, 194, 83, 8, 197, 186, 198, 170, 28, 10, 49});
        StdSignature stdSignature = StdSignature.newBuilder().setPubKey(ByteString.copyFrom(pubKey))
                .setSignature(ByteString.copyFrom(signature))
                .setAccountNumber(1L)
                .setSequence(10L)
                .build();

        byte[] sig = EncodeUtils.aminoWrap(stdSignature.toByteArray(), MessageType.StdSignature.getTypePrefixBytes(), false);

        StdTx stdTx = StdTx.newBuilder()
                .addMsgs(ByteString.copyFrom(msg))
                .addSignatures(ByteString.copyFrom(sig))
                .setMemo("")
                .build();
        byte[] tx = EncodeUtils.aminoWrap(stdTx.toByteArray(), MessageType.StdTx.getTypePrefixBytes(), true);

        String txStr = EncodeUtils.bytesToHex(tx);
        String expectedTxStr = "db01f0625dee0a65ce6dc0430a14b6561dcc104130059a7c08f48c64610c1f6f9064122b423635363144434331303431333030353941374330384634384336343631304331463646393036342d31311a0b4254432d3543345f424e42200228013080c2d72f3880989abc044001126e0a26eb5ae9872103baf53d1424f8ea83d03a82f6d157b5401c4ea57ffb8317872e15a19fc9b7ad7b1240e79a6606d28cf07b9cc6f566b524a5282b13beccc3162376c79f392620c95a447b19f64e761e22a7a3bc311a780e7d9fdd521e2f7edec25308c5bac6aa1c0a311801200a";
        Assert.assertEquals(expectedTxStr, txStr);
    }
}
