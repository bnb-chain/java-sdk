package com.binance.dex.api.client.domain.oracle;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Fitz.Lu
 **/
public class ClaimMsg {

    private int chainId;

    private long sequence;

    private byte[] payload;

    private String validatorAddress;

    public int getChainId() {
        return chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    @Override
    public String toString() {
        return "ClaimMsg{" +
                "chainId=" + chainId +
                ", sequence=" + sequence +
                ", payload='" + Hex.encodeHexString(payload) + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                '}';
    }
}
