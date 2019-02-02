package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.encoding.Crypto;

import java.io.IOException;
import java.util.List;

public class CreateWalletExample {
    public static void main(String[] args) throws IOException {
        List<String> mnemonicCodeWords = Crypto.generateMnemonicCode();
        Wallet wallet = Wallet.createWalletFromMnemonicCode(mnemonicCodeWords, BinanceDexEnvironment.TEST_NET);
        System.out.println("mnemonic code: " + String.join(" ", mnemonicCodeWords));
        System.out.println("private key: " + wallet.getPrivateKey());
        System.out.println("address: " + wallet.getAddress());
    }
}
