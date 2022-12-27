package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.domain.stake.Delegation;
import com.binance.dex.api.client.domain.stake.Redelegation;
import com.binance.dex.api.client.domain.stake.UnBondingDelegation;
import com.binance.dex.api.client.domain.stake.Validator;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.beaconchain.query.BeaconChainRedelegationMessage;
import com.binance.dex.api.client.encoding.message.beaconchain.query.BeaconChainUnBondingDelegationMessage;
import com.binance.dex.api.client.encoding.message.beaconchain.query.PoolMessage;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.sidechain.query.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francis.Liu
 **/
public class NodeQueryDelegateBeaconChainStaking extends NodeQuery {

    private String sideChainId = "";

    private NodeQueryDelegateSideChainStaking sideChainStakingQuery;

    public NodeQueryDelegateBeaconChainStaking(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp, NodeQueryDelegateSideChainStaking sideChainStakingQuery) {
        super(binanceDexNodeApi, hrp, valHrp);
        this.sideChainStakingQuery = sideChainStakingQuery;
    }

    public Validator queryBeaconChainValidator(String validatorAddress) throws IOException {
        QueryValidatorParams params = new QueryValidatorParams(sideChainId, Bech32AddressValue.fromBech32StringWithNewHrp(validatorAddress, valHrp));
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/validator\"", data);
        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            BechValidator validator = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<BechValidator>(){});
            return validator.toValidator();
        }
        return null;
    }

    public List<Validator> queryBeaconChainTopValidators(int top) throws IOException {
        List<Validator> validators = this.sideChainStakingQuery.querySideChainTopValidators(sideChainId, top);
        return validators;
    }

    public Delegation queryBeaconChainDelegation(String delegatorAddress, String validatorAddress) throws IOException {
        return this.sideChainStakingQuery.querySideChainDelegation(sideChainId, delegatorAddress, validatorAddress);
    }

    public List<Delegation> queryBeaconChainDelegations(String delegatorAddress) throws IOException {
        List<Delegation> delegations = this.sideChainStakingQuery.querySideChainDelegations(sideChainId, delegatorAddress);
        return delegations;
    }

    public Redelegation queryBeaconChainRedelegation(String delegatorAddress, String srcValidatorAddress, String dstValidatorAddress) throws IOException {
        QueryRedelegationParams params = new QueryRedelegationParams(sideChainId, delegatorAddress, srcValidatorAddress, dstValidatorAddress);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/redelegation\"", data);

        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            BeaconChainRedelegationMessage message = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<BeaconChainRedelegationMessage>(){});
            return message.toRedelegation();
        }
        return null;
    }

    public List<Redelegation> queryBeaconChainRedelegations(String delegatorAddress) throws IOException {
        QueryDelegatorParams params = new QueryDelegatorParams();
        params.setDelegatorAddr(delegatorAddress);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/delegatorRedelegations\"", data);
        List<Redelegation> results = new ArrayList<>();
        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            List<BeaconChainRedelegationMessage> messages = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<List<BeaconChainRedelegationMessage>>(){});
            for (BeaconChainRedelegationMessage message : messages) {
                results.add(message.toRedelegation());
            }
        }
        return results;
    }


    public UnBondingDelegation queryBeaconChainUnBondingDelegation(String delegatorAddress, String validatorAddress) throws IOException {
        QueryBondsParams params = new QueryBondsParams();
        params.setDelegatorAddr(delegatorAddress);
        params.setValidatorAddr(validatorAddress);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/unbondingDelegation\"", data);

        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            BeaconChainUnBondingDelegationMessage message = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<BeaconChainUnBondingDelegationMessage>(){});
            return message.toBeaconChainUnBondingDelegation();
        }
        return null;
    }

    public List<UnBondingDelegation> queryBeaconChainUnBondingDelegations(String delegatorAddress) throws IOException {
        QueryDelegatorParams params = new QueryDelegatorParams();
        params.setDelegatorAddr(delegatorAddress);
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/delegatorUnbondingDelegations\"", data);
        List<UnBondingDelegation> results = new ArrayList<>();
        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            List<BeaconChainUnBondingDelegationMessage> messages = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<List<BeaconChainUnBondingDelegationMessage>>(){});
            for (BeaconChainUnBondingDelegationMessage message : messages) {
                results.add(message.toBeaconChainUnBondingDelegation());
            }
        }
        return results;
    }

    public List<UnBondingDelegation> queryBeaconChainUnBondingDelegationsByValidator(String validatorAddress) throws IOException {
        List<UnBondingDelegation> unBondingDelegations = this.sideChainStakingQuery.querySideChainUnBondingDelegationsByValidator(sideChainId, validatorAddress);
        return unBondingDelegations;
    }

    public List<Redelegation> queryBeaconChainRedelegationsByValidator(String validatorAddress) throws IOException {
        List<Redelegation> redelegations = this.sideChainStakingQuery.querySideChainRedelegationsByValidator(sideChainId, validatorAddress);
        return redelegations;
    }

    public Pool queryBeaconChainPool() throws IOException {
        BaseQueryParams params = new BaseQueryParams();
        byte[] data = EncodeUtils.toJsonEncodeBytes(params);
        byte[] result = queryWithData("\"custom/stake/pool\"", data);

        if (!ByteUtil.isEmpty(result)){
            String jsonStr = new String(result);
            PoolMessage message = EncodeUtils.getObjectMapper().readValue(jsonStr, new TypeReference<PoolMessage>(){});
            return message.toPool();
        }
        return null;
    }

    public long queryAllBeaconChainValidatorsCount(boolean jailInvolved) throws IOException {
        return this.sideChainStakingQuery.queryAllSideChainValidatorsCount(sideChainId, jailInvolved);
    }

}
