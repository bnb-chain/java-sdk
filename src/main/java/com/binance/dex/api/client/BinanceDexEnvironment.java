package com.binance.dex.api.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BinanceDexEnvironment {
    public static final BinanceDexEnvironment PROD = new BinanceDexEnvironment(
            "https://dex.binance.org",
            "wss://dex.binance.org/api/",
            "bnb"
    );
    public static final BinanceDexEnvironment TEST_NET = new BinanceDexEnvironment(
            "https://testnet-dex.binance.org",
            "wss://testnet-dex.binance.org/api/",
            "tbnb"
    );
    public static final BinanceDexEnvironment QA = new BinanceDexEnvironment(
            "https://dex-api.fdgahl.cn",
            "wss://dex-api.fdgahl.cn/api/",
            "tbnb"
    );
    public static final BinanceDexEnvironment TEST_NET_NODE = new BinanceDexEnvironment(
            "http://data-seed-pre-0-s3.binance.org",
            "",
            "tbnb"
    );

    // Rest API base URL
    private String baseUrl;
    // Websocket API base URL
    private String wsBaseUrl;
    // Address human readable part prefix
    private String hrp;

    public BinanceDexEnvironment(String baseUrl, String wsBaseUrl, String hrp) {
        this.baseUrl = baseUrl;
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
}
