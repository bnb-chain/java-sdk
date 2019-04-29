package com.binance.dex.api.client.domain.broadcast;

public class Vote {

    private Long proposalId;

    private Integer option;

    private String voter;

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
                ", voter='" + voter + '\'' +
                '}';
    }
}
