package com.binance.dex.api.client.domain.broadcast;


import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: fletcher.fan
 * @create: 2019-08-19
 */
@Getter
@Setter
public class HashTimerLockTransfer {

    private String  from;
    private String  to;
    private String  recipientOtherChain;
    private String  randomNumberHash;
    private long    timestamp;
    private String  symbol;
    private long    amount;
    private String  expectedIncome;
    private long    heightSpan;
    private boolean crossChain;
}
