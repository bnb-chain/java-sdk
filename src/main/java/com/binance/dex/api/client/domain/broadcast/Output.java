package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Output {
    private String address;
    private List<OutputToken> tokens;

    public Output() {

    }

    public Output(String address, List<OutputToken> tokens) {
        this.address = address;
        this.tokens = tokens;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OutputToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<OutputToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("address", address)
                .append("tokens", tokens)
                .toString();
    }
}
