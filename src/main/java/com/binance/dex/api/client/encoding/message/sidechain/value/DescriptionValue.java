package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class DescriptionValue implements BinanceDexTransactionMessage, AminoSerializable {

    @JsonProperty(value = "moniker")
    private String moniker = "";

    @JsonProperty(value = "identity")
    private String identity = "";

    @JsonProperty(value = "website")
    private String website = "";

    @JsonProperty(value = "details")
    private String details = "";

    public DescriptionValue() {
    }

    public DescriptionValue(String moniker, String identity, String website, String details) {
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

    @Override
    public AminoSerializable newAminoMessage() {
        return new DescriptionValue();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                moniker = ((String) value);
                break;
            case 2:
                identity = ((String) value);
                break;
            case 3:
                website = ((String) value);
                break;
            case 4:
                details = ((String) value);
                break;
            default:
                break;
        }
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        ArrayList<AminoField<?>> fields = new ArrayList<>();
        fields.add(new AminoField<>(String.class, moniker, StringUtils.isEmpty(moniker)));
        fields.add(new AminoField<>(String.class, identity, StringUtils.isEmpty(identity)));
        fields.add(new AminoField<>(String.class, website, StringUtils.isEmpty(website)));
        fields.add(new AminoField<>(String.class, details, StringUtils.isEmpty(details)));
        return fields;
    }
}
