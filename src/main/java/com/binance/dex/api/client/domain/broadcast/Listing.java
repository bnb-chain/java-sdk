package com.binance.dex.api.client.domain.broadcast;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class Listing {

    private Long proposalId;
    private String baseAssetSymbol;
    private String quoteAssetSymbol;
    private Long initPrice;
    private String fromAddr;

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getBaseAssetSymbol() {
        return baseAssetSymbol;
    }

    public void setBaseAssetSymbol(String baseAssetSymbol) {
        this.baseAssetSymbol = baseAssetSymbol;
    }

    public String getQuoteAssetSymbol() {
        return quoteAssetSymbol;
    }

    public void setQuoteAssetSymbol(String quoteAssetSymbol) {
        this.quoteAssetSymbol = quoteAssetSymbol;
    }

    public Long getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Long initPrice) {
        this.initPrice = initPrice;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }
}
