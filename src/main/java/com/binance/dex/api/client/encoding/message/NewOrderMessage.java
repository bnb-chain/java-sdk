package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class NewOrderMessage implements BinanceDexTransactionMessage {
    private String id;
    @JsonProperty("ordertype")
    private OrderType orderType;
    private long price;
    private long quantity;
    private String sender;
    private OrderSide side;
    private String symbol;
    @JsonProperty("timeinforce")
    private TimeInForce timeInForce;

    private NewOrderMessage() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sender", sender)
                .append("id", id)
                .append("symbol", symbol)
                .append("orderType", orderType)
                .append("side", side)
                .append("price", price)
                .append("quantity", quantity)
                .append("timeInForce", timeInForce)
                .toString();
    }

    public static NewOrderBuilder newBuilder() {
        return new NewOrderBuilder();
    }

    public NewOrderBuilder toBuilder() {
        return newBuilder()
                .setSender(this.sender)
                .setId(this.id)
                .setSymbol(this.symbol)
                .setOrderType(this.orderType)
                .setSide(this.side)
                .setPrice(TransactionRequestAssembler.longToDouble(this.price))
                .setQuantity(TransactionRequestAssembler.longToDouble(this.quantity))
                .setTimeInForce(this.timeInForce);
    }

    /**
     * Builder class for NewOrderMessage transaction. It handles price/quantity conversion from double to long.
     */
    public static class NewOrderBuilder {
        private String id;
        private OrderType orderType;
        private String price;
        private String quantity;
        private String sender;
        private OrderSide side;
        private String symbol;
        private TimeInForce timeInForce;

        public NewOrderMessage build() {
            NewOrderMessage newOrder = new NewOrderMessage();
            newOrder.setId(id);
            newOrder.setOrderType(orderType);
            newOrder.setPrice(TransactionRequestAssembler.doubleToLong(price));
            newOrder.setQuantity(TransactionRequestAssembler.doubleToLong(quantity));
            newOrder.setSender(sender);
            newOrder.setSide(side);
            newOrder.setSymbol(symbol);
            newOrder.setTimeInForce(timeInForce);
            return newOrder;
        }

        public String getId() {
            return id;
        }

        public NewOrderBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public OrderType getOrderType() {
            return orderType;
        }

        public NewOrderBuilder setOrderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public String getPrice() {
            return price;
        }

        public NewOrderBuilder setPrice(String price) {
            this.price = price;
            return this;
        }

        public String getQuantity() {
            return quantity;
        }

        public NewOrderBuilder setQuantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public String getSender() {
            return sender;
        }

        public NewOrderBuilder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public OrderSide getSide() {
            return side;
        }

        public NewOrderBuilder setSide(OrderSide side) {
            this.side = side;
            return this;
        }

        public String getSymbol() {
            return symbol;
        }

        public NewOrderBuilder setSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public TimeInForce getTimeInForce() {
            return timeInForce;
        }

        public NewOrderBuilder setTimeInForce(TimeInForce timeInForce) {
            this.timeInForce = timeInForce;
            return this;
        }
    }
}
