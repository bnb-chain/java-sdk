package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.encoding.message.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-09-30
 */
@Getter
@Setter
public class HtltReq {

    private String  recipient;
    private String  recipientOtherChain;
    private String  senderOtherChain;
    private byte[]  randomNumberHash;
    private long    timestamp;
    private List<Token> outAmount;
    private String  expectedIncome;
    private long    heightSpan;
    private boolean crossChain;

}
