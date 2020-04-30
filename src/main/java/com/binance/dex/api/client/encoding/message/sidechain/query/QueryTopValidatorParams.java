package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fitz.Lu
 **/
public class QueryTopValidatorParams extends BaseQueryParams {

    @JsonProperty(value = "Top")
    private int top;

    public QueryTopValidatorParams() {
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
