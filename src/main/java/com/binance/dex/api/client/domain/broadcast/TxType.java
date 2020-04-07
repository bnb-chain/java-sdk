package com.binance.dex.api.client.domain.broadcast;

public enum TxType {
    NEW_ORDER,
    CANCEL_ORDER,
    FREEZE_TOKEN,
    UNFREEZE_TOKEN,
    TRANSFER,
    VOTE,
    ISSUE,
    BURN,
    MINT,
    SUBMIT_PROPOSAL,
    DEPOSIT,
    CREATE_VALIDATOR,
    REMOVE_VALIDATOR,
    LISTING,
    TimeLock,
    TimeUnlock,
    TimeRelock,
    SetAccountFlag,
    HTL_TRANSFER,
    CLAIM_HTL,
    REFUND_HTL,
    DEPOSIT_HTL,
    MINI_TOKEN_ISSUE,
    MINI_TOKEN_SET_URI,
    MINI_TOKEN_LIST
}
