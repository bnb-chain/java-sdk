package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private Long blockHeight;
    private Integer code;
    private Long confirmBlocks;
    private String data;
    private String fromAddr;
    private String orderId;
    private String timeStamp;
    private String toAddr;
    private Long txAge;
    private String txAsset;
    private String txFee;
    private String txHash;
    private String txType;
    private String value;
    private String memo;

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getConfirmBlocks() {
        return confirmBlocks;
    }

    public void setConfirmBlocks(Long confirmBlocks) {
        this.confirmBlocks = confirmBlocks;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public Long getTxAge() {
        return txAge;
    }

    public void setTxAge(Long txAge) {
        this.txAge = txAge;
    }

    public String getTxAsset() {
        return txAsset;
    }

    public void setTxAsset(String txAsset) {
        this.txAsset = txAsset;
    }

    public String getTxFee() {
        return txFee;
    }

    public void setTxFee(String txFee) {
        this.txFee = txFee;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("blockHeight", blockHeight)
                .append("code", code)
                .append("confirmBlocks", confirmBlocks)
                .append("data", data)
                .append("fromAddr", fromAddr)
                .append("orderId", orderId)
                .append("timeStamp", timeStamp)
                .append("toAddr", toAddr)
                .append("txAge", txAge)
                .append("txAsset", txAsset)
                .append("txFee", txFee)
                .append("txHash", txHash)
                .append("txType", txType)
                .append("value", value)
                .append("memo", memo)
                .toString();
    }
}
