package com.binance.dex.api.client.domain.stake;

/**
 * @author Fitz.Lu
 **/
public class Pool {

    private long looseTokens;

    private long bondedTokens;

    public Pool() {
    }

    public long getLooseTokens() {
        return looseTokens;
    }

    public void setLooseTokens(long looseTokens) {
        this.looseTokens = looseTokens;
    }

    public long getBondedTokens() {
        return bondedTokens;
    }

    public void setBondedTokens(long bondedTokens) {
        this.bondedTokens = bondedTokens;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "looseTokens=" + looseTokens +
                ", bondedTokens=" + bondedTokens +
                '}';
    }
}
