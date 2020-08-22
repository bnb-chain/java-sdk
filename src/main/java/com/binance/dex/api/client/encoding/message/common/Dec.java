package com.binance.dex.api.client.encoding.message.common;

import com.binance.dex.api.client.encoding.amino.AminoCustomSerialized;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
@Deprecated
public class Dec implements AminoCustomSerialized {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public static Dec newInstance(long v){
        Dec dec = new Dec();
        dec.setValue(v);
        return dec;
    }

    @Override
    public AminoCustomSerialized newAminoSerInstance() {
        return new Dec();
    }

    @Override
    public boolean isDefaultOrEmpty() {
        return value == 0L;
    }

    @Override
    public int getWireType() {
        return WireType.LENGTH_DELIMITED;
    }

    @Override
    public void encode(CodedOutputStream outputStream) throws IOException  {
        outputStream.writeInt64NoTag(value);
    }

    @Override
    public void decode(CodedInputStream inputStream) throws IOException {
        value = inputStream.readInt64();
    }

    @Override
    public int getSerializedSize() {
        return CodedOutputStream.computeInt64SizeNoTag(value);
    }
}
