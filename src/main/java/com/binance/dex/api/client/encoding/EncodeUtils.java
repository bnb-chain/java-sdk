package com.binance.dex.api.client.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.CodedOutputStream;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.charset.Charset;

public class EncodeUtils {
    private static final ObjectWriter OBJECT_WRITER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        OBJECT_WRITER = mapper.writer();
    }

    public static byte[] hexStringToByteArray(String s) {
        return Hex.decode(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    public static String toJsonStringSortKeys(Object object) throws JsonProcessingException {
        return OBJECT_WRITER.writeValueAsString(object);
    }

    public static byte[] toJsonEncodeBytes(Object object) throws JsonProcessingException {
        return toJsonStringSortKeys(object).getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] aminoWrap(byte[] raw, byte[] typePrefix, boolean isPrefixLength) throws IOException {
        int totalLen = raw.length + typePrefix.length;
        if (isPrefixLength)
            totalLen += CodedOutputStream.computeUInt64SizeNoTag(totalLen);

        byte[] msg = new byte[totalLen];
        CodedOutputStream cos = CodedOutputStream.newInstance(msg);
        if (isPrefixLength)
            cos.writeUInt64NoTag(raw.length + typePrefix.length);
        cos.write(typePrefix, 0, typePrefix.length);
        cos.write(raw, 0, raw.length);
        cos.flush();
        return msg;
    }

    public static void main(String[] args) throws IOException {
        byte[] pubkey = new byte[]{
                (byte)73,
                (byte)178,
                (byte)136,
                (byte)228,
                (byte)235,
                (byte)187,
                (byte)58,
                (byte)40,
                (byte)28,
                (byte)45,
                (byte)84,
                (byte)111,
                (byte)195,
                (byte)2,
                (byte)83,
                (byte)213,
                (byte)186,
                (byte)240,
                (byte)137,
                (byte)147,
                (byte)182,
                (byte)229,
                (byte)210,
                (byte)149,
                (byte)251,
                (byte)120,
                (byte)122,
                (byte)91,
                (byte)49,
                (byte)74,
                (byte)41,
                (byte)142
        };
        byte[] prefix = "tendermint/PubKeyEd25519".getBytes();
        System.out.println(prefix.length);
        byte[] afterAmino = aminoWrap(pubkey,prefix,true);
        String result = Bech32.encode("bcap",afterAmino);
        System.out.println(result);

    }
}
