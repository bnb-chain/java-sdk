package com.binance.dex.api.client.domain.jsonrpc;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.domain.NodeInfo;
import com.binance.dex.api.client.domain.SyncInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeInfos {

    @JsonProperty("node_info")
    private NodeInfo nodeInfo;
    @JsonProperty("sync_info")
    private SyncInfo syncInfo;
    @JsonProperty("validator_info")
    private ValidatorInfo validatorInfo;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ValidatorInfo {
        private String address;
        @JsonProperty("pub_key")
        private PubKey pubKey;
        @JsonProperty("voting_power")
        private Long votingPower;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public PubKey getPubKey() {
            return pubKey;
        }

        public void setPubKey(PubKey pubKey) {
            this.pubKey = pubKey;
        }

        public Long getVotingPower() {
            return votingPower;
        }

        public void setVotingPower(Long votingPower) {
            this.votingPower = votingPower;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PubKey {
        private String type;
        private byte[] value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }
    }

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
