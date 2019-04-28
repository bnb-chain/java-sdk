package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.ledger.LedgerDevice;
import com.binance.dex.api.client.ledger.LedgerKey;
import com.binance.dex.api.client.ledger.LedgerUtils;
import com.binance.dex.api.client.ledger.LedgerVersion;
import org.apache.commons.codec.binary.Hex;

import static com.binance.dex.api.client.ledger.LedgerDevice.*;

public class LedgerKeyExample {

    public static void main(String[] args) throws Exception {
        if (!hasLedgerConnected()) {
            System.out.println("No ledger device is connected to the machine");
        }
        LedgerDevice ledgerDevice = findLedgerDevice();
        LedgerVersion version = ledgerDevice.getVersion();
        System.out.println(version.toString());

        int[] bip44Path = LedgerUtils.createBIP44Path(0, 0);

        LedgerKey ledgerKey = new LedgerKey(ledgerDevice, bip44Path, "bnb");

        System.out.print("Please verify if the displayed address is identical to ");
        System.out.println(ledgerKey.getAddress());

        ledgerDevice.showAddressSECP256K1(bip44Path, "bnb");
        byte[] pubkey = ledgerDevice.getPublicKeySECP256K1(bip44Path);
        System.out.print("Public key: ");
        System.out.println(Hex.encodeHexString(pubkey));

        String msgString= "7b226163636f756e745f6e756d626572223a2230222c22636861696e5f6964223a2262696e616e63652d636861696" +
                "e222c2264617461223a6e756c6c2c226d656d6f223a2274657374206c6564676572207369676e222c226d736773223a5b7b2269" +
                "6e70757473223a5b7b2261646472657373223a22626e62317979633971353375746d6a3066717833706671376c386338336c727" +
                "0303674707065306e6370222c22636f696e73223a5b7b22616d6f756e74223a3130303030303030303030303030302c2264656e" +
                "6f6d223a22424e42227d5d7d5d2c226f757470757473223a5b7b2261646472657373223a22626e6231706b70706632617233776" +
                "a333863753779386b68796730636c6d6876663266326e7a74347736222c22636f696e73223a5b7b22616d6f756e74223a313030" +
                "3030303030303030303030302c2264656e6f6d223a22424e42227d5d7d5d7d5d2c2273657175656e6365223a2230222c22736f75726365223a2230227d";
        byte[] signature = ledgerDevice.signSECP256K1(bip44Path, LedgerUtils.hexToBin(msgString));
        System.out.println(Hex.encodeHexString(signature));

        ledgerDevice.close();
    }
}
