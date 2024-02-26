package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.encoding.message.Token;

public class SideChainStakeMigration {

    private String validatorSrcAddr;

    private String validatorDstAddr;

    private String delegatorAddr;

    private String refundAddr;

    private Token amount;

    public SideChainStakeMigration() {
    }

    public String getValidatorSrcAddr() {
        return validatorSrcAddr;
    }

    public void setValidatorSrcAddr(String validatorSrcAddr) {
        this.validatorSrcAddr = validatorSrcAddr;
    }

    public String getValidatorDstAddr() {
        return validatorDstAddr;
    }

    public void setValidatorDstAddr(String validatorDstAddr) {
        this.validatorDstAddr = validatorDstAddr;
    }

    public String getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(String delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public String getRefundAddr() {
        return refundAddr;
    }

    public void setRefundAddr(String refundAddr) {
        this.refundAddr = refundAddr;
    }

    public Token getAmount() {
        return amount;
    }

    public void setAmount(Token amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SideChainStakeMigration{" +
                "validatorSrcAddr='" + validatorSrcAddr + '\'' +
                ", validatorDstAddr='" + validatorDstAddr + '\'' +
                ", delegatorAddr='" + delegatorAddr + '\'' +
                ", refundAddr='" + refundAddr + '\'' +
                ", amount=" + amount +
                '}';
    }
}
