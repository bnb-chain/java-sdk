package com.binance.dex.api.client;

public enum BinanceDexEnvironment {
    PROD(
            "https://dex.binance.org",
            "wss://dex.binance.org/api/",
            "bnb"
    ),
    TEST_NET(
            "https://testnet-dex.binance.org",
            "wss://testnet-dex.binance.org/api/",
            "tbnb"
    ),
    TEST_NET_NODE (
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

    private BinanceDexEnvironment(String baseUrl, String wsBaseUrl, String hrp) {
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
}
