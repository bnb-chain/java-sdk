package com.binance.dex.api.client.encoding.amino;

import com.binance.dex.api.client.encoding.amino.types.PubKeyEd25519;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.*;
import org.bouncycastle.util.encoders.Hex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fitz.Lu
 * @see {https://developers.google.com/protocol-buffers/docs/encoding#structure}
 **/
public class WireType {

    private static final HashMap<Class<?>, String> aminoType;
    private static final HashMap<String, Class<?>> typePrefixMap;

    static {
        aminoType = new HashMap<>();
        aminoType.put(PubKeyEd25519.class, "tendermint/PubKeyEd25519");
        aminoType.put(CreateSideChainValidatorMessage.class, "cosmos-sdk/MsgCreateSideChainValidator");
        aminoType.put(EditSideChainValidatorMessage.class, "cosmos-sdk/MsgEditSideChainValidator");
        aminoType.put(SideChainDelegateMessage.class, "cosmos-sdk/MsgSideChainDelegate");
        aminoType.put(SideChainRedelegateMessage.class, "cosmos-sdk/MsgSideChainRedelegate");
        aminoType.put(SideChainUndelegateMessage.class, "cosmos-sdk/MsgSideChainUndelegate");

        typePrefixMap = new HashMap<>();
        Amino amino = new Amino();
        for (Map.Entry<Class<?>, String> entry : aminoType.entrySet()) {
            typePrefixMap.put(amino.nameToPrefixString(entry.getValue()), entry.getKey());
        }
    }

    public static int VARINT = 0;   //int32, int64, uint32, uint64, sint32, sint64, bool, enum

    public static int BIT64 = 1;    //fixed64, sfixed64, double

    public static int LENGTH_DELIMITED = 2; //string, bytes, embedded messages, packed repeated fields

    public static int BIT_32 = 5;   //	fixed32, sfixed32, float

    public static boolean isRegistered(Class<?> clazz){
        return aminoType.containsKey(clazz);
    }

    public static boolean isRegistered(byte[] typePrefix){
        return typePrefixMap.containsKey(Hex.toHexString(typePrefix));
    }

    public static String getRegisteredTypeName(Class<?> clazz){
        return aminoType.get(clazz);
    }

}
