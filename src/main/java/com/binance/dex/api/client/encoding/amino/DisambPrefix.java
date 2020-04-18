package com.binance.dex.api.client.encoding.amino;

import java.util.Arrays;

/**
 * Disambiguation and prefix pair
 * */
public class DisambPrefix {

    /**
     * The first three non-zero bytes
     * */
    private byte[] disamb;

    /**
     * The first four non-zero bytes
     * */
    private byte[] prefix;

    /**
     * You know it
     * */
    private byte[] disambPrefix;

    public DisambPrefix() { }

    public DisambPrefix(byte[] disamb, byte[] prefix, byte[] disambPrefix) {
        this.disamb = disamb;
        this.prefix = prefix;
        this.disambPrefix = disambPrefix;
    }

    public byte[] getDisamb() {
        return disamb;
    }

    public void setDisamb(byte[] disamb) {
        this.disamb = disamb;
    }

    public byte[] getPrefix() {
        return prefix;
    }

    public void setPrefix(byte[] prefix) {
        this.prefix = prefix;
    }

    public byte[] getDisambPrefix() {
        return disambPrefix;
    }

    public void setDisambPrefix(byte[] disambPrefix) {
        this.disambPrefix = disambPrefix;
    }

    @Override
    public String toString() {
        return "DisambPrefixPair{" +
                "disamb=" + Arrays.toString(disamb) +
                ", prefix=" + Arrays.toString(prefix) +
                ", disambPrefix=" + Arrays.toString(disambPrefix) +
                '}';
    }
}
