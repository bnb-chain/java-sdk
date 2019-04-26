package com.binance.dex.api.client.ledger.utils;

import com.google.common.primitives.Bytes;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

public class Utils {
    public static String dump(byte[] buffer, int offset, int length) {
        String result = "";
        for (int i=0; i<length; i++) {
            String temp = Integer.toHexString((buffer[offset + i]) & 0xff);
            if (temp.length() < 2) {
                temp = "0" + temp;
            }
            result += temp;
        }
        return result;
    }

    public static String dump(byte[] buffer) {
        return dump(buffer, 0, buffer.length);
    }

    public static byte[] hexToBin(String src) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int i = 0;
        while (i < src.length()) {
            char x = src.charAt(i);
            if (!((x >= '0' && x <= '9') || (x >= 'A' && x <= 'F') || (x >= 'a' && x <= 'f'))) {
                i++;
                continue;
            }
            try {
                result.write(Integer.valueOf("" + src.charAt(i) + src.charAt(i + 1), 16));
                i += 2;
            }
            catch (Exception e) {
                return null;
            }
        }
        return result.toByteArray();
    }
    public static byte[] getBip32bytes(int[] bip32Path, int hardenCount) {
        byte[] message = new byte[41];
        if (bip32Path.length > 10) {
            return null;
        }
        message[0] = (byte)bip32Path.length;
        for (int index=0; index < bip32Path.length; index++) {
            int pos = 1 + index*4;
            int value = bip32Path[index];
            if (index < hardenCount) {
                value = 0x80000000 | bip32Path[index];
            }
            for (int i=0; i < 4; i++){
                message[pos+i]=(byte) (value & 0xFF);
                value = value >> 8;
            }
        }
        return message;
    }

    public static byte[] compressedLedgerPubkey(byte[] pubkey) {
        if (pubkey.length != 65) {
            return null;
        }
        byte[] result = new byte[1];
        BigInteger bigX =  new BigInteger(Arrays.copyOfRange(pubkey, 1, 33));
        BigInteger bigY =  new BigInteger(Arrays.copyOfRange(pubkey, 33, 65));

        result[0]=(byte)0x2;
        if (bigY.toByteArray()[0] % 2 != 0) {
            result[0] = (byte)(result[0] | 0x1);
        }
        return Bytes.concat(result, bigX.toByteArray());
    }
}
