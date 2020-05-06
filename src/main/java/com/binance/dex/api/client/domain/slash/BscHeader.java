package com.binance.dex.api.client.domain.slash;

import com.binance.dex.api.client.encoding.serializer.BytesToPrefixedHexStringSerializer;
import com.binance.dex.api.client.encoding.serializer.LongToHexStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BscHeader {

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] parentHash;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] sha3Uncles;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] miner;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] stateRoot;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] transactionsRoot;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] receiptsRoot;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] logsBloom;

    @JsonSerialize(using = LongToHexStringSerializer.class)
    private Long difficulty;

    @JsonSerialize(using = LongToHexStringSerializer.class)
    private Long number;

    @JsonSerialize(using = LongToHexStringSerializer.class)
    private Long gasLimit;

    @JsonSerialize(using = LongToHexStringSerializer.class)
    private Long gasUsed;

    @JsonSerialize(using = LongToHexStringSerializer.class)
    private Long timestamp;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] extra;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] mixHash;

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] nonce;
}
