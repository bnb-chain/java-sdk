package com.binance.dex.api.client.utils.converter;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.proto.Send;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Token {
    @JsonProperty(value = "denom")
    private String denom;
    @JsonProperty(value = "amount")
    private Long amount;

    public static com.binance.dex.api.client.encoding.message.Token of(com.binance.dex.api.proto.Token source) {
        com.binance.dex.api.client.encoding.message.Token token = new com.binance.dex.api.client.encoding.message.Token();
        token.setDenom(source.getDenom());
        token.setAmount(source.getAmount());
        return token;
    }

    public static com.binance.dex.api.client.encoding.message.Token of(Send.Token sendToken) {
        com.binance.dex.api.client.encoding.message.Token token = new com.binance.dex.api.client.encoding.message.Token();
        token.setDenom(sendToken.getDenom());
        token.setAmount(sendToken.getAmount());
        return token;
    }

    public Token() {
    }

    public Token(String denom, Long amount) {
        this.denom = denom;
        this.amount = amount;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("denom", denom)
                .append("amount", amount)
                .toString();
    }
}
