package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionPageV2 {
    private Long total;
    private List<TransactionV2> txs;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<TransactionV2> getTxs() {
        return txs;
    }

    public void setTxs(List<TransactionV2> txs) {
        this.txs = txs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("total", total)
                .append("txs", txs)
                .toString();
    }
}
