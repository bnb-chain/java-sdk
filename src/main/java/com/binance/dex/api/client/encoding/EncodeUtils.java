package com.binance.dex.api.client.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.CodedOutputStream;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;

public class EncodeUtils {
    private static final ObjectWriter OBJECT_WRITER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        OBJECT_WRITER = mapper.writer();
    }

    // DatatypeConverter will not be included in java SDK 9
    public static byte[] hexStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
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
}
