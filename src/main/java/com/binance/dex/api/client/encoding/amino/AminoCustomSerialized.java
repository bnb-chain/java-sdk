package com.binance.dex.api.client.encoding.amino;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public interface AminoCustomSerialized {

    AminoCustomSerialized newAminoSerInstance();

    boolean isDefaultOrEmpty();

    void encode(CodedOutputStream outputStream) throws IOException;

    int getWireType();

    void decode(CodedInputStream inputStream) throws IOException;

    int getSerializedSize();

}
