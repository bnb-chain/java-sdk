package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.stake.sidechain.SideChainUnBond;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.NumberUtil;
import com.binance.dex.api.client.utils.converter.Token;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FSideChainUndelegateConverter extends TransactionConverter<SideChainUnBond> {

    @Override
    public TxType getType() {
        return TxType.SIDECHAIN_UNBOND;
    }

    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        transaction.setValue(NumberUtil.longToBigDecimalString(transactionV2.getAmount()));

        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        if (transaction.getValue() == null || transaction.getTxAsset() == null) {
            Token token = getToken(map, "amount");
            transaction.setTxAsset(token.getDenom());
            transaction.setValue(NumberUtil.longToBigDecimalString(token.getAmount()));
        }

        map.put("delegatorAddress", map.get("delegatorAddr"));
        map.put("validatorAddress", map.get("validatorAddr"));
        map.remove("delegatorAddr");
        map.remove("validatorAddr");
        transaction.setData(JsonUtil.toJson(map));
    }
}
