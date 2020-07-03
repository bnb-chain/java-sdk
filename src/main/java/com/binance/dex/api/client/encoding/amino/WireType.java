package com.binance.dex.api.client.encoding.amino;

import com.binance.dex.api.client.encoding.amino.types.PubKeyEd25519;
import com.binance.dex.api.client.encoding.message.bridge.BindMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.ClaimMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.TransferOutMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.UnbindMsgMessage;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.*;
import org.bouncycastle.util.encoders.Hex;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * @author Fitz.Lu
 * @see {https://developers.google.com/protocol-buffers/docs/encoding#structure}
 **/
public class WireType {

    private static final HashMap<Class<?>, String> classToTypeName = new HashMap<>();

    static {
        classToTypeName.put(PubKeyEd25519.class, "tendermint/PubKeyEd25519");

        classToTypeName.put(CreateSideChainValidatorMessage.class, "cosmos-sdk/MsgCreateSideChainValidator");
        classToTypeName.put(EditSideChainValidatorMessage.class, "cosmos-sdk/MsgEditSideChainValidator");
        classToTypeName.put(SideChainDelegateMessage.class, "cosmos-sdk/MsgSideChainDelegate");
        classToTypeName.put(SideChainRedelegateMessage.class, "cosmos-sdk/MsgSideChainRedelegate");
        classToTypeName.put(SideChainUndelegateMessage.class, "cosmos-sdk/MsgSideChainUndelegate");

        classToTypeName.put(TransferOutMsgMessage.class, "bridge/TransferOutMsg");
        classToTypeName.put(BindMsgMessage.class, "bridge/BindMsg");
        classToTypeName.put(UnbindMsgMessage.class, "bridge/UnbindMsg");
        classToTypeName.put(ClaimMsgMessage.class, "oracle/ClaimMsg");
    }

    public static int VARINT = 0;   //int32, int64, uint32, uint64, sint32, sint64, bool, enum

    public static int BIT64 = 1;    //fixed64, sfixed64, double

    public static int LENGTH_DELIMITED = 2; //string, bytes, embedded messages, packed repeated fields

    public static int BIT_32 = 5;   //	fixed32, sfixed32, float


    public static boolean isRegistered(Class<?> clazz){
        return classToTypeName.containsKey(clazz);
    }

    public static boolean isRegistered(byte[] typePrefix){
        String typeHex = Hex.toHexString(typePrefix);
        for (String value : classToTypeName.values()) {
            if (typeHex.equals(value)){
                return true;
            }
        }

        return false;
    }

    public static String getRegisteredTypeName(Class<?> clazz) {
        return classToTypeName.get(clazz);
    }

    public static byte[] getTypePrefix(Class<?> clazz) throws IllegalStateException, NoSuchAlgorithmException {
        if (!isRegistered(clazz)){
            throw new IllegalStateException("class " + clazz.getCanonicalName() + " has not been registered into amino");
        }
        return InternalAmino.get().nameToPrefix(getRegisteredTypeName(clazz));
    }

}
