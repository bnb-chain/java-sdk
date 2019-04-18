package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockMeta {

    @JsonProperty("block_id")
    private BlockId blockId;
    private Header header;

    public BlockId getBlockId() {
        return blockId;
    }

    public void setBlockId(BlockId blockId) {
        this.blockId = blockId;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BlockMetaResult {
        @JsonProperty("block_meta")
        private BlockMeta blockMeta;

        public BlockMeta getBlockMeta() {
            return blockMeta;
        }

        public void setBlockMeta(BlockMeta blockMeta) {
            this.blockMeta = blockMeta;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BlockId {
        private String hash;
        private Parts parts;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public Parts getParts() {
            return parts;
        }

        public void setParts(Parts parts) {
            this.parts = parts;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parts {
        private Integer total;
        private String hash;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Header {
        private Version version;

        @JsonProperty("chain_id")
        private String chainId;
        private Long height;
        private Date time;
        @JsonProperty("num_txs")
        private Integer numTxs;
        @JsonProperty("total_txs")
        private Integer totalTxs;
        @JsonProperty("last_block_id")
        private BlockId lastBlockId;
        @JsonProperty("last_commit_hash")
        private String lastCommitHash;
        @JsonProperty("data_hash")
        private String dataHash;
        @JsonProperty("validators_hash")
        private String validatorsHash;
        @JsonProperty("next_validators_hash")
        private String nextValidatorsHash;
        @JsonProperty("consensus_hash")
        private String consensusHash;
        @JsonProperty("app_hash")
        private String appHash;
        @JsonProperty("last_results_hash")
        private String lastResultsHash;
        @JsonProperty("evidence_hash")
        private String evidenceHash;
        @JsonProperty("proposer_address")
        private String proposerAddress;

        public Version getVersion() {
            return version;
        }

        public void setVersion(Version version) {
            this.version = version;
        }

        public String getChainId() {
            return chainId;
        }

        public void setChainId(String chainId) {
            this.chainId = chainId;
        }

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public Integer getNumTxs() {
            return numTxs;
        }

        public void setNumTxs(Integer numTxs) {
            this.numTxs = numTxs;
        }

        public Integer getTotalTxs() {
            return totalTxs;
        }

        public void setTotalTxs(Integer totalTxs) {
            this.totalTxs = totalTxs;
        }

        public BlockId getLastBlockId() {
            return lastBlockId;
        }

        public void setLastBlockId(BlockId lastBlockId) {
            this.lastBlockId = lastBlockId;
        }

        public String getLastCommitHash() {
            return lastCommitHash;
        }

        public void setLastCommitHash(String lastCommitHash) {
            this.lastCommitHash = lastCommitHash;
        }

        public String getDataHash() {
            return dataHash;
        }

        public void setDataHash(String dataHash) {
            this.dataHash = dataHash;
        }

        public String getValidatorsHash() {
            return validatorsHash;
        }

        public void setValidatorsHash(String validatorsHash) {
            this.validatorsHash = validatorsHash;
        }

        public String getNextValidatorsHash() {
            return nextValidatorsHash;
        }

        public void setNextValidatorsHash(String nextValidatorsHash) {
            this.nextValidatorsHash = nextValidatorsHash;
        }

        public String getConsensusHash() {
            return consensusHash;
        }

        public void setConsensusHash(String consensusHash) {
            this.consensusHash = consensusHash;
        }

        public String getAppHash() {
            return appHash;
        }

        public void setAppHash(String appHash) {
            this.appHash = appHash;
        }

        public String getLastResultsHash() {
            return lastResultsHash;
        }

        public void setLastResultsHash(String lastResultsHash) {
            this.lastResultsHash = lastResultsHash;
        }

        public String getEvidenceHash() {
            return evidenceHash;
        }

        public void setEvidenceHash(String evidenceHash) {
            this.evidenceHash = evidenceHash;
        }

        public String getProposerAddress() {
            return proposerAddress;
        }

        public void setProposerAddress(String proposerAddress) {
            this.proposerAddress = proposerAddress;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Version {
        private Integer block;
        private Integer app;

        public Integer getBlock() {
            return block;
        }

        public void setBlock(Integer block) {
            this.block = block;
        }

        public Integer getApp() {
            return app;
        }

        public void setApp(Integer app) {
            this.app = app;
        }
    }
}
