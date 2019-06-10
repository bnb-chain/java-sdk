package com.binance.dex.api.client.domain.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonRequest {

    @JsonProperty("jsonrpc")
    private String jsonRpc;

    private String id;

    private String method;

    private Object params;

    public String getJsonRpc() {
        return jsonRpc;
    }

    public void setJsonRpc(String jsonRpc) {
        this.jsonRpc = jsonRpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
