package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.jsonrpc.ABCIQueryResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.domain.sidechain.*;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.binance.dex.api.client.encoding.message.sidechain.query.BechValidator;
import com.binance.dex.api.client.encoding.message.sidechain.query.QueryTopValidatorParams;
import com.binance.dex.api.client.encoding.message.sidechain.query.SideChainValidatorMessage;
import com.binance.dex.api.client.encoding.message.sidechain.value.DelegationValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.RedelegationValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.UnBondingValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.util.encoders.Hex;
import types.Types;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
class BinanceDexApiNodeClientImplSideChainQueryDelegate {

    private final String stakeStoreName = "stake";
    private final String scStoreKey = "sc";

    private static final int addressLength            = 20;

    private final byte[] sideChainStorePrefixByIdKey  = new byte[]{0x01};
    private final byte[] validatorsKey                = new byte[]{0x21};
    private final byte[] delegationKey                = new byte[]{0x31};
    private final byte[] redelegationKey              = new byte[]{0x34};
    private final byte[] unBondingDelegationKey       = new byte[]{0x32};

    private final Charset defaultCharset              = Charsets.UTF_8;

    private final BinanceDexNodeApi binanceDexNodeApi;

    private final Amino amino;

    BinanceDexApiNodeClientImplSideChainQueryDelegate(BinanceDexNodeApi binanceDexNodeApi) {
        this.binanceDexNodeApi = binanceDexNodeApi;
        this.amino = new Amino();
    }

    SideChainValidator querySideChainValidator(String sideChainId, byte[] validatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] keyPrefix = queryStore(stakeStoreName, storePrefix);
        if (keyPrefix == null) {
            keyPrefix = new byte[0];
        }
        byte[] key = ByteUtil.appendBytesArray(keyPrefix, getValidatorKey(validatorAddress));
        byte[] result = queryStore(stakeStoreName, key);

