package com.binance.dex.api.client.utils.converter.impl;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.domain.broadcast.TxType;
import com.binance.dex.api.client.domain.slash.BscSubmitEvidence;
import com.binance.dex.api.client.utils.converter.JsonUtil;
import com.binance.dex.api.client.utils.converter.TransactionConverter;

import java.util.Map;


public class FBscSubmitEvidenceConverter extends TransactionConverter<BscSubmitEvidence> {

    @Override
    public TxType getType() {
        return TxType.BSC_SUBMIT_EVIDENCE;
    }


    @Override
    public void doConvert(TransactionV2 transactionV2, Transaction transaction) {
        Map<String, Object> map = JsonUtil.fromJson(transactionV2.getData(), Map.class);
        map.put("submitter", transactionV2.getFromAddr());
        transaction.setData(JsonUtil.toJson(map));
    }
}
