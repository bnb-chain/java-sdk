package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Optional fields for Binance DEX standard transaction
 */
public class TransactionOption {

    public static final TransactionOption DEFAULT_INSTANCE =
            new TransactionOption("", BinanceDexConstants.BINANCE_DEX_API_CLIENT_JAVA_SOURCE, null);

    private String memo;
    private long source;
    private byte[] data;

    public TransactionOption(String memo, long source, byte[] data) {
        this.memo = memo;
        this.source = source;
        this.data = data;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("memo", memo)
                .append("source", source)
                .append("data", data)
                .toString();
    }
}
