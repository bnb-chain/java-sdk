package com.binance.dex.api.client.domain.broadcast;


import com.binance.dex.api.client.encoding.message.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String  senderOtherChain;
    private String  randomNumberHash;
    private long    timestamp;
    private List<Token> outAmount;
    private String  expectedIncome;
    private long    heightSpan;
    private boolean crossChain;
}
