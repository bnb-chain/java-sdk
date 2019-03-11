package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class MultiTransfer {
    private String fromAddress;
    private List<Output> outputs;

    public MultiTransfer() {
    }

    public MultiTransfer(String fromAddress, List<Output> outputs) {
        this.fromAddress = fromAddress;
        this.outputs = outputs;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("fromAddress", fromAddress)
                .append("outputs", outputs)
                .toString();
    }
}
