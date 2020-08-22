package com.binance.dex.api.client.domain.broadcast;

import java.util.List;

import com.binance.dex.api.client.domain.ProposalType;
import com.binance.dex.api.client.encoding.message.Token;

public class SideSubmitProposal {
    private String title;
    private String description;
    private ProposalType proposalType;
    private String proposer;
    private List<Token> initDeposit;
    private Long votingPeriod;
    private String sideChainId;

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProposalType getProposalType() {
        return proposalType;
    }

    public void setProposalType(ProposalType proposalType) {
        this.proposalType = proposalType;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public List<Token> getInitDeposit() {
        return initDeposit;
    }

    public void setInitDeposit(List<Token> initDeposit) {
        this.initDeposit = initDeposit;
    }

    public Long getVotingPeriod() {
        return votingPeriod;
    }

    public void setVotingPeriod(Long votingPeriod) {
        this.votingPeriod = votingPeriod;
    }

    @Override
    public String toString() {
        return "SubmitProposal{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", proposalType=" + proposalType +
                ", proposer='" + proposer + '\'' +
                ", initDeposit=" + initDeposit +
                ", votingPeriod=" + votingPeriod +
                ", sideChainId=" + sideChainId +
                '}';
    }
}
