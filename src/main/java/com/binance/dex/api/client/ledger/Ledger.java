package com.binance.dex.api.client.ledger;

import com.binance.dex.api.client.ledger.common.BTChipException;

public interface Ledger {
    void close() throws BTChipException;

    LedgerVersion getVersion();

    byte[] signSECP256K1(int[] bip32Path, byte[] transaction) throws BTChipException;

    byte[] getPublicKeySECP256K1(int[] bip32Path) throws BTChipException;

    int showAddressSECP256K1(int[] bip32Path, boolean isMainnet) throws Exception;
}
