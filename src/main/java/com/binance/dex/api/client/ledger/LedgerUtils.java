package com.binance.dex.api.client.ledger;

import com.google.common.primitives.Bytes;

import java.math.BigInteger;
import java.util.Arrays;

public class LedgerUtils {
    private static final int bipPurpose = 44;
    private static final int bipCoinType = 714;
    private static final int bipChange = 0;

    public static byte[] bipPathToBytes(int[] bip32Path, int hardenCount) {
        byte[] result = new byte[41];
        if (!verifyBIP44Path(bip32Path)) {
            return null;
        }
        result[0] = (byte) bip32Path.length;
        for (int index = 0; index < bip32Path.length; index++) {
            int pos = 1 + index * 4;
            int value = bip32Path[index];
            if (index < hardenCount) {
                value = 0x80000000 | bip32Path[index];
            }
            for (int i = 0; i < 4; i++) {
                result[pos + i] = (byte) (value & 0xFF);
                value = value >> 8;
            }
        }
        return result;
    }

    public static byte[] compressedLedgerPubkey(byte[] pubkey) {
        if (pubkey.length != 65) {
            return null;
        }
        byte[] result = new byte[1];
        BigInteger bigX = new BigInteger(Arrays.copyOfRange(pubkey, 1, 33));
        BigInteger bigY = new BigInteger(Arrays.copyOfRange(pubkey, 33, 65));

        result[0] = (byte) 0x2;
        if (bigY.toByteArray()[0] % 2 != 0) {
            result[0] = (byte) (result[0] | 0x1);
        }
        return Bytes.concat(result, bigX.toByteArray());
    }

    public static int[] createBIP44Path(int account, int index) {
        if (account < 0 || index < 0) {
            return null;
        }
        return new int[]{bipPurpose, bipCoinType, account, bipChange, index};
    }

    public static boolean verifyBIP44Path(int[] bip44Path) {
        if (bip44Path.length != 5) {
            return false;
        }
        if (bip44Path[0] != bipPurpose || bip44Path[1] != bipCoinType || bip44Path[3] != bipChange) {
            return false;
        }
        if (bip44Path[2] < 0 || bip44Path[4] < 0) {
            return false;
        }
        return true;
    }
}
