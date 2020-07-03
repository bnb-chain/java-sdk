package com.binance.dex.api.client.domain.broadcast;

public class SideVote {

    private Long proposalId;

    private Integer option;

    private String voter;

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

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "proposalId=" + proposalId +
                ", option=" + option +
                ", sideChainId=" + sideChainId +
                ", voter='" + voter + '\'' +
                '}';
    }
}
