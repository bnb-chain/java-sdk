package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * Created by fletcher on 2019/5/15.
 */

@Setter
@Getter
public class Proposal {

    private String type;
    private Value value;

    @Getter
    @Setter
    public static class Value {
        @JsonProperty("proposal_id")
        private String proposalId;
        private String title;
        private String description;
        @JsonProperty("proposal_type")
        private String proposalType;
        @JsonProperty("voting_period")
        private Long votingPeriod;
        @JsonProperty("proposal_status")
        private String proposalStatus;
        @JsonProperty("tally_result")
        private TallyResult tallyResult;
        @JsonProperty("submit_time")
        private String submitTime;
        @JsonProperty("total_deposit")
        private List<com.binance.dex.api.client.encoding.message.Token> totalDeposit;
        @JsonProperty("voting_start_time")
        private String votingStartTime;

        @Getter
        @Setter
        public static class TallyResult {
            private String yes;
            private String abstain;
            private String no;
            @JsonProperty("no_with_veto")
            private String noWithVeto;
            private String total;
        }
    }
}
