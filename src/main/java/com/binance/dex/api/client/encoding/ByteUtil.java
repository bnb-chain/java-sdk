package com.binance.dex.api.client.encoding;

import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class ByteUtil {

    public static boolean isEmpty(byte[] bytes){
        return bytes == null || bytes.length == 0;
    }

    public static byte[] read(byte[] bytes, int length){
        requireByteArrayNotEmpty(bytes);

        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, 0, newBytes, 0, length);
        return newBytes;
    }

    public static byte[] cut(byte[] bytes, int length){
        requireByteArrayNotEmpty(bytes);
        requireByteArrayLengthNotLessThan(bytes, length);

        byte[] newBytes = new byte[bytes.length - length];
        System.arraycopy(bytes, length, newBytes, 0, newBytes.length);
        return newBytes;
    }

    public static byte[] pick(byte[] bytes, int start, int length){
        requireByteArrayNotEmpty(bytes);
        requireByteArrayLengthNotLessThan(bytes, length);

        byte[] newBytes = new byte[length];
        System.arraycopy(bytes, start, newBytes, 0, length);
        return newBytes;
    }

    public static byte[] trim(byte[] bytes, byte b){
        requireByteArrayNotEmpty(bytes);
        byte[] newBytes = new byte[0];
        for(byte bb : bytes){
            if (bb != b){
                newBytes = appendByte(newBytes,bb);
            }
        }
        return newBytes;
    }

    public static byte[] appendByte(byte[] bytes, byte b) {
        byte[] result = Arrays.copyOf(bytes, bytes.length + 1);
        result[result.length - 1] = b;
        return result;
    }

    public static byte[] appendBytesArray(byte[] first, byte[] second){
        if (first == null){
            throw new NullPointerException("first byte array is null");
        }
        if (second == null){
            throw new NullPointerException("second byte array is null");
        }

        byte[] newArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }

    public static void requireByteArrayNotEmpty(byte[] bytes){
        if (isEmpty(bytes)){
            throw new IllegalArgumentException("Input byte array is null or empty");
        }
    }

    public static void requireByteArrayLengthNotLessThan(byte[] bytes, int length){
        if (bytes.length < length){
            throw new IllegalArgumentException("Input byte array length is less than " + length);
        }
    }

}