        if (result != null) {
            SideChainValidatorMessage message = new SideChainValidatorMessage();
            amino.decodeWithLengthPrefix(result, message);
            return convert(message);
        }else{
            return null;
        }
    }

    List<SideChainValidator> querySideChainTopValidators(String sideChainId, int top) throws IOException {
        if (top > 50 || top < 1){
            throw new IllegalArgumentException("top must be between 1 and 50");
        }

        QueryTopValidatorParams params = new QueryTopValidatorParams();
        params.setSideChainId(sideChainId);
        params.setTop(top);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/topValidators\"", data);

        String jsonStr = new String(result);

        List<BechValidator> bechValidator = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<List<BechValidator>>(){});

        List<SideChainValidator> sideChainValidators = new ArrayList<>();

        if (bechValidator != null && !bechValidator.isEmpty()){
            for (BechValidator validator : bechValidator) {
                sideChainValidators.add(validator.toSideChainValidator());
            }
        }

        return sideChainValidators;
    }

    private SideChainValidator convert(SideChainValidatorMessage message){
        SideChainValidator sideChainValidator = new SideChainValidator();

        sideChainValidator.setFeeAddr(message.getFeeAddr());
        sideChainValidator.setOperatorAddr(message.getOperatorAddr());

        //TODO consPubKey use amino decode with type prefix
        boolean fallBack = true;
        byte[] typePrefix = ByteUtil.pick(message.getConsPubKey(), 0, 4);
        if (WireType.isRegistered(typePrefix)){
            byte length = ByteUtil.pick(message.getConsPubKey(), 4, 1)[0];
            int len = Byte.toUnsignedInt(length);
            if (message.getConsPubKey().length - 5 == len){
                sideChainValidator.setConsPubKey(ByteUtil.cut(message.getConsPubKey(), 5));
                fallBack = false;
            }
        }
        if (fallBack) {
            sideChainValidator.setConsPubKey(message.getConsPubKey());
        }

        sideChainValidator.setJailed(message.isJailed());
        sideChainValidator.setStatus(message.getStatus());
        sideChainValidator.setTokens(message.getTokens().getValue());
        sideChainValidator.setDelegatorShares(message.getDelegatorShares().getValue());

        Description description = new Description();
        if (message.getDescription() != null){
            description.setMoniker(message.getDescription().getMoniker());
            description.setWebsite(message.getDescription().getWebsite());
            description.setDetails(message.getDescription().getDetails());
            description.setIdentity(message.getDescription().getIdentity());
        }
        sideChainValidator.setDescription(description);

        sideChainValidator.setBondHeight(message.getBondHeight());
        sideChainValidator.setBondIntraTxCounter(message.getBondIntraTxCounter());
        sideChainValidator.setUnBondingHeight(message.getUnBondingHeight());
        sideChainValidator.setUnBondingMinTime(message.getUnBondingMinTime());

        Commission commission = new Commission();
        if (message.getCommission() != null) {
            commission.setRate(message.getCommission().getRate());
            commission.setMaxRate(message.getCommission().getMaxRate());
            commission.setMaxChangeRate(message.getCommission().getMaxChangeRate());
        }
        sideChainValidator.setCommission(commission);

        sideChainValidator.setSideChainId(message.getSideChainId());
        sideChainValidator.setSideConsAddr(message.getSideConsAddr());
        sideChainValidator.setSideFeeAddr(message.getSideFeeAddr());

        return sideChainValidator;
    }

    SideChainDelegation querySideChainDelegation(String sideChainId, byte[] delegatorAddress, byte[] validatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] delegationKey = getDelegationKey(delegatorAddress, validatorAddress);

        byte[] key = ByteUtil.appendBytesArray(storePrefix, delegationKey);
        byte[] result = queryStore(stakeStoreName, key);

        if (result != null && result.length > 0){
            DelegationValue delegationValue = new DelegationValue();
            amino.decodeWithLengthPrefix(result, delegationValue);
            return convert(delegationValue, delegationKey);
        }

        return null;
    }

    List<SideChainDelegation> querySideChainDelegations(String sideChainId, byte[] delegatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, getDelegationsKey(delegatorAddress));
        List<common.Types.KVPair> kvPairs = queryStoreSubspaceKVPairs(stakeStoreName, key);

        List<SideChainDelegation> delegations = new ArrayList<>();
        for (common.Types.KVPair kvPair : kvPairs) {
            DelegationValue value = new DelegationValue();
            amino.decodeWithLengthPrefix(kvPair.getValue().toByteArray(), value);
            delegations.add(convert(value, Arrays.copyOfRange(kvPair.getKey().toByteArray(), storePrefix.length, kvPair.getKey().toByteArray().length)));
        }

        return delegations;
    }

    private SideChainDelegation convert(DelegationValue delegationValue, byte[] key){
        if (key.length - 1 != addressLength * 2){
            throw new IllegalArgumentException("unexpected address length for this (address, validator) pair");
        }
        byte[] delAddress = new byte[addressLength];
        byte[] valAddress = new byte[addressLength];

        System.arraycopy(key, 1, delAddress, 0, addressLength);
        System.arraycopy(key, addressLength, valAddress, 0, addressLength);

        SideChainDelegation sideChainDelegation = new SideChainDelegation();
        sideChainDelegation.setDelegatorAddress(delAddress);
        sideChainDelegation.setValidatorAddress(valAddress);
        sideChainDelegation.setHeight(delegationValue.getHeight());
        if (delegationValue.getShares() != null) {
            sideChainDelegation.setShares(delegationValue.getShares().getValue());
        }

        return sideChainDelegation;
    }

    SideChainRedelegation querySideChainRedelegation(String sideChainId, byte[] delegatorAddress, byte[] srcValidatorAddress, byte[] dstValidatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] redKey = getRedelegationKey(delegatorAddress, srcValidatorAddress, dstValidatorAddress);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, redKey);
        byte[] result = queryStore(stakeStoreName, key);

        if (!ByteUtil.isEmpty(result)){
            RedelegationValue message = new RedelegationValue();
            amino.decodeWithLengthPrefix(result, message);
            return convert(message, redKey);
        }

        return null;
    }

    List<SideChainRedelegation> querySideChainRedelegations(String sideChainId, byte[] delegatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] redsKey = getRedelegationsKey(delegatorAddress);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, redsKey);
        List<common.Types.KVPair> result = queryStoreSubspaceKVPairs(stakeStoreName, key);
        List<SideChainRedelegation> redelegations = new ArrayList<>();
        if (result != null && !result.isEmpty()){
            for (common.Types.KVPair kvPair : result) {
                byte[] k = ByteUtil.cut(kvPair.getKey().toByteArray(), storePrefix.length);
                RedelegationValue message = new RedelegationValue();
                amino.decodeWithLengthPrefix(kvPair.getValue().toByteArray(), message);
                redelegations.add(convert(message, k));
            }
        }

        return redelegations;
    }

    private SideChainRedelegation convert(RedelegationValue value, byte[] key){
        byte[] addresses = ByteUtil.cut(key, 1);
        if (addresses.length != addressLength * 3){
            throw new IllegalStateException("unexpected address length for (address, srcValidator, dstValidator)");
        }

        byte[] delAddr = ByteUtil.pick(addresses, 0, addressLength);
        byte[] srcValAddr = ByteUtil.pick(addresses, addressLength, addressLength);
        byte[] dstValAddr = ByteUtil.pick(addresses, addressLength * 2, addressLength);

        SideChainRedelegation redelegation = new SideChainRedelegation();
        redelegation.setDelegatorAddress(delAddr);
        redelegation.setSrcValidatorAddress(srcValAddr);
        redelegation.setDstValidatorAddress(dstValAddr);

        redelegation.setCreateHeight(value.getCreationHeight());
        if (value.getMinTime() != null) {
            redelegation.setMinTimeInMs(value.getMinTime().getTimeInMilliseconds());
        }
        if (value.getInitialBalance() != null){
            redelegation.setInitialBalance(value.getInitialBalance().toToken());
        }
        if (value.getBalance() != null){
            redelegation.setBalance(value.getBalance().toToken());
        }

        if (value.getSharesSrc() != null){
            redelegation.setSrcShares(value.getSharesSrc().getValue());
        }

        if (value.getSharesDst() != null){
            redelegation.setDstShare(value.getSharesDst().getValue());
        }

        return redelegation;
    }

    SideChainUnBondingDelegation querySideChainUnBondingDelegation(String sideChainId, byte[] delegatorAddress, byte[] validatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] ubdKey = getUnBondingDelegationKey(delegatorAddress, validatorAddress);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, ubdKey);
        byte[] result = queryStore(stakeStoreName, key);
        if (!ByteUtil.isEmpty(result)) {
            UnBondingValue unBondingValue = new UnBondingValue();
            amino.decodeWithLengthPrefix(result, unBondingValue);
            return convert(unBondingValue, ByteUtil.cut(ubdKey, unBondingDelegationKey.length));
        }else{
            return null;
        }
    }

    List<SideChainUnBondingDelegation> querySideChainUnBondingDelegations(String sideChainId, byte[] delegatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] ubdsKey = getUnBondingDelegationsKey(delegatorAddress);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, ubdsKey);
        List<common.Types.KVPair> kvPairs = queryStoreSubspaceKVPairs(stakeStoreName, key);
        List<SideChainUnBondingDelegation> unBondingDelegations = new ArrayList<>();
        if (kvPairs != null && !kvPairs.isEmpty()){
            for (common.Types.KVPair kvPair : kvPairs) {
                UnBondingValue unBondingValue = new UnBondingValue();
                amino.decodeWithLengthPrefix(kvPair.getValue().toByteArray(), unBondingValue);
                unBondingDelegations.add(convert(unBondingValue, ByteUtil.cut(kvPair.getKey().toByteArray(), storePrefix.length + unBondingDelegationKey.length)));
            }
        }

        return unBondingDelegations;
    }

    private SideChainUnBondingDelegation convert(UnBondingValue value, byte[] addresses){
        if (addresses.length != addressLength * 2){
            throw new IllegalStateException("unexpected address length for this (address, validator) pair");
        }

        SideChainUnBondingDelegation unBondingDelegation = new SideChainUnBondingDelegation();
        unBondingDelegation.setDelegatorAddress(ByteUtil.pick(addresses, 0, addressLength));
        unBondingDelegation.setValidatorAddress(ByteUtil.pick(addresses, addressLength, addressLength));
        unBondingDelegation.setCreateHeight(value.getCreationHeight());
        if (value.getMinTime() != null){
            unBondingDelegation.setMinTimeInMs(value.getMinTime().getTimeInMilliseconds());
        }
        if (value.getInitialBalance() != null){
            unBondingDelegation.setInitialBalance(value.getInitialBalance().toToken());
        }
        if (value.getBalance() != null){
            unBondingDelegation.setBalance(value.getBalance().toToken());
        }

        return unBondingDelegation;
    }

    private byte[] getSideChainStorePrefixKey(String sideChainId){
        byte[] sideChainIdBytes = sideChainId.getBytes(defaultCharset);
        byte[] key = ByteUtil.appendBytesArray(sideChainStorePrefixByIdKey, sideChainIdBytes);
        byte[] result = queryStore(scStoreKey, key);

        if (result == null || result.length == 0){
            throw new IllegalArgumentException("Invalid sideChainId: " + sideChainId);
        }

        return result;
    }

    private byte[] getValidatorKey(byte[] operatorAddress){
        return ByteUtil.appendBytesArray(validatorsKey, operatorAddress);
    }

    private byte[] getRedelegationKey(byte[] delAddr, byte[] srcValAddr, byte[] dstValAddr){
        int length = 1 + addressLength * 3;
        byte[] key = new byte[length];

        byte[] redsKey = getRedelegationsKey(delAddr);
        System.arraycopy(redsKey, 0, key, 0, redsKey.length);
        System.arraycopy(srcValAddr, 0, key, redsKey.length, srcValAddr.length);
        System.arraycopy(dstValAddr, 0, key, redsKey.length + srcValAddr.length, dstValAddr.length);
        return key;
    }

    private byte[] getDelegationKey(byte[] delegateAddress, byte[] validatorAddress){
        return ByteUtil.appendBytesArray(getDelegationsKey(delegateAddress), validatorAddress);
    }

    private byte[] getDelegationsKey(byte[] delegateAddress){
        return ByteUtil.appendBytesArray(delegationKey, delegateAddress);
    }

    private byte[] getRedelegationsKey(byte[] delAddr){
        return ByteUtil.appendBytesArray(redelegationKey, delAddr);
    }

    byte[] getUnBondingDelegationKey(byte[] delegatorAddress, byte[] validatorAddress){
        return ByteUtil.appendBytesArray(getUnBondingDelegationsKey(delegatorAddress), validatorAddress);
    }

    byte[] getUnBondingDelegationsKey(byte[] delegatorAddress){
        return ByteUtil.appendBytesArray(unBondingDelegationKey, delegatorAddress);
    }

    private byte[] queryWithData(String path, byte[] data){
        String d =  "0x" + Hex.toHexString(data);
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, d));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    private byte[] queryStore(String storeName, byte[] key){
        String keyHex =  "0x" + Hex.toHexString(key);
        String path = String.format("\"/store/%s/%s\"", storeName, "key");
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, keyHex));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    private byte[] queryStoreSubspace(String storeName, byte[] key){
        String keyHex =  "0x" + Hex.toHexString(key);
        String path = String.format("\"/store/%s/subspace\"", storeName);
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, keyHex));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    private List<common.Types.KVPair> queryStoreSubspaceKVPairs(String storeName, byte[] key) throws IOException {
        String keyHex =  "0x" + Hex.toHexString(key);
        String path = String.format("\"/store/%s/subspace\"", storeName);
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, keyHex));
        checkABCIResponse(rpcResponse);

        byte[] result = rpcResponse.getResult().getResponse().getValue();

        if (!ByteUtil.isEmpty(result)) {
            try {
                CodedInputStream codedInputStream = CodedInputStream.newInstance(result);

                if (codedInputStream.isAtEnd()){
                    return new ArrayList<>();
                }
                //read length prefix
                int length = codedInputStream.readRawVarint32();
                if (length == 0){
                    return new ArrayList<>();
                }

                Types.KVPairs kvPairs = Types.KVPairs.parseFrom(codedInputStream);

                return kvPairs.getPairsList();
            }catch (InvalidProtocolBufferException e){
                throw new IOException("Decode response failed due to: " + e.getMessage());
            }
        }else{
            return new ArrayList<>();
        }
    }

    private void checkABCIResponse(JsonRpcResponse<ABCIQueryResult> rpcResponse) {
        if (null != rpcResponse.getError() && null != rpcResponse.getError().getCode() && rpcResponse.getError().getCode().intValue() != 0) {
            throw new RuntimeException(rpcResponse.getError().toString());
        }
        ABCIQueryResult.Response response = rpcResponse.getResult().getResponse();
        if (response.getCode() != null) {
            BinanceDexApiError binanceDexApiError = new BinanceDexApiError();
            binanceDexApiError.setCode(response.getCode());
            binanceDexApiError.setMessage(response.getLog());
            throw new BinanceDexApiException(binanceDexApiError);
        }
    }

}
