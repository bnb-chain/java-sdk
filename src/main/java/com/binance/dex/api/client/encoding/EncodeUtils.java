package com.binance.dex.api.client.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.protobuf.CodedOutputStream;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class EncodeUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static byte[] hexStringToByteArray(String s) {
        return Hex.decode(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    public static String toJsonStringSortKeys(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T toObjectFromJsonString(String jsonString,Class<T> tClass) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString,tClass);
    }

    public static byte[] toJsonEncodeBytes(Object object) throws JsonProcessingException {
        return toJsonStringSortKeys(object).getBytes(Charsets.UTF_8);
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

    public static byte[] hashBySHA256(byte[] originData){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(originData);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] long2Bytes(long x){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(x);
        return buffer.array();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}
