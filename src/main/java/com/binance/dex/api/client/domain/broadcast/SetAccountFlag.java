package com.binance.dex.api.client.domain.broadcast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetAccountFlag {

    private String fromAddr;

    private Long flags;

}
