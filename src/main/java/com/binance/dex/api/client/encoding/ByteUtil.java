package com.binance.dex.api.client.encoding;

/**
 * @author Fitz.Lu
 **/
//TODO add safe check
public class ByteUtil {

    public static boolean isEmpty(byte[] bytes){
        return bytes == null || bytes.length == 0;
    }

    public static byte[] read(byte[] bytes, int length){
        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, 0, newBytes, 0, length);
        return newBytes;
    }

    public static byte[] cut(byte[] bytes, int length){
        byte[] newBytes = new byte[bytes.length - length];
        System.arraycopy(bytes, length, newBytes, 0, newBytes.length);
        return newBytes;
    }

    public static byte[] pick(byte[] bytes, int start, int length){
        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, start, newBytes, 0, length);
        return newBytes;
    }

    public static byte[] appendBytesArray(byte[] first, byte[] second){
        byte[] newArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }

}
