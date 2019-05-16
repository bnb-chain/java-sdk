package com.binance.dex.api.client.domain.broadcast;

public class Transaction {
    private Long height;
    private String hash;
    private Integer code;
    private String log;
    private String memo;
    private TxType txType;
    private Object realTx;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public TxType getTxType() {
        return txType;
    }

    public void setTxType(TxType txType) {
        this.txType = txType;
    }

    public Object getRealTx() {
        return realTx;
    }

    public void setRealTx(Object realTx) {
        this.realTx = realTx;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
