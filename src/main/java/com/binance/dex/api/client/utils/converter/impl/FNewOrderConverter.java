package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class FNewOrderConverter extends TransactionConverter<NewOrder> {

    public TxType getType() {
        return TxType.NEW_ORDER;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        String symbol = (String) map.get("symbol");
        String orderId = (String) map.get("orderId");
        String orderType = (String) map.get("orderType");
        String side = (String) map.get("side");
        String timeInForce = (String) map.get("timeInForce");
        String price = (String) map.get("price");
        String quantity = (String) map.get("quantity");

        transaction.setOrderId(orderId);
        transaction.setTxAsset(symbol.split("_")[0]);

        Map<String, Object> newMap = new HashMap<>();
        newMap.put("orderData", NO.builder().symbol(symbol).orderId(orderId)
                .orderType(orderType).side(side).timeInForce(timeInForce)
                .price(NumberUtil.longToBigDecimal(Long.parseLong(price)).stripTrailingZeros().toPlainString())
                .quantity(NumberUtil.longToBigDecimal(Long.parseLong(quantity)).stripTrailingZeros().toPlainString())
                .build());
        transaction.setData(JsonUtil.toJson(newMap));

        BigDecimal value = NumberUtil.longToBigDecimal(Long.parseLong(price)).multiply(NumberUtil.longToBigDecimal(Long.parseLong(quantity)).setScale(8, BigDecimal.ROUND_HALF_UP));
        transaction.setValue(NumberUtil.decimalFormat(value));
    }
}

@Data
@Builder
class NO {
    String symbol;
    String orderId;
    String orderType;
    String side;
    String price;
    String quantity;
    String timeInForce;
}
