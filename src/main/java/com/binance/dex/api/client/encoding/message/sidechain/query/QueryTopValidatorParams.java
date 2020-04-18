package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fitz.Lu
 **/
public class QueryTopValidatorParams {

    @JsonProperty(value = "SideChainId")
    private String sideChainId;

    @JsonProperty(value = "Top")
    private int top;

    public QueryTopValidatorParams() {
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
