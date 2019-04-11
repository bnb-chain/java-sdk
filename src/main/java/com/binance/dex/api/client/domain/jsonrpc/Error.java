package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {
    private Integer code;
    private String message;
    private String data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
