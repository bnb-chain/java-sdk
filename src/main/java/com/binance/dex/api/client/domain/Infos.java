package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Infos {
    @JsonProperty("node_info")
    private NodeInfo nodeInfo;
    @JsonProperty("sync_info")
    private SyncInfo syncInfo;
    @JsonProperty("validator_info")
    private ValidatorInfo validatorInfo;

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public SyncInfo getSyncInfo() {
        return syncInfo;
    }

    public void setSyncInfo(SyncInfo syncInfo) {
        this.syncInfo = syncInfo;
    }

    public ValidatorInfo getValidatorInfo() {
        return validatorInfo;
    }

    public void setValidatorInfo(ValidatorInfo validatorInfo) {
        this.validatorInfo = validatorInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("nodeInfo", nodeInfo)
                .append("syncInfo", syncInfo)
                .append("validatorInfo", validatorInfo)
                .toString();
    }
}
