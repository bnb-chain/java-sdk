package com.binance.dex.api.client.domain.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TxSearchRequest {

    private String query;
    private Boolean prove;
    private String page;
    @JsonProperty("per_page")
    private String perPage;


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public Boolean getProve() {
        return prove;
    }

    public void setProve(Boolean prove) {
        this.prove = prove;
    }
}
