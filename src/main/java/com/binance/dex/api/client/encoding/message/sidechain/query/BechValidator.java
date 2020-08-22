package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.sidechain.SideChainValidator;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Fitz.Lu
 **/
public class BechValidator {

    static class CommissionForDecode {

        @JsonProperty(value = "rate")
        private long rate;

        @JsonProperty(value = "max_rate")
        private long maxRate;

        @JsonProperty(value = "max_change_rate")
        private long maxChangeRate;

        @JsonProperty(value = "update_time")
        private String updateTime;

        public CommissionForDecode() {
        }

        public long getRate() {
            return rate;
        }

        public void setRate(long rate) {
            this.rate = rate;
        }

        public long getMaxRate() {
            return maxRate;
        }

        public void setMaxRate(long maxRate) {
            this.maxRate = maxRate;
        }

        public long getMaxChangeRate() {
            return maxChangeRate;
        }

        public void setMaxChangeRate(long maxChangeRate) {
            this.maxChangeRate = maxChangeRate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    @JsonProperty(value = "fee_addr")
    private String feeAddr;

    @JsonProperty(value = "operator_address")
    private String operatorAddr;

    @JsonProperty(value = "consensus_pubkey")
    private String consPubKey;

    @JsonProperty(value = "jailed")
    private boolean jailed;

    @JsonProperty(value = "status")
    private int status;

    @JsonProperty(value = "tokens")
    private long tokens;

    @JsonProperty(value = "delegator_shares")
    private long delegatorShares;

    @JsonProperty(value = "description")
    private DescriptionValue description;

    @JsonProperty(value = "bond_height")
    private long bondHeight;

    @JsonProperty(value = "bond_intra_tx_counter")
    private int bondIntraTxCounter;

    @JsonProperty(value = "unbonding_height")
    private long unBondingHeight;

    @JsonProperty(value = "unbonding_time")
    private String unBondingMinTime;

    @JsonProperty(value = "commission")
    private CommissionForDecode commission;

    @JsonProperty(value = "distribution_addr")
    private String distributionAddr;

    @JsonProperty(value = "side_chain_id")
    private String sideChainId;

    @JsonProperty(value = "side_cons_addr")
    private String sideConsAddr;

    @JsonProperty(value = "side_fee_addr")
    private String sideFeeAddr;

    public BechValidator() {
    }


    public SideChainValidator toSideChainValidator() {
        SideChainValidator validator = new SideChainValidator();
        if (feeAddr != null) {
            validator.setFeeAddr(feeAddr);
        }
        if (operatorAddr != null) {
            validator.setOperatorAddr(operatorAddr);
        }
        if (consPubKey != null) {
            try {
                validator.setConsPubKey(Amino.getByteArrayAfterTypePrefix(consPubKey.getBytes()));
            }catch (IllegalStateException e){
                validator.setConsPubKey(consPubKey.getBytes());
            }
        }
        validator.setJailed(jailed);
        validator.setStatus(status);
        validator.setTokens(tokens);
        validator.setDelegatorShares(delegatorShares);

        Description descript = new Description();
        if (description != null) {
            descript.setMoniker(description.getMoniker());
            descript.setWebsite(description.getWebsite());
            descript.setDetails(description.getDetails());
            descript.setIdentity(description.getIdentity());
        }
        validator.setDescription(descript);

        validator.setBondHeight(bondHeight);
        validator.setBondIntraTxCounter(bondIntraTxCounter);
        validator.setUnBondingHeight(unBondingHeight);
//        validator.setUnBondingMinTime(unBondingMinTime);

        Commission comm = new Commission();
        if (commission != null) {
            comm.setRate(commission.getRate());
            comm.setMaxRate(commission.getMaxRate());
            comm.setMaxChangeRate(commission.getMaxChangeRate());
//            comm.setUpdateTimeInMs(commission.getUpdateTime());
        }
        validator.setCommission(comm);

        if (!StringUtils.isEmpty(sideChainId)) {
            if (distributionAddr != null) {
                validator.setDistributionAddr(distributionAddr);
            }
            validator.setSideChainId(sideChainId);
            if (sideConsAddr != null) {
                validator.setSideConsAddr(sideConsAddr);
            }
            if (sideFeeAddr != null) {
                validator.setSideFeeAddr(sideFeeAddr);
            }
        }

        return validator;
    }

    public String getConsPubKey() {
        return consPubKey;
    }

    public void setConsPubKey(String consPubKey) {
        this.consPubKey = consPubKey;
    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public long getDelegatorShares() {
        return delegatorShares;
    }

    public void setDelegatorShares(long delegatorShares) {
        this.delegatorShares = delegatorShares;
    }

    public long getBondHeight() {
        return bondHeight;
    }

    public void setBondHeight(long bondHeight) {
        this.bondHeight = bondHeight;
    }

    public int getBondIntraTxCounter() {
        return bondIntraTxCounter;
    }

    public void setBondIntraTxCounter(int bondIntraTxCounter) {
        this.bondIntraTxCounter = bondIntraTxCounter;
    }

    public long getUnBondingHeight() {
        return unBondingHeight;
    }

    public void setUnBondingHeight(long unBondingHeight) {
        this.unBondingHeight = unBondingHeight;
    }

    public String getUnBondingMinTime() {
        return unBondingMinTime;
    }

    public void setUnBondingMinTime(String unBondingMinTime) {
        this.unBondingMinTime = unBondingMinTime;
    }

    public DescriptionValue getDescription() {
        return description;
    }

    public void setDescription(DescriptionValue description) {
        this.description = description;
    }

    public CommissionForDecode getCommission() {
        return commission;
    }

    public void setCommission(CommissionForDecode commission) {
        this.commission = commission;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public String getSideConsAddr() {
        return sideConsAddr;
    }

    public void setSideConsAddr(String sideConsAddr) {
        this.sideConsAddr = sideConsAddr;
    }

    public String getSideFeeAddr() {
        return sideFeeAddr;
    }

    public void setSideFeeAddr(String sideFeeAddr) {
        this.sideFeeAddr = sideFeeAddr;
    }

    public String getFeeAddr() {
        return feeAddr;
    }

    public void setFeeAddr(String feeAddr) {
        this.feeAddr = feeAddr;
    }

    public String getOperatorAddr() {
        return operatorAddr;
    }

    public void setOperatorAddr(String operatorAddr) {
        this.operatorAddr = operatorAddr;
    }

    public String getDistributionAddr() {
        return distributionAddr;
    }

    public void setDistributionAddr(String distributionAddr) {
        this.distributionAddr = distributionAddr;
    }
}
