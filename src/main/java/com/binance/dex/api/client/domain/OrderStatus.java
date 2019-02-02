package com.binance.dex.api.client.domain;

public enum OrderStatus {
    Ack,
    PartialFill,
    IocNoFill,
    FullyFill,
    Canceled,
    Expired,
    Unknown
}
