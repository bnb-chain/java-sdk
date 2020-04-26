package com.binance.dex.api.client.encoding.message.common;

import com.binance.dex.api.client.encoding.amino.AminoCustomSerialized;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class EthAddressValue implements AminoCustomSerialized {

    private String address;

    public static EthAddressValue from(String addr){
        return new EthAddressValue(addr);
    }

    public EthAddressValue() {
    }

    public EthAddressValue(String address) {
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
        return new EthAddressValue();
    }

    @Override
    public boolean isDefaultOrEmpty() {
        return StringUtils.isEmpty(address);
    }

    @Override
    public void encode(CodedOutputStream outputStream) throws IOException {
        outputStream.writeByteArrayNoTag(getValueBytes());
    }

    @Override
    public int getWireType() {
        return WireType.LENGTH_DELIMITED;
    }

    @Override
    public void decode(CodedInputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readByteArray();
        address = Hex.toHexString(bytes);
        address = "0x" + address;
    }

    @Override
    public int getSerializedSize() {
        if (StringUtils.isEmpty(address)){
            return 0;
        }else{
            return CodedOutputStream.computeByteArraySizeNoTag(getValueBytes());
        }
    }

    private byte[] getValueBytes(){
        String addr = address;
        if (addr.startsWith("0x")){
            addr = address.substring(2);
        }
        return Hex.decode(addr);
    }
}
