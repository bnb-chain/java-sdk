package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Validators {
    @JsonProperty("block_height")
    private Long blockHeight;
    private List<ValidatorInfo> validators;

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public List<ValidatorInfo> getValidators() {
        return validators;
    }

    public void setValidators(List<ValidatorInfo> validators) {
        this.validators = validators;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("blockHeight", blockHeight)
                .append("validators", validators)
                .toString();
    }
}
