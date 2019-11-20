package com.binance.dex.api.client.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersUpdateEvent {
    @JsonProperty("stream")
    private String stream;

    public String getStream() {
        return stream;
    }

    @Override
    public String toString() {
        return "OrdersUpdateEvent{" +
                "stream='" + stream + '\'' +
                ", data=" + data +
                '}';
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public ArrayList<ExecutionReport> getData() {
        return data;
    }

    public void setData(ArrayList<ExecutionReport> data) {
        this.data = data;
    }

    @JsonProperty("data")
    private ArrayList<ExecutionReport> data;
}
