package com.binance.dex.api.client.domain;


import com.binance.dex.api.client.encoding.message.Token;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-08-20
 */
@Getter
@Setter
public class AtomicSwap {

    private String from;
    private String to;

    @JsonProperty("out_amount")
    private List<Token> outAmount;
    @JsonProperty("in_amount")
    private List<Token> inAmount;
    @JsonProperty("expected_income")
    private String expectedIncome;
    @JsonProperty("recipient_other_chain")
    private String recipientOtherChain;
    @JsonProperty("random_number_hash")
    private String randomNumberHash;
    @JsonProperty("random_number")
    private String randomNumber;
    private Long   timestamp;
    @JsonProperty("cross_chain")
    private Boolean crossChain;
    @JsonProperty("expire_height")
    private Long    expireHeight;
    private Long    index;
    @JsonProperty("closed_time")
    private Long    closedTime;

    // 0:null 1:Open 2:Completed 3:Expired
    private String status;


}
