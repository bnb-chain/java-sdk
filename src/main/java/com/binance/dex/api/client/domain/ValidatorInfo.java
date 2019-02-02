package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidatorInfo {
    private String address;
    @JsonProperty("pub_key")
    private List<Integer> pubKey;
    @JsonProperty("voting_power")
    private Long votingPower;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getPubKey() {
        return pubKey;
    }

    public void setPubKey(List<Integer> pubKey) {
        this.pubKey = pubKey;
    }

    public Long getVotingPower() {
        return votingPower;
    }

    public void setVotingPower(Long votingPower) {
        this.votingPower = votingPower;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("address", address)
                .append("pubKey", pubKey)
                .append("votingPower", votingPower)
                .toString();
    }
}
