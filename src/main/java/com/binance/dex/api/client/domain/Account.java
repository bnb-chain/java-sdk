package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @JsonProperty("account_number")
    private Integer accountNumber;
    private String address;
    private List<Balance> balances;
    @JsonProperty("public_key")
    private List<Integer> publicKey;
    private Long sequence;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public List<Integer> getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(List<Integer> publicKey) {
        this.publicKey = publicKey;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("accountNumber", accountNumber)
                .append("address", address)
                .append("balances", balances)
                .append("publicKey", publicKey)
                .append("sequence", sequence)
                .toString();
    }
}
