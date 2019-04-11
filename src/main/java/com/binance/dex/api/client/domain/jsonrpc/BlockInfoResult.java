package com.binance.dex.api.client.domain.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockInfoResult {

    private Long total_count;
    private List<Transaction> txs;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Transaction {
        private String hash;
        private Long height;
        private Long index;
        private TxResult tx_result;
        private byte[] tx;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public Long getIndex() {
            return index;
        }

        public void setIndex(Long index) {
            this.index = index;
        }

        public TxResult getTx_result() {
            return tx_result;
        }

        public void setTx_result(TxResult tx_result) {
            this.tx_result = tx_result;
        }

        public byte[] getTx() {
            return tx;
        }

        public void setTx(byte[] tx) {
            this.tx = tx;
        }
    }

    public Long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Long total_count) {
        this.total_count = total_count;
    }

    public List<Transaction> getTxs() {
        return txs;
    }

    public void setTxs(List<Transaction> txs) {
        this.txs = txs;
    }
}
