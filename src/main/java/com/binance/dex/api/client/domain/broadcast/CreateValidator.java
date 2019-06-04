package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.encoding.message.Token;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class CreateValidator {

    private String delegatorAddress;
    private String validatorAddress;
    private Token delegation;
    private Long proposalId;

    public String getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public Token getDelegation() {
        return delegation;
    }

    public void setDelegation(Token delegation) {
        this.delegation = delegation;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }
}
