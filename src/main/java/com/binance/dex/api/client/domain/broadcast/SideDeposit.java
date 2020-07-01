package com.binance.dex.api.client.domain.broadcast;

import java.util.List;

import com.binance.dex.api.client.encoding.message.Token;
/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class SideDeposit {

    private Long proposalId;
    private String depositer;
    private List<Token> amount;
    private String sideChainId;

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }
    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getDepositer() {
        return depositer;
    }

    public void setDepositer(String depositer) {
        this.depositer = depositer;
    }

    public List<Token> getAmount() {
        return amount;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }
}
