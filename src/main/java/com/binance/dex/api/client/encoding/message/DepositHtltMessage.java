package com.binance.dex.api.client.encoding.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-10-09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
@Setter
@Getter
public class DepositHtltMessage implements BinanceDexTransactionMessage {

    private String from;

    private List<Token> amount;

    @JsonProperty("swap_id")
    private String swapId;

}
