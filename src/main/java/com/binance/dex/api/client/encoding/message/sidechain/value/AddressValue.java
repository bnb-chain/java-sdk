package com.binance.dex.api.client.encoding.message.sidechain.value;

import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.amino.AminoCustomSerialized;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class AddressValue implements AminoCustomSerialized {

    private String address;

    public static AddressValue from(String value){
        return new AddressValue(value);
    }

    public AddressValue() {
    }

    public AddressValue(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public AminoCustomSerialized newAminoSerInstance() {
        return new AddressValue();
    }

    @Override
    public boolean isDefaultOrEmpty() {
        return StringUtils.isEmpty(address);
    }

    @Override
    public int getWireType() {
        return WireType.LENGTH_DELIMITED;
    }

    @Override
    public void encode(CodedOutputStream outputStream) throws IOException {
        outputStream.writeByteArrayNoTag(getValueBytes());
    }

    @Override
    public void decode(CodedInputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readByteArray();
//        address = Bech32.encode("bnb", bytes);
    }

    @Override
    public int getSerializedSize() {
        if (StringUtils.isEmpty(address)) {
            return 0;
        }else{
            return CodedOutputStream.computeByteArraySizeNoTag(getValueBytes());
        }
    }

    private byte[] getValueBytes(){
        return Crypto.decodeAddress(address);
    }

}
