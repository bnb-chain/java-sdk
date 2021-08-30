package com.binance.dex.api.client.encoding;

import org.bitcoinj.crypto.MnemonicException;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class CryptoTest {

    @Test
    public void testGetPrivateKeyFromMnemonic() throws MnemonicException.MnemonicLengthException, MnemonicException.MnemonicChecksumException, MnemonicException.MnemonicWordException {
        String mnemonic = "fragile duck lunch coyote cotton pole gym orange share muscle impulse mom pause isolate define oblige hungry sound stereo spider style river fun account";
        List<String> words = Arrays.asList(mnemonic.split(" "));
        String privateKey = Crypto.getPrivateKeyFromMnemonicCode(words);
        Assert.assertEquals("caf2009a04bd53d426fc0907383b3f1dfe13013aee694d0159f6befc3fdccd5f", privateKey);
    }

    @Test
    public void testDecodeAddress() throws Crypto.SegwitAddressException {
        String address = "bnb1x9nnkazcgrqr902a83k7xecu44607jpjlw4snf";
        byte[] pubKey = Crypto.decodeAddress(address);
        Assert.assertEquals("31673b745840c032bd5d3c6de3671cad74ff4832", EncodeUtils.bytesToHex(pubKey));
    }

    @Test
    public void testGetAddressFromPrivateKey() {
        String privateKey = "90335b9d2153ad1a9799a3ccc070bd64b4164e9642ee1dd48053c33f9a3a05e9";
        String address = Crypto.getAddressFromPrivateKey(privateKey, "bnc");
        Assert.assertEquals("bnc1hgm0p7khfk85zpz5v0j8wnej3a90w7098fpxyh", address);
    }

    @Test
    public void testSign() throws NoSuchAlgorithmException {
        String hex = "7b226163636f756e745f6e756d626572223a2231222c22636861696e5f6964223a22626e62636861696e2d31303030222c226d656d6f223a22222c226d736773223a5b7b226964223a22423635363144434331303431333030353941374330384634384336343631304331463646393036342d3130222c226f7264657274797065223a322c227072696365223a3130303030303030302c227175616e74697479223a313230303030303030302c2273656e646572223a22626e63316b6574706d6e71736779637174786e7570723667636572707073306b6c797279687a36667a6c222c2273696465223a312c2273796d626f6c223a224254432d3543345f424e42222c2274696d65696e666f726365223a317d5d2c2273657175656e6365223a2239227d";
        byte[] sig = Crypto.sign(EncodeUtils.hexStringToByteArray(hex), "30c5e838578a29e3e9273edddd753d6c9b38aca2446dd84bdfe2e5988b0da0a1");
        Assert.assertEquals("9c0421217ef92d556a14e3f442b07c85f6fc706dfcd8a72d6b58f05f96e95aa226b10f7cf62ccf7c9d5d953fa2c9ae80a1eacaf0c779d0253f1a34afd17eef34",
                EncodeUtils.bytesToHex(sig));
    }

    @Test
    public void testCheckValidBNBAddress() {
        String address = "bnb1x9nnkazcgrqr902a83k7xecu44607jpjlw4snf";
        boolean result = Crypto.checkBNBAddress(address);
        Assert.assertEquals(true,
                result);
    }

    @Test
    public void testCheckInvalidBNBAddress() {
        String address = "bnb1x9nnkazcgrqr902a83k7xecu44607jpjlw4snx";
        boolean result = Crypto.checkBNBAddress(address);
        Assert.assertEquals(false,
                result);
    }

    @Test
    public void testCheckNonBNBAddress() {
        String address = "cosmosaccaddr1wqrn76z0v36pr3vx3sgue4y5rv4pzpu6ffnjj0";
        boolean result = Crypto.checkBNBAddress(address);
        Assert.assertEquals(false,
                result);
    }

}
