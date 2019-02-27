package com.binance.dex.api.client.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixedFeeParams {

    @JsonProperty("msg_type")
    private String msgType;
    @JsonProperty("fee")
    private long fee;
    @JsonProperty("fee_for")
    private int feeFor;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public int getFeeFor() {
        return feeFor;
    }

    public void setFeeFor(int feeFor) {
        this.feeFor = feeFor;
    }
}
