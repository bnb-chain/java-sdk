package com.binance.dex.api.client.domain.oracle;

import java.util.Map;

/**
 * @author Fitz.Lu
 **/
public class Prophecy {

    private String id;

    private Status status;

    private Map<String, byte[]> claimValidators;

    private Map<String, String> validatorClaims;

    public Prophecy() {
    }

    public Prophecy(String id, Status status, Map<String, byte[]> claimValidators, Map<String, String> validatorClaims) {
        this.id = id;
        this.status = status;
        this.claimValidators = claimValidators;
        this.validatorClaims = validatorClaims;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, byte[]> getClaimValidators() {
        return claimValidators;
    }

    public void setClaimValidators(Map<String, byte[]> claimValidators) {
        this.claimValidators = claimValidators;
    }

    public Map<String, String> getValidatorClaims() {
        return validatorClaims;
    }

    public void setValidatorClaims(Map<String, String> validatorClaims) {
        this.validatorClaims = validatorClaims;
    }
}
