package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.encoding.VoteOptionSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class VoteMessage implements BinanceDexTransactionMessage  {

    @JsonProperty("proposal_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long proposalId;
    private String voter;
    @JsonSerialize(using = VoteOptionSerializer.class)
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

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("proposalId", proposalId)
                .append("option", option)
                .toString();
    }


}
