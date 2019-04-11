package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMetadata {
    private int code;
    private String data;
    private String hash;
    private String log;
    private Long height;
    private boolean ok;

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public String getLog() {
        return log;
    }

    public boolean isOk() {
        return ok;
    }

    public Long getHeight() {
        return height;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("code", code)
                .append("data", data)
                .append("hash", hash)
                .append("log", log)
                .append("ok", ok)
                .toString();
    }
}
