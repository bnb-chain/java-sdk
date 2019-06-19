package com.binance.dex.api.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BinanceDexEnvironment {
    public static final BinanceDexEnvironment PROD = new BinanceDexEnvironment(
            "https://dex.binance.org",
            "https://dataseed1.ninicoin.io",
            "wss://dataseed1.ninicoin.io/websocket",
            "bnb"
    );
    public static final BinanceDexEnvironment TEST_NET = new BinanceDexEnvironment(
            "https://testnet-dex.binance.org",
            "http://data-seed-pre-0-s3.binance.org",
            "wss://data-seed-pre-0-s3.binance.org/websocket",
            "tbnb"
    );

    // Rest API base URL
    private String baseUrl;
    // RPC API base URL
    private String nodeUrl;
    // Websocket API base URL
    private String wsBaseUrl;
    // Address human readable part prefix
    private String hrp;

    public BinanceDexEnvironment(String baseUrl,String nodeUrl ,String wsBaseUrl, String hrp) {
        this.baseUrl = baseUrl;
        this.nodeUrl = nodeUrl;
        this.wsBaseUrl = wsBaseUrl;
        this.hrp = hrp;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getWsBaseUrl() {
        return wsBaseUrl;
    }

    public String getHrp() {
        return hrp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BinanceDexEnvironment that = (BinanceDexEnvironment) o;

        return new EqualsBuilder()
                .append(baseUrl, that.baseUrl)
                .append(wsBaseUrl, that.wsBaseUrl)
                .append(hrp, that.hrp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(baseUrl)
                .append(wsBaseUrl)
                .append(hrp)
                .toHashCode();
    }

    public String getNodeUrl() {
        return nodeUrl;
    }
}
