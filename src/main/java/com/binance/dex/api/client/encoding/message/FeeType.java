package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.encoding.EncodeUtils;

import java.util.Arrays;

/**
 * Created by fletcher on 2019/5/14.
 */
public enum FeeType {

    DexFeeParam("495A5044"),
    FixedFeeParams("C2A96FA3"),
    TransferFeeParam("9A3D2769");

    private byte[] typePrefixBytes;

    FeeType(String typePrefix) {
        if (typePrefix == null) {
            this.typePrefixBytes = new byte[0];
        } else
            this.typePrefixBytes = EncodeUtils.hexStringToByteArray(typePrefix);
    }

    public byte[] getTypePrefixBytes() {
        return typePrefixBytes;
    }

    public static FeeType getFeeType(byte[] bytes) {
        if (null == bytes || bytes.length < 4) {
            return null;
        }
        return Arrays.stream(FeeType.values())
                .filter(type -> {
                    if (null == type.getTypePrefixBytes() || type.getTypePrefixBytes().length < 4) {
                        return false;
                    }
                    for (int i = 0; i < 4; i++) {
                        if (type.getTypePrefixBytes()[i] != bytes[i]) {
                            return false;
                        }
                    }
                    return true;
                })
                .findAny().orElse(null);
    }

}
