package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.encoding.message.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TimeRelock {

    private String fromAddr;
    private String description;
    private List<Token> amount;
    private Date lockTime;
    private Long lockId;

}
