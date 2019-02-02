package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Peer {
    private Boolean accelerated;
    @JsonProperty("access_addr")
    private String accessAddress;
    private List<String> capabilities;
    private String id;
    @JsonProperty("listen_addr")
    private String listenAddress;
    private String moniker;
    private String network;
    @JsonProperty("stream_addr")
    private String streamAddress;
    private String version;

    public Boolean getAccelerated() {
        return accelerated;
    }

    public void setAccelerated(Boolean accelerated) {
        this.accelerated = accelerated;
    }

    public String getAccessAddress() {
        return accessAddress;
    }

    public void setAccessAddress(String accessAddress) {
        this.accessAddress = accessAddress;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListenAddress() {
        return listenAddress;
    }

    public void setListenAddress(String listenAddress) {
        this.listenAddress = listenAddress;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getStreamAddress() {
        return streamAddress;
    }

    public void setStreamAddress(String streamAddress) {
        this.streamAddress = streamAddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("accelerated", accelerated)
                .append("accessAddress", accessAddress)
                .append("capabilities", capabilities)
                .append("id", id)
                .append("listenAddress", listenAddress)
                .append("moniker", moniker)
                .append("network", network)
                .append("streamAddress", streamAddress)
                .append("version", version)
                .toString();
    }
}
