package com.binance.dex.api.client.domain.stake;

/**
 * @author Fitz.Lu
 **/
public class Description {

    private String moniker;

    private String identity;

    private String website;

    private String details;

    public Description() {
        this.moniker = "";
        this.identity = "";
        this.website = "";
        this.details = "";
    }

    public Description(String moniker, String identity, String website, String details) {
        this.moniker = moniker;
        this.identity = identity;
        this.website = website;
        this.details = details;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Description{" +
                "moniker='" + moniker + '\'' +
                ", identity='" + identity + '\'' +
                ", website='" + website + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
