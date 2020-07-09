package com.binance.dex.api.client.encoding.message.common;

import com.binance.dex.api.client.encoding.Bech32;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.amino.AminoCustomSerialized;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.binance.dex.api.client.encoding.serializer.Bech32AddressValueToStringSerializer;
import com.binance.dex.api.client.rlp.RlpDecodable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
@JsonSerialize(using = Bech32AddressValueToStringSerializer.class)
public class Bech32AddressValue implements AminoCustomSerialized, RlpDecodable {

    private String hrp;

    private byte[] raw;

    public static Bech32AddressValue fromBech32String(String address){
        return new Bech32AddressValue(address);
    }

    public static Bech32AddressValue fromBech32StringWithNewHrp(String originalAddress, String hrp){
        return new Bech32AddressValue(originalAddress, hrp);
    }

    public Bech32AddressValue() { }

    public Bech32AddressValue(String address) {
        this.hrp = Bech32.decode(address).getHrp();
        this.raw = Crypto.decodeAddress(address);
    }

    public Bech32AddressValue(String originalAddress, String hrp){
        this.hrp = hrp;
        this.raw = Crypto.decodeAddress(originalAddress);
    }

    public String getHrp() {
        return hrp;
    }

    public void setHrp(String hrp) {
        this.hrp = hrp;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    public byte[] getRaw(){
        return raw;
    }

    @Override
    public AminoCustomSerialized newAminoSerInstance() {
        return new Bech32AddressValue();
    }

    @Override
    public boolean isDefaultOrEmpty() {
        return ByteUtil.isEmpty(raw);
    }

    @Override
    public int getWireType() {
        return WireType.LENGTH_DELIMITED;
    }

    @Override
    public void encode(CodedOutputStream outputStream) throws IOException {
        if (raw != null) {
            outputStream.writeByteArrayNoTag(raw);
        }
    }

    @Override
    public void decode(CodedInputStream inputStream) throws IOException {
        raw = inputStream.readByteArray();
    }

    @Override
    public int getSerializedSize() {
        if (ByteUtil.isEmpty(raw)) {
            return 0;
        }else{
            return CodedOutputStream.computeByteArraySizeNoTag(raw);
        }
    }

    @Override
    public void decode(byte[] raw, Object superInstance) {
        this.raw = raw;
    }
}
