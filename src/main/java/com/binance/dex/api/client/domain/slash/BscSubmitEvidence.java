package com.binance.dex.api.client.domain.slash;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BscSubmitEvidence {

    private String submitter;

    private BscHeader[] headers;
}
