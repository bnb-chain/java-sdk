package com.binance.dex.api.client.encoding.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class SignData {
    @JsonProperty("chain_id")
    private String chainId;
    @JsonProperty("account_number")
    private String accountNumber;
    private String sequence;
    private String memo;
    private BinanceDexTransactionMessage[] msgs;
    private String source;
    private byte[] data;

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BinanceDexTransactionMessage[] getMsgs() {
        return msgs;
    }

    public void setMsgs(BinanceDexTransactionMessage[] msgs) {
        this.msgs = msgs;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("chainId", chainId)
                .append("accountNumber", accountNumber)
                .append("sequence", sequence)
                .append("memo", memo)
                .append("msgs", msgs)
                .append("source", source)
                .append("data", data)
                .toString();
    }
}
