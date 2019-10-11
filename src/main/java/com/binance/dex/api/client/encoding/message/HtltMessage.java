package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.encoding.ToHexStringSerializer;
import com.binance.dex.api.client.encoding.VoteOptionSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bouncycastle.util.encoders.Hex;

import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-09-30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
@Setter
@Getter
public class HtltMessage implements BinanceDexTransactionMessage {

    private String from;
    private String to;

    @JsonProperty("recipient_other_chain")
    private String recipientOtherChain;

    @JsonProperty("sender_other_chain")
    private String senderOtherChain;

    @JsonProperty("random_number_hash")
    @JsonSerialize(using = ToHexStringSerializer.class)
    private byte[] randomNumberHash;

    private Long   timestamp;
    private List<Token> amount;

    @JsonProperty("expected_income")
    private String expectedIncome;

    @JsonProperty("height_span")
    private Long   heightSpan;

    @JsonProperty("cross_chain")
    private Boolean crossChain;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from", from)
                .append("to", to)
                .append("recipientOtherChain", recipientOtherChain)
                .append("senderOtherChain", senderOtherChain)
                .append("randomNumberHash", Hex.toHexString(randomNumberHash))
                .append("timestamp", timestamp)
                .append("amount", amount)
                .append("expectedIncome", expectedIncome)
                .append("heightSpan", heightSpan)
                .append("crossChain", crossChain)
                .toString();
    }


}
