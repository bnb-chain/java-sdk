package com.binance.dex.api.client.domain.oracle;

import com.binance.dex.api.client.crosschain.Package;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class ClaimMsg {

    private int chainId;

    private long sequence;

    private List<Package> payload;

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

    public List<Package> getPayload() {
        return payload;
    }

    public void setPayload(List<Package> payload) {
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
        try {
            return "ClaimMsg{" +
                    "chainId=" + chainId +
                    ", sequence=" + sequence +
                    ", payload='" + EncodeUtils.toJsonStringSortKeys(payload) + '\'' +
                    ", validatorAddress='" + validatorAddress + '\'' +
                    '}';
        } catch (JsonProcessingException e) {
            throw new RuntimeException("failed to encode payload", e);
        }
    }
}
