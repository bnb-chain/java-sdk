package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.encoding.message.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-08-19
 */
@Getter
@Setter
public class DepositHashTimerLock {

    private String from;
    private List<Token> amount;
    private String swapID;

}
