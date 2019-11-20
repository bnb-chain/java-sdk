package com.binance.dex.api.client.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUpdateEvent {
    @JsonProperty("stream")
    private String stream;

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "AccountUpdateEvent{" +
                "stream='" + stream + '\'' +
                ", data=" + data +
                '}';
    }


    public OutboundAccountInfo getData() {
        return data;
    }

    public void setData(OutboundAccountInfo data) {
        this.data = data;
    }

    @JsonProperty("data")
    private OutboundAccountInfo data;
}
