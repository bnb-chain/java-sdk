package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TxResult {
    private byte[] data;
    private String log;
    private Integer code = 0;
    private List<Tag> tags;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static class Tag {
        private byte[] key;
        private byte[] value;

        public byte[] getKey() {
            return key;
        }

        public void setKey(byte[] key) {
            this.key = key;
        }

        public byte[] getValue() {
            return value;
        }

        public void setValue(byte[] value) {
            this.value = value;
        }
    }
}
