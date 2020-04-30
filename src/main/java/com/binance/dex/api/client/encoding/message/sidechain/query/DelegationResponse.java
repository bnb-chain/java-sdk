package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class DelegationResponse {

    @JsonProperty(value = "Delegation")
    private Delegation delegation;

    @JsonProperty(value = "balance")
    private CoinValue balance;

    public DelegationResponse() {
    }

    public DelegationResponse(Delegation delegation, CoinValue balance) {
        this.delegation = delegation;
        this.balance = balance;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public CoinValue getBalance() {
        return balance;
    }

    public void setBalance(CoinValue balance) {
        this.balance = balance;
    }
}
