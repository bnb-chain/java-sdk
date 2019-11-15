package com.binance.dex.api.client.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionReport {
    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventHeight;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("S")
    private int side;

    @JsonProperty("o")
    private int orderType;

    @JsonProperty("f")
    private int timeInForce;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getEventHeight() {
        return eventHeight;
    }

    public void setEventHeight(Long eventHeight) {
        this.eventHeight = eventHeight;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
    }

    @Override
    public String toString() {
        return "ExecutionReport{" +
                "eventType='" + eventType + '\'' +
                ", eventHeight=" + eventHeight +
                ", symbol='" + symbol + '\'' +
                ", side=" + side +
                ", orderType=" + orderType +
                ", timeInForce=" + timeInForce +
                ", orderQuantity='" + orderQuantity + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", currentExecutionType='" + currentExecutionType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId='" + orderId + '\'' +
                ", lastExecutedQuantity='" + lastExecutedQuantity + '\'' +
                ", cumulativeFilledQuantity='" + cumulativeFilledQuantity + '\'' +
                ", lastExecutedPrice='" + lastExecutedPrice + '\'' +
                ", commissionAmount='" + commissionAmount + '\'' +
                ", transactionTime=" + transactionTime +
                ", tradeId='" + tradeId + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCurrentExecutionType() {
        return currentExecutionType;
    }

    public void setCurrentExecutionType(String currentExecutionType) {
        this.currentExecutionType = currentExecutionType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLastExecutedQuantity() {
        return lastExecutedQuantity;
    }

    public void setLastExecutedQuantity(String lastExecutedQuantity) {
        this.lastExecutedQuantity = lastExecutedQuantity;
    }

    public String getCumulativeFilledQuantity() {
        return cumulativeFilledQuantity;
    }

    public void setCumulativeFilledQuantity(String cumulativeFilledQuantity) {
        this.cumulativeFilledQuantity = cumulativeFilledQuantity;
    }

    public String getLastExecutedPrice() {
        return lastExecutedPrice;
    }

    public void setLastExecutedPrice(String lastExecutedPrice) {
        this.lastExecutedPrice = lastExecutedPrice;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("q")
    private String orderQuantity;

    @JsonProperty("p")
    private String orderPrice;

    @JsonProperty("x")
    private String currentExecutionType;

    @JsonProperty("X")
    private String orderStatus;

    @JsonProperty("i")
    private String orderId;

    @JsonProperty("l")
    private String lastExecutedQuantity;

    @JsonProperty("z")
    private String cumulativeFilledQuantity;

    @JsonProperty("L")
    private String lastExecutedPrice;

    @JsonProperty("n")
    private String commissionAmount;

    @JsonProperty("T")
    private Long transactionTime;

    @JsonProperty("t")
    private String tradeId;

    @JsonProperty("O")
    private Long createTime;
}
