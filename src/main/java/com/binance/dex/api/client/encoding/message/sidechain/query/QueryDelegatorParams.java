package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fitz.Lu
 **/
public class QueryDelegatorParams extends BaseQueryParams {

    @JsonProperty(value = "DelegatorAddr")
    private String delegatorAddr;

    public String getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(String delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }
}
