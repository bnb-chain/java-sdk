package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fitz.Lu
 **/
public class BaseQueryParams {

    @JsonProperty(value = "SideChainId")
    protected String sideChainId;

    public BaseQueryParams() {
    }

    public BaseQueryParams(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }
}
