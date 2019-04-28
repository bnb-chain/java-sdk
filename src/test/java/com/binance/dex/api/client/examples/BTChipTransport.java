package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.ledger.LedgerUtils;
import com.binance.dex.api.client.ledger.hid.BTChipTransportHID;

public class BTChipTransport {
    public static void main(String args[]) throws Exception {
        BTChipTransportHID device = BTChipTransportHID.openDevice(null);
        byte[] result = device.exchange(LedgerUtils.hexToBin("BC00000000"));
        System.out.print("Get ledger app version: ");
        System.out.println(LedgerUtils.dump(result));

        result = device.exchange(LedgerUtils.hexToBin("BC01000029052c000080CA0200800000008000000000000000000000000000000000000000000000000000000000"));
        System.out.print("Get a public key from ledger: ");
        System.out.println(LedgerUtils.dump(result));
        device.close();
    }
}
