package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferMessage implements BinanceDexTransactionMessage {
    private List<InputOutput> inputs;
    private List<InputOutput> outputs;

    public List<InputOutput> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputOutput> inputs) {
        this.inputs = inputs;
    }

    public List<InputOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<InputOutput> outputs) {
        this.outputs = outputs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("inputs", inputs)
                .append("outputs", outputs)
                .toString();
    }
}
