package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.encoding.message.InputOutput;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Getter
@Setter
public class TransferInfo {
    private List<InputOutput> inputs;
    private List<InputOutput> outputs;

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("inputs", inputs)
                .append("outputs", outputs)
                .toString();
    }
}
