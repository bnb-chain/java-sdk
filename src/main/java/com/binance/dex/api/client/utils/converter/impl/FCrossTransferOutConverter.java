package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.bridge.TransferOut;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.Token;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FCrossTransferOutConverter extends TransactionConverter<TransferOut> {

    @Override
    public TxType getType() {
        return TxType.TRANSFER_OUT;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));

        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        map.put("from", transactionV2.getFromAddr());
        map.put("toAddress", transactionV2.getToAddr());

        Token amount = Token.builder().denom(transactionV2.getAsset()).amount(transactionV2.getAmount()).build();
        map.put("amount", amount);

        transaction.setData(JsonUtil.toJson(map));
    }

}
