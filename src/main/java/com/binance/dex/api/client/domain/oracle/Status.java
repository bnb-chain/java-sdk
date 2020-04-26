package com.binance.dex.api.client.domain.oracle;

/**
 * @author Fitz.Lu
 **/
public class Status {

    private int text;

    private String finalClaim;

    public Status() {
    }

    public Status(int text, String finalClaim) {
        this.text = text;
        this.finalClaim = finalClaim;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public String getFinalClaim() {
        return finalClaim;
    }

    public void setFinalClaim(String finalClaim) {
        this.finalClaim = finalClaim;
    }
}
