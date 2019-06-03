package com.binance.dex.api.client.domain.broadcast;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimeUnlock {

    private String fromAddr;
    private Long lockId;

}
