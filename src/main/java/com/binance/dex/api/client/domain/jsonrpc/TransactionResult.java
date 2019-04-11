package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResult {
    private String hash;
    private Long height;
    private byte[] tx;
    @JsonProperty("tx_result")
    private TxResult txResult;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public byte[] getTx() {
        return tx;
    }

    public void setTx(byte[] tx) {
        this.tx = tx;
    }

    public TxResult getTxResult() {
        return txResult;
    }

    public void setTxResult(TxResult txResult) {
        this.txResult = txResult;
    }
}
