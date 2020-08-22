package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexApiClientGenerator;
import com.binance.dex.api.client.BinanceDexApiException;
import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.jsonrpc.AsyncBroadcastResult;
import com.binance.dex.api.client.domain.jsonrpc.CommitBroadcastResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.encoding.message.BinanceDexTransactionMessage;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
class NodeTx {

    protected final BinanceDexNodeApi binanceDexNodeApi;

    protected final String hrp;

    protected final String valHrp;

    NodeTx(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp){
        this.binanceDexNodeApi = binanceDexNodeApi;
        this.hrp = hrp;
        this.valHrp = valHrp;
    }

    protected List<TransactionMetadata> broadcast(BinanceDexTransactionMessage message, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        message.validateBasic();
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        String requestPayload = "0x" + assembler.buildTxPayload(message);
        if (sync) {
            return syncBroadcast(requestPayload, wallet);
        } else {
            return asyncBroadcast(requestPayload, wallet);
        }
    }

    protected List<TransactionMetadata> syncBroadcast(String requestBody, Wallet wallet) {
        try {
            JsonRpcResponse<CommitBroadcastResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.commitBroadcast(requestBody));
            CommitBroadcastResult commitBroadcastResult = rpcResponse.getResult();
            TransactionMetadata transactionMetadata = new TransactionMetadata();
            transactionMetadata.setCode(commitBroadcastResult.getCheckTx().getCode());
            if (commitBroadcastResult.getHeight() != null && StringUtils.isNoneBlank(commitBroadcastResult.getHash()) && transactionMetadata.getCode() == 0) {
                wallet.increaseAccountSequence();
                transactionMetadata.setHash(commitBroadcastResult.getHash());
                transactionMetadata.setHeight(commitBroadcastResult.getHeight());
                transactionMetadata.setLog(commitBroadcastResult.getCheckTx().getLog());
                transactionMetadata.setOk(true);
            } else {
                wallet.invalidAccountSequence();
                transactionMetadata.setLog(commitBroadcastResult.getCheckTx().getLog());
                transactionMetadata.setOk(false);
            }

            return Lists.newArrayList(transactionMetadata);
        } catch (BinanceDexApiException e) {
            wallet.invalidAccountSequence();
            throw new RuntimeException(e);
        }
    }

    protected List<TransactionMetadata> syncBroadcast(String requestBody) {
        try {
            JsonRpcResponse<CommitBroadcastResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.commitBroadcast(requestBody));
            CommitBroadcastResult commitBroadcastResult = rpcResponse.getResult();
            TransactionMetadata transactionMetadata = new TransactionMetadata();
            transactionMetadata.setCode(commitBroadcastResult.getCheckTx().getCode());
            if (commitBroadcastResult.getHeight() != null && StringUtils.isNoneBlank(commitBroadcastResult.getHash()) && transactionMetadata.getCode() == 0) {
                transactionMetadata.setHash(commitBroadcastResult.getHash());
                transactionMetadata.setHeight(commitBroadcastResult.getHeight());
                transactionMetadata.setLog(commitBroadcastResult.getCheckTx().getLog());
                transactionMetadata.setOk(true);
            } else {
                transactionMetadata.setLog(commitBroadcastResult.getCheckTx().getLog());
                transactionMetadata.setOk(false);
            }

            return Lists.newArrayList(transactionMetadata);
        } catch (BinanceDexApiException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<TransactionMetadata> asyncBroadcast(String requestBody) {
        try {
            JsonRpcResponse<AsyncBroadcastResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.asyncBroadcast(requestBody));
            AsyncBroadcastResult asyncBroadcastResult = rpcResponse.getResult();
            TransactionMetadata transactionMetadata = new TransactionMetadata();

            transactionMetadata.setCode(asyncBroadcastResult.getCode());
            transactionMetadata.setLog(asyncBroadcastResult.getLog());

            if (asyncBroadcastResult.getCode() == 0) {
                transactionMetadata.setHash(asyncBroadcastResult.getHash());
                transactionMetadata.setData(asyncBroadcastResult.getData());
                transactionMetadata.setOk(true);
            } else {
                transactionMetadata.setOk(false);
            }

            return Lists.newArrayList(transactionMetadata);
        } catch (BinanceDexApiException e) {
            throw new RuntimeException(e);
        }
    }


    protected List<TransactionMetadata> asyncBroadcast(String requestBody, Wallet wallet) {
        try {
            JsonRpcResponse<AsyncBroadcastResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.asyncBroadcast(requestBody));
            AsyncBroadcastResult asyncBroadcastResult = rpcResponse.getResult();
            TransactionMetadata transactionMetadata = new TransactionMetadata();

            transactionMetadata.setCode(asyncBroadcastResult.getCode());
            transactionMetadata.setLog(asyncBroadcastResult.getLog());

            if (asyncBroadcastResult.getCode() == 0) {
                wallet.increaseAccountSequence();
                transactionMetadata.setHash(asyncBroadcastResult.getHash());
                transactionMetadata.setData(asyncBroadcastResult.getData());
                transactionMetadata.setOk(true);
            } else {
                wallet.invalidAccountSequence();
                transactionMetadata.setOk(false);
            }

            return Lists.newArrayList(transactionMetadata);
        } catch (BinanceDexApiException e) {
            wallet.invalidAccountSequence();
            throw new RuntimeException(e);
        }
    }

}
