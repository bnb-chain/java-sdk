package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeInfo {
    private String id;
    @JsonProperty("listen_addr")
    private String listenAddr;
    private String network;
    private String version;
    private String channels;
    private String moniker;
    private Map<String, Object> other;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListenAddr() {
        return listenAddr;
    }

    public void setListenAddr(String listenAddr) {
        this.listenAddr = listenAddr;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("id", id)
                .append("listenAddr", listenAddr)
                .append("network", network)
                .append("version", version)
                .append("channels", channels)
                .append("moniker", moniker)
                .append("other", other)
                .toString();
    }
}
