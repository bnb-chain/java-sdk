package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.DexFeeField;
import com.binance.dex.api.client.domain.Fees;
import com.binance.dex.api.client.encoding.message.FeeType;
import com.binance.dex.api.proto.DexFeeParam;
import com.binance.dex.api.proto.FixedFeeParams;
import com.binance.dex.api.proto.TransferFeeParam;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.stream.Collectors;

/**
 * Fee converter
 * Created by fletcher on 2019/5/14.
 */
public class FeeConverter {

    public Fees convert(byte[] bytes){

        try {
            FeeType feeType = FeeType.getFeeType(bytes);
            if(feeType == null){
                return null;
            }
            switch (feeType){
                case DexFeeParam:
                    return convertDexFeeParam(bytes);
                case FixedFeeParams:
                    return convertFixedFeeParams(bytes);
                case TransferFeeParam:
                    return convertTransferFeeParam(bytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Fees convertTransferFeeParam(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        TransferFeeParam transferFeeParam = TransferFeeParam.parseFrom(array);
        Fees fees = new Fees();
        fees.setFeeType(FeeType.TransferFeeParam);
        fees.setFixedFeeParams(convert(transferFeeParam.getFixedFeeParams()));
        fees.setMultiTransferFee(String.valueOf(transferFeeParam.getMultiTransferFee()));
        fees.setLowerLimitAsMulti(String.valueOf(transferFeeParam.getLowerLimitAsMulti()));
        return fees;
    }

    private com.binance.dex.api.client.domain.FixedFeeParams convert(FixedFeeParams fixedFeeParams) {
        com.binance.dex.api.client.domain.FixedFeeParams feeParams = new com.binance.dex.api.client.domain.FixedFeeParams();
        feeParams.setFee(fixedFeeParams.getFee());
        feeParams.setFeeFor(fixedFeeParams.getFeeFor());
        feeParams.setMsgType(fixedFeeParams.getMsgType());
        return feeParams;
    }

    private Fees convertFixedFeeParams(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        FixedFeeParams fixedFeeParams = FixedFeeParams.parseFrom(array);
        Fees fees = new Fees();
        fees.setFeeType(FeeType.FixedFeeParams);
        fees.setMsgType(fixedFeeParams.getMsgType());
        fees.setFeeFor(fixedFeeParams.getFeeFor());
        fees.setFee(fixedFeeParams.getFee());
        return fees;
    }

    private Fees convertDexFeeParam(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        DexFeeParam dexFeeParam = DexFeeParam.parseFrom(array);
        Fees fees = new Fees();
        fees.setFeeType(FeeType.DexFeeParam);
        fees.setDexFeeFields(dexFeeParam.getDexFeeFieldsList().stream().map(dexFeeField -> {
            DexFeeField feeField = new DexFeeField();
            feeField.setFeeName(dexFeeField.getFeeName());
            feeField.setFeeValue(dexFeeField.getFeeValue());
            return feeField;
        }).collect(Collectors.toList()));
        return fees;

    }

    private int getStartIndex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x80) {
                return i + 5;
            }
        }
        return -1;
    }

}
