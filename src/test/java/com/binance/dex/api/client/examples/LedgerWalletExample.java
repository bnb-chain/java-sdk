package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.ledger.LedgerDevice;
import com.binance.dex.api.client.ledger.LedgerUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.binance.dex.api.client.ledger.LedgerDevice.findLedgerDevice;
import static com.binance.dex.api.client.ledger.LedgerDevice.hasLedgerConnected;

public class LedgerWalletExample {
    //To run this example, please send token to your ledger key address first
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (!hasLedgerConnected()) {
            System.out.println("No ledger device is connected to the machine");
        }
        LedgerDevice ledgerDevice = findLedgerDevice();

        Wallet walletSender = new Wallet(LedgerUtils.createBIP44Path(0, 0), ledgerDevice, BinanceDexEnvironment.TEST_NET);
        Wallet walletReceiver = new Wallet("0f652de68eb8048951c753303f6a3a22114ff7d4418ecbe1711d5d6a02b50522", BinanceDexEnvironment.TEST_NET);

        Transfer transfer = new Transfer();
        transfer.setAmount("100");
        transfer.setCoin("BNB");
        transfer.setFromAddress(walletSender.getAddress());
        transfer.setToAddress(walletReceiver.getAddress());
        System.out.print("Transfer msg: ");
        System.out.println(transfer.toString());

        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        BinanceDexApiRestClient client = BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
        Account sendAccount = client.getAccount(walletSender.getAddress());
        System.out.print("Send account info: ");
        System.out.println(sendAccount.toString());

        System.out.println(String.format("Please make sure %s has enough token", walletSender.getAddress()));
        walletSender.initAccount(client);
        List<TransactionMetadata> resp = client.transfer(transfer, walletSender, options, true);
        System.out.println(resp.get(0));

        ledgerDevice.close();
    }
}
