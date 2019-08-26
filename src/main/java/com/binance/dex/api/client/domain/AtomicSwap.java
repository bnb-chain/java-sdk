package com.binance.dex.api.client.domain;


import lombok.Getter;
import lombok.Setter;

/**
 * @author: fletcher.fan
 * @create: 2019-08-20
 */
@Getter
@Setter
public class AtomicSwap {

    private String from;
    private String to;
    private String outSymbol;
    private Long   outAmount;
    private String inSymbol;
    private Long   inAmount;
    private String expectedIncome;
    private String recipientOtherChain;
    private String randomNumberHash;
    private String randomNumber;
    private Long   timestamp;
    private Boolean crossChain;
    private Long    expireHeight;
    private Long    index;
    private Long    closedTime;

    // 0:null 1:Open 2:Completed 3:Expired
    private Integer status;


}
