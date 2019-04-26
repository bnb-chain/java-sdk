package com.binance.dex.api.client.ledger;

public class LedgerVersion {
    public byte appMode;
    public byte major;
    public byte minor;
    public byte patch;

    public LedgerVersion(byte appMode, byte major, byte minor, byte patch) {
        this.appMode = appMode;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    // CheckVersion compares the current version with the required version
    public boolean compare(LedgerVersion version) {
        if (this.major != version.major) {
            return this.major > version.major;
        }

        if (this.minor != version.minor) {
            return this.minor > version.minor;
        }

        return this.patch >= version.patch;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", this.major, this.minor, this.patch);
    }
}
