package com.binance.dex.api.client.domain.broadcast;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: fletcher.fan
 * @create: 2019-08-19
 */
@Getter
@Setter
public class ClaimHashTimerLock {

    private String from;
    private String swapID;
    private String randomNumber;

}
