package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitBroadcastResult {

    private String hash;

    private Long height;

    @JsonProperty("check_tx")
    private CheckTx checkTx;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CheckTx {

        private Integer code = 0;

        private String log;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }

    public CheckTx getCheckTx() {
        return checkTx;
    }

    public void setCheckTx(CheckTx checkTx) {
        this.checkTx = checkTx;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }
}
