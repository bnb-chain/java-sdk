package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProposalType {


    Nil(0L),
    Text(1L),
    ParameterChange(2L),
    SoftwareUpgrade(3L),
    ListTradingPair(4L),
    FeeChange(5L),
    CreateValidator(6L),
    RemoveValidator(7L),
    DelistTradingPair(8L),
    SideChainParamsChange(129L),
    CrossSideChainParamsChange(130L);

    private long value;

    ProposalType(Long value) {
        this.value = value;
    }

    @JsonCreator
    public static ProposalType fromValue(long value) {
        for (ProposalType ot : ProposalType.values()) {
            if (ot.value == value) {
                return ot;
            }
        }
        return null;
    }

    @JsonValue
    public String toName() {
        return this.name();
    }
}
