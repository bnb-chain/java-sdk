package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.domain.jsonrpc.TxResult;

import java.util.List;

public class Transaction {
    private Long height;
    private String hash;
    private Integer code;
    private String log;
    private String memo;
    private TxType txType;
    private Object realTx;
    private List<TxResult.Tag> tags;
    private List<TxResult.Event> events;
    private byte[] resultData;
    private long source;
    private long sequence;

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

    public List<TxResult.Tag> getTags() {
        return tags;
    }

    public void setTags(List<TxResult.Tag> tags) {
        this.tags = tags;
    }

    public byte[] getResultData() {
        return resultData;
    }

    public void setResultData(byte[] resultData) {
        this.resultData = resultData;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public List<TxResult.Event> getEvents() {
        return events;
    }

    public void setEvents(List<TxResult.Event> events) {
        this.events = events;
    }
}
