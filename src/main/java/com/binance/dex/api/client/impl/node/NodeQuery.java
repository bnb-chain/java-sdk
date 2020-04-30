package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexApiClientGenerator;
import com.binance.dex.api.client.BinanceDexApiError;
import com.binance.dex.api.client.BinanceDexApiException;
import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.domain.jsonrpc.ABCIQueryResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import common.Types;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
class NodeQuery {

    private final BinanceDexNodeApi binanceDexNodeApi;

    protected final String hrp;

    protected final String valHrp;

    public NodeQuery(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        this.binanceDexNodeApi = binanceDexNodeApi;
        this.hrp = hrp;
        this.valHrp = valHrp;
    }

    protected byte[] queryWithData(String path, byte[] data){
        String d =  "0x" + Hex.toHexString(data);
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, d));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    protected byte[] queryStore(String storeName, byte[] key){
        String keyHex =  "0x" + Hex.toHexString(key);
        String path = String.format("\"/store/%s/%s\"", storeName, "key");
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, keyHex));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    protected byte[] queryStoreSubspace(String storeName, byte[] key){
        String keyHex =  "0x" + Hex.toHexString(key);
        String path = String.format("\"/store/%s/subspace\"", storeName);
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.abciQuery(path, keyHex));
        checkABCIResponse(rpcResponse);

        return rpcResponse.getResult().getResponse().getValue();
    }

    protected List<Types.KVPair> queryStoreSubspaceKVPairs(String storeName, byte[] key) throws IOException {
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

                types.Types.KVPairs kvPairs = types.Types.KVPairs.parseFrom(codedInputStream);

                return kvPairs.getPairsList();
            }catch (InvalidProtocolBufferException e){
                throw new IOException("Decode response failed due to: " + e.getMessage());
            }
        }else{
            return new ArrayList<>();
        }
    }

    protected void checkABCIResponse(JsonRpcResponse<ABCIQueryResult> rpcResponse) {
        if (null != rpcResponse.getError() && null != rpcResponse.getError().getCode()) {
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
