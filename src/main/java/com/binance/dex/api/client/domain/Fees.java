package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * https://testnet-dex.binance.org/api/v1/fees
 * Get Fees
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fees {
    @JsonProperty("msg_type")
    private String msgType;
    @JsonProperty("fee")
    private long fee;
    @JsonProperty("fee_for")
    private int feeFor;
    @JsonProperty("multi_transfer_fee")
    private String multiTransferFee;
    @JsonProperty("lower_limit_as_multi")
    private String lowerLimitAsMulti;
    @JsonProperty("fixed_fee_params")
    private FixedFeeParams fixedFeeParams;
    @JsonProperty("dex_fee_fields")
    private List<DexFeeField> dexFeeFields;


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

    public String getMultiTransferFee() {
        return multiTransferFee;
    }

    public void setMultiTransferFee(String multiTransferFee) {
        this.multiTransferFee = multiTransferFee;
    }

    public String getLowerLimitAsMulti() {
        return lowerLimitAsMulti;
    }

//    @JsonProperty("fixed_fee_params")
    public void setLowerLimitAsMulti(String lowerLimitAsMulti) {
        this.lowerLimitAsMulti = lowerLimitAsMulti;
    }
}
