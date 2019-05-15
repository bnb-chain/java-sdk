package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.TransactionConverter;
import com.binance.dex.api.client.encoding.Bech32;
import com.binance.dex.api.client.encoding.Crypto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by fletcher on 2019/5/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StakeValidator {

    @JsonProperty(value = "operator_address")
    private String operaAddress;

    private String operaHrAddress;

    @JsonProperty(value = "fee_addr")
    private String feeAddress;

    @JsonProperty(value = "consensus_pubkey")
    @JsonSerialize(using = ConsensusAddrSerializer.class)
    private String consensusAddress;

    @JsonProperty(value = "tokens")
    private Long votingPower;

    private Boolean jailed;

    private Integer status;

    private Description description;

    public Boolean getJailed() {
        return jailed;
    }

    public void setJailed(Boolean jailed) {
        this.jailed = jailed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public static class Description {
        private String moniker;
        private String identity;
        private String website;
        private String details;

        public String getMoniker() {
            return moniker;
        }

        public void setMoniker(String moniker) {
            this.moniker = moniker;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public String getOperaAddress() {
        return operaAddress;
    }

    public void setOperaAddress(String operaAddress) {
        this.operaAddress = operaAddress;
    }

    public String getFeeAddress() {
        return feeAddress;
    }

    public void setFeeAddress(String feeAddress) {
        this.feeAddress = feeAddress;
    }

    public String getConsensusAddress() {
        return consensusAddress;
    }

    public void setConsensusAddress(String consensusAddress) {
        this.consensusAddress = consensusAddress;
    }

    public Long getVotingPower() {
        return votingPower;
    }

    public void setVotingPower(Long votingPower) {
        this.votingPower = votingPower;
    }


    private static ObjectMapper objectMapper = new ObjectMapper();
    public static StakeValidator fromJson(String json,String hrp){
        try {
            StakeValidator stakeValidator = objectMapper.readValue(json,StakeValidator.class);
            if(null != stakeValidator && null != stakeValidator.getOperaAddress()){
                stakeValidator.setOperaHrAddress(Bech32.encode(hrp,Bech32.decode(stakeValidator.getOperaAddress()).getData()));
            }
            return stakeValidator;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<StakeValidator> fromJsonToArray(String json, String hrp){
        try {
            List<StakeValidator> list =  objectMapper.readValue(json,objectMapper.getTypeFactory().constructCollectionType(List.class,StakeValidator.class));
            if(list != null && list.size() > 0){
                list = list.stream().map(stakeValidator -> {
                    if(null != stakeValidator && null != stakeValidator.getOperaAddress()){
                        stakeValidator.setOperaHrAddress(Bech32.encode(hrp,Bech32.decode(stakeValidator.getOperaAddress()).getData()));
                    }
                    return stakeValidator;
                }).collect(Collectors.toList());
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOperaHrAddress() {
        return operaHrAddress;
    }

    public void setOperaHrAddress(String operaHrAddress) {
        this.operaHrAddress = operaHrAddress;
    }

    static class ConsensusAddrSerializer extends JsonSerializer<String>{

        @Override
        public void serialize(String consensusPubKey, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if(StringUtils.isBlank(consensusPubKey)){
                return;
            }
            byte[] pubkey = Crypto.decodeAddress(consensusPubKey);
            int startIndex = getStartIndex(pubkey);
            byte[] array = new byte[pubkey.length - startIndex];
            System.arraycopy(pubkey, startIndex, array, 0, array.length);
            jsonGenerator.writeString(Hex.toHexString(Sha256Hash.hash(array)).toUpperCase().substring(0,40));

        }
    }

    private static int getStartIndex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x80) {
                return i + 5;
            }
        }
        return -1;
    }


}
