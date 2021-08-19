package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


public class FCancelOrderConverter extends TransactionConverter<CancelOrder> {

    public TxType getType() {
        return TxType.CANCEL_ORDER;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        String symbol = (String) map.get("symbol");
        String orderId = (String) map.get("orderId");

        Map<String, Object> newMap = new HashMap<>();
        newMap.put("orderData", CO.builder().symbol(symbol).orderId(orderId).build());
        transaction.setData(JsonUtil.toJson(newMap));
    }
}

@Data
@Builder
class CO {
    String symbol;
    String orderId;
}