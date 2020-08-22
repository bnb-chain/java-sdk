package com.binance.dex.api.client.encoding.amino;

import java.util.ArrayList;

/**
 * @author Fitz.Lu
 **/
public interface AminoSerializable {

    AminoSerializable newAminoMessage();

    ArrayList<AminoField<?>> IterateFields();

    void setValueByFieldIndex(int fieldIndex, Object value);

}
