package com.binance.dex.api.client.domain.oracle;

/**
 * @author Fitz.Lu
 **/
public class ClaimMsg {

    private int claimType;

    private long sequence;

    private String claim;

    private String validatorAddress;

    public int getClaimType() {
        return claimType;
    }

    public void setClaimType(int claimType) {
        this.claimType = claimType;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
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
                "claimType=" + claimType +
                ", sequence=" + sequence +
                ", claim='" + claim + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                '}';
    }
}
