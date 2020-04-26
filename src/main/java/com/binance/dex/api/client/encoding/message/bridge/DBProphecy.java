package com.binance.dex.api.client.encoding.message.bridge;

import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.amino.AminoField;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public class DBProphecy implements AminoSerializable {

    private String id;

    private StatusValue status;

    private byte[] validatorClaims;

    public DBProphecy() {
    }

    public DBProphecy(String id, StatusValue status, byte[] validatorClaims) {
        this.id = id;
        this.status = status;
        this.validatorClaims = validatorClaims;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusValue getStatus() {
        return status;
    }

    public void setStatus(StatusValue status) {
        this.status = status;
    }

    public byte[] getValidatorClaims() {
        return validatorClaims;
    }

    public void setValidatorClaims(byte[] validatorClaims) {
        this.validatorClaims = validatorClaims;
    }

    @Override
    public AminoSerializable newAminoMessage() {
        return new DBProphecy();
    }

    @Override
    public ArrayList<AminoField<?>> IterateFields() {
        return AminoField.newFieldsBuilder()
                .addField(String.class, id, StringUtils.isEmpty(id))
                .addField(StatusValue.class, status, status == null)
                .addField(byte[].class, validatorClaims, ByteUtil.isEmpty(validatorClaims))
                .build();
    }

    @Override
    public void setValueByFieldIndex(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 1:
                id = ((String) value);
                break;
            case 2:
                status = ((StatusValue) value);
                break;
            case 3:
                validatorClaims = ((byte[]) value);
                break;
            default:
                break;
        }
    }
}
