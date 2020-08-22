package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.domain.oracle.Prophecy;
import com.binance.dex.api.client.domain.oracle.Status;
import com.binance.dex.api.client.encoding.Bech32;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.message.bridge.DBProphecy;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fitz.Lu
 **/
public class NodeQueryDelegateOracle extends NodeQuery {

    private static final String oracleStoreName = "oracle";

    private static final String claimTypeSequencePrefix = "claimTypeSeq:";

    private static final Amino amino = new Amino();

    public NodeQueryDelegateOracle(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        super(binanceDexNodeApi, hrp, valHrp);
    }

    @Nullable
    public Prophecy getProphecy(int claimType, long sequence) throws IOException {
        byte[] key = getClaimId(claimType, sequence).getBytes(Charsets.UTF_8);
        byte[] result = queryStore(oracleStoreName, key);

        if (!ByteUtil.isEmpty(result)){
            DBProphecy prophecy = new DBProphecy();
            amino.decodeBare(result, prophecy);
            return convert(prophecy);
        }

        return null;
    }

    public long getCurrentSequence(int claimType){
        byte[] key = getClaimTypeSequence(claimType);
        byte[] result = queryStore(oracleStoreName, key);
        if (!ByteUtil.isEmpty(result)){
            return java.nio.ByteBuffer.wrap(result).order(ByteOrder.BIG_ENDIAN).getLong();
        }

        return 0;
    }

    private Prophecy convert(DBProphecy dbProphecy) throws IOException {
        Prophecy prophecy = new Prophecy();
        HashMap<String, String> validatorClaims;
        validatorClaims = EncodeUtils.getObjectMapper().readValue(new String(dbProphecy.getValidatorClaims()), new TypeReference<HashMap<String, String>>(){});

        HashMap<String, byte[]> claimValidators = new HashMap<>();
        for (Map.Entry<String, String> entry : validatorClaims.entrySet()) {
            claimValidators.put(entry.getValue(), Bech32.decode(entry.getKey()).getData());
        }

        prophecy.setId(dbProphecy.getId());

        Status status = new Status();
        if (dbProphecy.getStatus() != null){
            status.setText(dbProphecy.getStatus().getText());
            status.setFinalClaim(dbProphecy.getStatus().getFinalClaim());
        }
        prophecy.setStatus(status);
        prophecy.setValidatorClaims(validatorClaims);
        prophecy.setClaimValidators(claimValidators);

        return prophecy;
    }

    private String getClaimId(int claimType, long sequence) {
        return String.format("%d:%d", claimType, sequence);
    }

    private byte[] getClaimTypeSequence(int claimType){
        return ByteUtil.appendBytesArray(claimTypeSequencePrefix.getBytes(Charsets.UTF_8), new byte[]{((byte) claimType)});
    }

}
