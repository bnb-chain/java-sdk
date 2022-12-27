package com.binance.dex.api.client.encoding.message.beaconchain.query;

import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author Francis.Liu
 **/
public class PoolMessage {
    @JsonProperty(value = "loose_tokens")
    private long looseTokens;
    @JsonProperty(value = "bonded_tokens")
    private long bondedTokens;

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

    public Pool toPool(){
        Pool pool = new Pool();
        pool.setBondedTokens(bondedTokens);
        pool.setLooseTokens(looseTokens);
        return pool;
    }

}
