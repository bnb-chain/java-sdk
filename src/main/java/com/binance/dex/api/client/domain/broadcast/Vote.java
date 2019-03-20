package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Vote {

    private Long proposalId;

    private Integer option;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("proposalId", proposalId)
                .append("option", option)
                .toString();
    }
}
