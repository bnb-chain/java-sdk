package com.binance.dex.api.client.domain.broadcast;


import com.binance.dex.api.client.encoding.message.Token;

import java.util.List;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class Deposit {

    private Long proposalId;
    private String depositer;
    private List<Token> amount;

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
