package com.binance.dex.api.client.ledger;

import com.binance.dex.api.client.encoding.Crypto;
import org.bitcoinj.core.Utils;

import java.io.IOException;

import static com.binance.dex.api.client.ledger.LedgerDevice.hasLedgerConnected;
import static com.binance.dex.api.client.ledger.LedgerUtils.verifyBIP44Path;

public class LedgerKey {
    private LedgerDevice ledgerDevice;
    private int[] bip44Path;
    private byte[] pubKey;
    private String hrp;

    public LedgerKey(LedgerDevice ledgerDevice, int[] bip44Path, String hrp) throws IOException, IllegalArgumentException {
        if (!verifyBIP44Path(bip44Path)) {
            throw new IllegalArgumentException("Invalid BIP44 path.");
        }
        if (!hasLedgerConnected()) {
            throw new IOException("No ledger device found");
        }
        this.ledgerDevice = ledgerDevice;
        this.bip44Path = bip44Path;
        this.pubKey = ledgerDevice.getPublicKeySECP256K1(bip44Path);
        this.hrp = hrp;
    }

    public String getAddress() {
        byte[] hash = Utils.sha256hash160(this.pubKey);
        return Crypto.encodeAddress(hrp, hash);
    }

    public LedgerDevice getLedgerDevice() {
        return ledgerDevice;
    }

    public int[] getBip44Path() {
        return bip44Path;
    }

    public byte[] getPubKey() {
        return pubKey;
    }

    public String getHrp() {
        return hrp;
    }
}
