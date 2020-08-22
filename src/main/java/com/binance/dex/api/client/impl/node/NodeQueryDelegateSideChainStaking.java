package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.domain.stake.sidechain.*;
import com.binance.dex.api.client.domain.stake.sidechain.Delegation;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.amino.InternalAmino;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.binance.dex.api.client.encoding.message.sidechain.query.*;
import com.binance.dex.api.client.encoding.message.sidechain.value.RedelegationValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.UnBondingValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class NodeQueryDelegateSideChainStaking extends NodeQuery {

    private final String stakeStoreName = "stake";
    private final String scStoreKey = "sc";

    private static final int addressLength            = 20;

    private final byte[] sideChainStorePrefixByIdKey  = new byte[]{0x01};
    private final byte[] validatorsKey                = new byte[]{0x21};
    private final byte[] delegationKey                = new byte[]{0x31};
    private final byte[] redelegationKey              = new byte[]{0x34};
    private final byte[] unBondingDelegationKey       = new byte[]{0x32};
    private final byte[] poolKey                      = new byte[]{0x01};

    private final Charset defaultCharset              = Charsets.UTF_8;

    private final Amino amino;

    public NodeQueryDelegateSideChainStaking(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        super(binanceDexNodeApi, hrp, valHrp);
        amino = InternalAmino.get();
    }

    public SideChainValidator querySideChainValidator(String sideChainId, String validatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, getValidatorKey(Crypto.decodeAddress(validatorAddress)));
        byte[] result = queryStore(stakeStoreName, key);

        if (result != null) {
            SideChainValidatorMessage message = new SideChainValidatorMessage();
            amino.decodeWithLengthPrefix(result, message);
            return convert(message);
        }else{
            return null;
        }
    }

    public List<SideChainValidator> querySideChainTopValidators(String sideChainId, int top) throws IOException {
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

        if (message.getFeeAddr() != null) {
            sideChainValidator.setFeeAddr(EncodeUtils.bytesToPrefixHex(message.getFeeAddr()));
        }
        if (message.getOperatorAddr() != null) {
            sideChainValidator.setOperatorAddr(Crypto.encodeAddress(valHrp, message.getOperatorAddr()));
        }

        //TODO consPubKey use amino decode with type prefix
        if (message.getConsPubKey() != null) {
            boolean fallBack = true;
            byte[] typePrefix = ByteUtil.pick(message.getConsPubKey(), 0, 4);
            if (WireType.isRegistered(typePrefix)) {
                byte length = ByteUtil.pick(message.getConsPubKey(), 4, 1)[0];
                int len = Byte.toUnsignedInt(length);
                if (message.getConsPubKey().length - 5 == len) {
                    sideChainValidator.setConsPubKey(ByteUtil.cut(message.getConsPubKey(), 5));
                    fallBack = false;
                }
            }
            if (fallBack) {
                sideChainValidator.setConsPubKey(message.getConsPubKey());
            }
        }

        if (message.getDistributionAddr() != null){
            sideChainValidator.setDistributionAddr(Crypto.encodeAddress(hrp, message.getDistributionAddr()));
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
            if (message.getCommission().getRate() != null) {
                commission.setRate(message.getCommission().getRate().getValue());
            }
            if (message.getCommission().getMaxRate() != null) {
                commission.setMaxRate(message.getCommission().getMaxRate().getValue());
            }
            if (message.getCommission().getMaxChangeRate() != null) {
                commission.setMaxChangeRate(message.getCommission().getMaxChangeRate().getValue());
            }
        }
        sideChainValidator.setCommission(commission);

        sideChainValidator.setSideChainId(message.getSideChainId());

        if (message.getSideConsAddr() != null) {
            sideChainValidator.setSideConsAddr(EncodeUtils.bytesToPrefixHex(message.getSideConsAddr()));
        }
        if (message.getSideFeeAddr() != null) {
            sideChainValidator.setSideFeeAddr(EncodeUtils.bytesToPrefixHex(message.getSideFeeAddr()));
        }

        return sideChainValidator;
    }

    public SideChainDelegation querySideChainDelegation(String sideChainId, String delegatorAddress, String validatorAddress) throws IOException {
        QueryBondsParams params = new QueryBondsParams();
        params.setSideChainId(sideChainId);
        params.setDelegatorAddr(delegatorAddress);
        params.setValidatorAddr(validatorAddress);

        byte[] paramsBytes = EncodeUtils.toJsonEncodeBytes(params);

        byte[] response = queryWithData("\"custom/stake/delegation\"", paramsBytes);
        if (!ByteUtil.isEmpty(response)){
            DelegationResponse delegationResponse = EncodeUtils.getObjectMapper().readValue(response, DelegationResponse.class);
            return convert(delegationResponse);
        }

        return null;
    }

    public List<SideChainDelegation> querySideChainDelegations(String sideChainId, String delegatorAddress) throws IOException {
        QueryDelegatorParams params = new QueryDelegatorParams();
        params.setSideChainId(sideChainId);
        params.setDelegatorAddr(delegatorAddress);

        byte[] paramsBytes = EncodeUtils.toJsonEncodeBytes(params);
        byte[] response = queryWithData("\"custom/stake/delegatorDelegations\"", paramsBytes);

        List<SideChainDelegation> results = new ArrayList<>();

        if (!ByteUtil.isEmpty(response)){
            String a = new String(response);
            List<DelegationResponse> delegations = EncodeUtils.getObjectMapper().readValue(response, new TypeReference<List<DelegationResponse>>(){});
            for (DelegationResponse delegation : delegations) {
                results.add(convert(delegation));
            }
        }

        return results;
    }

    private SideChainDelegation convert(DelegationResponse delegationResponse){
        SideChainDelegation sideChainDelegation = new SideChainDelegation();
        if (delegationResponse.getDelegation() != null) {
            Delegation delegation = new Delegation();
            delegation.setDelegatorAddress(delegationResponse.getDelegation().getDelegatorAddress());
            delegation.setValidatorAddress(delegationResponse.getDelegation().getValidatorAddress());
            delegation.setShares(delegationResponse.getDelegation().getShares());
            sideChainDelegation.setDelegation(delegation);
        }
        if (delegationResponse.getBalance() != null){
            sideChainDelegation.setBalance(convert(delegationResponse.getBalance()));
        }

        return sideChainDelegation;
    }

    public SideChainRedelegation querySideChainRedelegation(String sideChainId, String delegatorAddress, String srcValidatorAddress, String dstValidatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] redKey = getRedelegationKey(Crypto.decodeAddress(delegatorAddress), Crypto.decodeAddress(srcValidatorAddress),
                Crypto.decodeAddress(dstValidatorAddress));
        byte[] key = ByteUtil.appendBytesArray(storePrefix, redKey);
        byte[] result = queryStore(stakeStoreName, key);

        if (!ByteUtil.isEmpty(result)){
            RedelegationValue message = new RedelegationValue();
            amino.decodeWithLengthPrefix(result, message);
            return convert(message, redKey);
        }

        return null;
    }

    public List<SideChainRedelegation> querySideChainRedelegations(String sideChainId, String delegatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] redsKey = getRedelegationsKey(Crypto.decodeAddress(delegatorAddress));
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

        String  delAddr = Crypto.encodeAddress(hrp, ByteUtil.pick(addresses, 0, addressLength));
        String srcValAddr = Crypto.encodeAddress(valHrp, ByteUtil.pick(addresses, addressLength, addressLength));
        String dstValAddr = Crypto.encodeAddress(valHrp, ByteUtil.pick(addresses, addressLength * 2, addressLength));

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

    public UnBondingDelegation querySideChainUnBondingDelegation(String sideChainId, String delegatorAddress, String validatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] ubdKey = getUnBondingDelegationKey(Crypto.decodeAddress(delegatorAddress), Crypto.decodeAddress(validatorAddress));
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

    public List<UnBondingDelegation> querySideChainUnBondingDelegations(String sideChainId, String delegatorAddress) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] ubdsKey = getUnBondingDelegationsKey(Crypto.decodeAddress(delegatorAddress));
        byte[] key = ByteUtil.appendBytesArray(storePrefix, ubdsKey);
        List<common.Types.KVPair> kvPairs = queryStoreSubspaceKVPairs(stakeStoreName, key);
        List<UnBondingDelegation> unBondingDelegations = new ArrayList<>();
        if (kvPairs != null && !kvPairs.isEmpty()){
            for (common.Types.KVPair kvPair : kvPairs) {
                UnBondingValue unBondingValue = new UnBondingValue();
                amino.decodeWithLengthPrefix(kvPair.getValue().toByteArray(), unBondingValue);
                unBondingDelegations.add(convert(unBondingValue, ByteUtil.cut(kvPair.getKey().toByteArray(), storePrefix.length + unBondingDelegationKey.length)));
            }
        }

        return unBondingDelegations;
    }

    public List<UnBondingDelegation> querySideChainUnBondingDelegationsByValidator(String sideChainId, String validatorAddress) throws IOException {
        QueryValidatorParams params = new QueryValidatorParams(sideChainId, Bech32AddressValue.fromBech32StringWithNewHrp(validatorAddress, valHrp));
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/validatorUnbondingDelegations\"", data);

        List<UnBondingDelegation> unBondingDelegations = new ArrayList<>();

        if (!ByteUtil.isEmpty(result)){
            List<UnBondingDelegationMessage> messages = EncodeUtils.getObjectMapper().readValue(result, new TypeReference<List<UnBondingDelegationMessage>>(){});
            if (messages != null && !messages.isEmpty()){
                for (UnBondingDelegationMessage message : messages) {
                    unBondingDelegations.add(convert(message));
                }
            }
        }

        return unBondingDelegations;
    }

    public List<SideChainRedelegation> querySideChainRedelegationsByValidator(String sideChainId, String validatorAddress) throws IOException {
        QueryValidatorParams params = new QueryValidatorParams(sideChainId, Bech32AddressValue.fromBech32StringWithNewHrp(validatorAddress, valHrp));
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/validatorRedelegations\"", data);

        List<SideChainRedelegation> redelegations = new ArrayList<>();

        if (!ByteUtil.isEmpty(result)){
            List<SideChainRedelegationMessage> messages = EncodeUtils.getObjectMapper().readValue(result, new TypeReference<List<SideChainRedelegationMessage>>(){});
            if (messages != null && !messages.isEmpty()){
                for (SideChainRedelegationMessage message : messages) {
                    redelegations.add(convert(message));
                }
            }
        }

        return redelegations;
    }

    public Pool querySideChainPool(String sideChainId) throws IOException {
        byte[] storePrefix = getSideChainStorePrefixKey(sideChainId);
        byte[] key = ByteUtil.appendBytesArray(storePrefix, poolKey);
        byte[] result = queryStore(stakeStoreName, key);

        if (!ByteUtil.isEmpty(result)){
            PoolMessage message = new PoolMessage();
            amino.decodeWithLengthPrefix(result, message);

            Pool pool = new Pool();
            if (message.getLooseTokens() != null){
                pool.setLooseTokens(message.getLooseTokens().getValue());
            }
            if (message.getBondedTokens() != null){
                pool.setBondedTokens(message.getBondedTokens().getValue());
            }

            return pool;
        }

        return null;
    }

    public long queryAllSideChainValidatorsCount(String sideChainId, boolean jailInvolved) throws IOException {
        BaseQueryParams params = new BaseQueryParams();
        params.setSideChainId(sideChainId);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        String path = "\"custom/stake/allUnJailValidatorsCount\"";
        if (jailInvolved){
            path = "\"custom/stake/allValidatorsCount\"";
        }
        byte[] result = queryWithData(path, data);
        if (!ByteUtil.isEmpty(result)){
            String count = new String(result);
            if (count.contains("\"")){
                count = count.replaceAll("\"", "");
            }
            return Long.parseLong(count);
        }

        return 0L;
    }

    private SideChainRedelegation convert(SideChainRedelegationMessage message){
        SideChainRedelegation redelegation = new SideChainRedelegation();
        redelegation.setDelegatorAddress(message.getDelegatorAddress());
        redelegation.setSrcValidatorAddress(message.getSrcValidatorAddress());
        redelegation.setDstValidatorAddress(message.getDstValidatorAddress());
        redelegation.setCreateHeight(message.getCreateHeight());
        redelegation.setMinTime(message.getMinTime());
        if (message.getInitialBalance() != null) {
            redelegation.setInitialBalance(convert(message.getInitialBalance()));
        }
        if (message.getBalance() != null){
            redelegation.setBalance(convert(message.getBalance()));
        }
        redelegation.setSrcShares(message.getSrcShares());
        redelegation.setDstShare(message.getDstShare());

        return redelegation;
    }

    private Token convert(CoinValue coinValue){
        Token token = new Token();
        token.setDenom(coinValue.getDenom());
        token.setAmount(coinValue.getAmount());
        return token;
    }

    private UnBondingDelegation convert(UnBondingDelegationMessage message){
        UnBondingDelegation unBondingDelegation = new UnBondingDelegation();
        unBondingDelegation.setBalance(message.getBalance());
        unBondingDelegation.setCreateHeight(message.getCreateHeight());
        unBondingDelegation.setDelegatorAddress(message.getDelegatorAddress());
        unBondingDelegation.setValidatorAddress(message.getValidatorAddress());
        unBondingDelegation.setInitialBalance(message.getInitialBalance());
        unBondingDelegation.setMinTime(message.getMinTime());

        return unBondingDelegation;
    }

    private UnBondingDelegation convert(UnBondingValue value, byte[] addresses){
        if (addresses.length != addressLength * 2){
            throw new IllegalStateException("unexpected address length for this (address, validator) pair");
        }

        UnBondingDelegation unBondingDelegation = new UnBondingDelegation();
        unBondingDelegation.setDelegatorAddress(Crypto.encodeAddress(hrp, ByteUtil.pick(addresses, 0, addressLength)));
        unBondingDelegation.setValidatorAddress(Crypto.encodeAddress(hrp, ByteUtil.pick(addresses, addressLength, addressLength)));
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

}
