package com.binance.dex.api.client.utils.converter;

import java.util.HashMap;
import java.util.Map;

public enum TxType {
    NEW_ORDER(com.binance.dex.api.client.domain.broadcast.TxType.NEW_ORDER),
    ISSUE_TOKEN(com.binance.dex.api.client.domain.broadcast.TxType.ISSUE),
    BURN_TOKEN(com.binance.dex.api.client.domain.broadcast.TxType.BURN),
    LIST_TOKEN(com.binance.dex.api.client.domain.broadcast.TxType.LISTING),
    CANCEL_ORDER(com.binance.dex.api.client.domain.broadcast.TxType.CANCEL_ORDER),
    FREEZE_TOKEN(com.binance.dex.api.client.domain.broadcast.TxType.FREEZE_TOKEN),
    UN_FREEZE_TOKEN(com.binance.dex.api.client.domain.broadcast.TxType.UNFREEZE_TOKEN),
    TRANSFER(com.binance.dex.api.client.domain.broadcast.TxType.TRANSFER),
    PROPOSAL(com.binance.dex.api.client.domain.broadcast.TxType.SUBMIT_PROPOSAL),
    SIDE_PROPOSAL(com.binance.dex.api.client.domain.broadcast.TxType.SIDE_SUBMIT_PROPOSAL),
    VOTE(com.binance.dex.api.client.domain.broadcast.TxType.VOTE),
    SIDE_VOTE(com.binance.dex.api.client.domain.broadcast.TxType.SIDE_VOTE),
    DEPOSIT(com.binance.dex.api.client.domain.broadcast.TxType.DEPOSIT),
    SIDE_DEPOSIT(com.binance.dex.api.client.domain.broadcast.TxType.SIDE_DEPOSIT),
    MINT(com.binance.dex.api.client.domain.broadcast.TxType.MINT),
    CREATE_VALIDATOR(com.binance.dex.api.client.domain.broadcast.TxType.CREATE_VALIDATOR),
    REMOVE_VALIDATOR(com.binance.dex.api.client.domain.broadcast.TxType.REMOVE_VALIDATOR),
    TIME_LOCK(com.binance.dex.api.client.domain.broadcast.TxType.TimeLock),
    TIME_UNLOCK(com.binance.dex.api.client.domain.broadcast.TxType.TimeUnlock),
    TIME_RELOCK(com.binance.dex.api.client.domain.broadcast.TxType.TimeRelock),
    SET_ACCOUNT_FLAG(com.binance.dex.api.client.domain.broadcast.TxType.SetAccountFlag),
    HTL_TRANSFER(com.binance.dex.api.client.domain.broadcast.TxType.HTL_TRANSFER),
    DEPOSIT_HTL(com.binance.dex.api.client.domain.broadcast.TxType.DEPOSIT_HTL),
    CLAIM_HTL(com.binance.dex.api.client.domain.broadcast.TxType.CLAIM_HTL),
    REFUND_HTL(com.binance.dex.api.client.domain.broadcast.TxType.REFUND_HTL),
    CREATE_SIDECHAIN_VALIDATOR(com.binance.dex.api.client.domain.broadcast.TxType.CREATE_SIDECHAIN_VALIDATOR),
    EDIT_SIDECHAIN_VALIDATOR(com.binance.dex.api.client.domain.broadcast.TxType.EDIT_SIDECHAIN_VALIDATOR),
    SIDECHAIN_DELEGATE(com.binance.dex.api.client.domain.broadcast.TxType.SIDECHAIN_DELEGATE),
    SIDECHAIN_REDELEGATE(com.binance.dex.api.client.domain.broadcast.TxType.SIDECHAIN_REDELEGATE),
    SIDECHAIN_UNDELEGATE(com.binance.dex.api.client.domain.broadcast.TxType.SIDECHAIN_UNBOND),
    ORACLE_CLAIM(com.binance.dex.api.client.domain.broadcast.TxType.CLAIM),
    CROSS_TRANSFER_OUT(com.binance.dex.api.client.domain.broadcast.TxType.TRANSFER_OUT),
    CROSS_BIND(com.binance.dex.api.client.domain.broadcast.TxType.BIND),
    CROSS_UNBIND(com.binance.dex.api.client.domain.broadcast.TxType.UNBIND),
    BSC_SUBMIT_EVIDENCE(com.binance.dex.api.client.domain.broadcast.TxType.BSC_SUBMIT_EVIDENCE),
    SIDECHAIN_UNJAIL(com.binance.dex.api.client.domain.broadcast.TxType.SIDECHAIN_UNJAIL),
    TRANSFER_TOKEN_OWNERSHIP(com.binance.dex.api.client.domain.broadcast.TxType.TRANSFER_TOKEN_OWNERSHIP),
    TINY_TOKEN_ISSUE(com.binance.dex.api.client.domain.broadcast.TxType.TINY_TOKEN_ISSUE),
    MINI_TOKEN_ISSUE(com.binance.dex.api.client.domain.broadcast.TxType.MINI_TOKEN_ISSUE),
    MINI_TOKEN_LIST(com.binance.dex.api.client.domain.broadcast.TxType.MINI_TOKEN_LIST),
    MINI_TOKEN_SET_URI(com.binance.dex.api.client.domain.broadcast.TxType.MINI_TOKEN_SET_URI);

    private com.binance.dex.api.client.domain.broadcast.TxType code;

    TxType(com.binance.dex.api.client.domain.broadcast.TxType code) {
        this.code = code;
    }

    private static final Map<String, com.binance.dex.api.client.domain.broadcast.TxType> nameTypeMap = new HashMap<>();

    static {
        for (TxType txType : TxType.values()) {
            nameTypeMap.put(txType.name().toUpperCase(), txType.code);
        }
    }

    public static com.binance.dex.api.client.domain.broadcast.TxType getTypeByName(String name) {
        if (name == null) {
            return null;
        }
        return nameTypeMap.get(name.toUpperCase());
    }
}
