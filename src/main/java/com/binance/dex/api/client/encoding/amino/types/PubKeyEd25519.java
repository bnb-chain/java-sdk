package com.binance.dex.api.client.encoding.amino.types;

/**
 * @author Fitz.Lu
 **/
public class PubKeyEd25519 implements PubKey {

    private final int keySize = 32;

    private byte[] raw;

    public PubKeyEd25519(byte[] raw) {
        this.raw = raw;
    }

    public byte[] getRaw() {
        return raw;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    @Override
    public byte[] getBytes() {
        return raw;
    }
}
