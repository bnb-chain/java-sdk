package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.StakeValidator;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.jsonrpc.*;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;
import com.binance.dex.api.proto.*;
import com.binance.dex.api.proto.Token;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.protobuf.ByteString;
import com.google.protobuf.Value;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BinanceDexApiNodeClientImpl implements BinanceDexApiNodeClient {

    private BinanceDexNodeApi binanceDexNodeApi;

    private String hrp;

    private TransactionConverter transactionConverter;

    private FeeConverter feeConverter;

    private final String ARG_ACCOUNT_PREFIX = Hex.toHexString("account:".getBytes());

    private final static int TX_SEARCH_PAGE = 1;

    private final static int TX_SEARCH_PERPAGE = 10000;

    public BinanceDexApiNodeClientImpl(String nodeUrl, String hrp) {
        this.binanceDexNodeApi = BinanceDexApiClientGenerator.createService(BinanceDexNodeApi.class, nodeUrl);
        this.hrp = hrp;
        transactionConverter = new TransactionConverter(hrp);
        feeConverter = new FeeConverter();
    }

    @Override
    public AccountSequence getAccountSequence(String address) {
        Account account = this.getAccount(address);
        AccountSequence accountSequence = new AccountSequence();
        accountSequence.setSequence(account.getSequence());
        return accountSequence;
    }

    @Override
    public Infos getNodeInfo() {
        JsonRpcResponse<NodeInfos> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getNodeStatus());
        checkRpcResult(rpcResponse);
        NodeInfos nodeInfos = rpcResponse.getResult();
        return convert(nodeInfos);
    }

    @Override
    public List<Fees> getFees() {
        try {
            JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getFees());
            checkRpcResult(rpcResponse);
            byte[] value = rpcResponse.getResult().getResponse().getValue();
            int startIndex = 2;
            byte[] array = new byte[value.length - startIndex];
            System.arraycopy(value, startIndex, array, 0, array.length);
            Value protoValue = Value.parseFrom(array);
            List<ByteString> byteStrings = protoValue.getUnknownFields().asMap().get(1).getLengthDelimitedList();
            return byteStrings.stream().map((ByteString byteString)
                    -> feeConverter.convert(byteString.toByteArray())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getAccount(String address) {
        try {
            String queryPath = String.format("\"/account/%s\"",address);
            JsonRpcResponse<AccountResult> response = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getAccount(queryPath));
            checkRpcResult(response);
            byte[] value = response.getResult().getResponse().getValue();
            if(value != null && value.length > 0){
                byte[] array = new byte[value.length - 4];
                System.arraycopy(value, 4, array, 0, array.length);
                AppAccount account = AppAccount.parseFrom(array);
                return convert(account);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getCommittedAccount(String address) {
        String encodedAddress = "0x" + ARG_ACCOUNT_PREFIX + Hex.toHexString(Crypto.decodeAddress(address));
        try {
            JsonRpcResponse<AccountResult> response = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getCommittedAccount(encodedAddress));
            checkRpcResult(response);
            byte[] value = response.getResult().getResponse().getValue();
            if(value != null && value.length > 0){
                byte[] array = new byte[value.length - 4];
                System.arraycopy(value, 4, array, 0, array.length);
                AppAccount account = AppAccount.parseFrom(array);
                return convert(account);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AtomicSwap getSwapByID(String swapID){
        try {
            Map.Entry swapIdEntry = Maps.immutableEntry("SwapID", swapID);
            String requestData = "0x" + Hex.toHexString(EncodeUtils.toJsonStringSortKeys(swapIdEntry).getBytes());
            JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getSwapByID(requestData));
            checkRpcResult(rpcResponse);
            ABCIQueryResult.Response response = rpcResponse.getResult().getResponse();
            if (response.getCode() != null) {
                BinanceDexApiError binanceDexApiError = new BinanceDexApiError();
                binanceDexApiError.setCode(response.getCode());
                binanceDexApiError.setMessage(response.getLog());
                throw new BinanceDexApiException(binanceDexApiError);
            }
            String swapJson = new String(response.getValue());
            return EncodeUtils.toObjectFromJsonString(swapJson, AtomicSwap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getBlockTransactions(Long height) {
        JsonRpcResponse<BlockInfoResult> response = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi
                .getBlockTransactions("\"tx.height=" + height.toString() + "\"",TX_SEARCH_PAGE,TX_SEARCH_PERPAGE));
        checkRpcResult(response);
        return response.getResult().getTxs().stream()
                .map(transactionConverter::convert)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction getTransaction(String hash) {
        try {
            JsonRpcResponse<TransactionResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getTransaction("0x" + hash));
            checkRpcResult(rpcResponse);
            TransactionResult transactionResult = rpcResponse.getResult();
            byte[] txBytes = transactionResult.getTx();
            int startIndex = getStartIndex(txBytes);
            byte[] array = new byte[txBytes.length - startIndex];
            System.arraycopy(txBytes, startIndex, array, 0, array.length);
            StdTx stdTx = StdTx.parseFrom(array);

            List<Transaction> transactions = stdTx.getMsgsList().stream()
                    .map(byteString -> {
                        byte[] bytes = byteString.toByteArray();
                        Transaction transaction = transactionConverter.convert(bytes);
                        transaction.setMemo(stdTx.getMemo());
                        return transaction;
                    }).collect(Collectors.toList());

            if (null != transactions && transactions.size() >= 1) {
                transactions.get(0).setHeight(transactionResult.getHeight());
                transactions.get(0).setHash(transactionResult.getHash());
                transactions.get(0).setCode(transactionResult.getTxResult().getCode());
                transactions.get(0).setLog(transactionResult.getTxResult().getLog());
                transactions.get(0).setTags(transactionResult.getTxResult().getTags());
                return transactions.get(0);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BlockMeta getBlockMetaByHeight(Long height) {
        JsonRpcResponse<BlockMeta.BlockMetaResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getBlock(height));
        checkRpcResult(rpcResponse);
        BlockMeta.BlockMetaResult result = rpcResponse.getResult();
        return result.getBlockMeta();
    }

    @Override
    public BlockMeta getBlockMetaByHash(String hash) {
        try {
            JsonRpcResponse<BlockMeta.BlockMetaResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getBlock("0x" + hash));
            checkRpcResult(rpcResponse);
            BlockMeta.BlockMetaResult result = rpcResponse.getResult();
            return result.getBlockMeta();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public com.binance.dex.api.client.domain.Token getTokenInfoBySymbol(String symbol) {
        try {
            String pathWithSymbol = "\"tokens/info/" + symbol + "\"";
            JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getTokenInfo(pathWithSymbol));
            checkRpcResult(rpcResponse);
            byte[] value = rpcResponse.getResult().getResponse().getValue();
            int startIndex = getStartIndex(value);
            byte[] array = new byte[value.length - startIndex];
            System.arraycopy(value, startIndex, array, 0, array.length);
            TokenInfo tokenInfo = TokenInfo.parseFrom(array);
            return convert(tokenInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StakeValidator> getStakeValidator() {
        JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getStakeValidators());
        checkRpcResult(rpcResponse);
        byte[] value = rpcResponse.getResult().getResponse().getValue();
        return StakeValidator.fromJsonToArray(new String(value), hrp);
    }

    @Override
    public Proposal getProposalById(String proposalId) {
        try {
            Map.Entry proposalIdEntry = Maps.immutableEntry("ProposalID", proposalId);
            String requestData = "0x" + Hex.toHexString(EncodeUtils.toJsonStringSortKeys(proposalIdEntry).getBytes());
            JsonRpcResponse<ABCIQueryResult> rpcResponse = BinanceDexApiClientGenerator.executeSync(binanceDexNodeApi.getProposalById(requestData));
            checkRpcResult(rpcResponse);
            ABCIQueryResult.Response response = rpcResponse.getResult().getResponse();
            if (response.getCode() != null) {
                BinanceDexApiError binanceDexApiError = new BinanceDexApiError();
                binanceDexApiError.setCode(response.getCode());
                binanceDexApiError.setMessage(response.getLog());
                throw new BinanceDexApiException(binanceDexApiError);
            }
            String proposalJson = new String(response.getValue());
            return EncodeUtils.toObjectFromJsonString(proposalJson, Proposal.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<TransactionMetadata> transfer(Transfer transfer, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        synchronized (wallet) {
            wallet.ensureWalletIsReady(this);
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            String requestPayload = "0x" + assembler.buildTransferPayload(transfer);
            if (sync) {
                return syncBroadcast(requestPayload, wallet);
            } else {
                return asyncBroadcast(requestPayload, wallet);
            }
        }
    }

    @Override
    public List<TransactionMetadata> multiTransfer(MultiTransfer multiTransfer, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        synchronized (wallet) {
            wallet.ensureWalletIsReady(this);
            TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
            String requestPayload = "0x" + assembler.buildMultiTransferPayload(multiTransfer);
            if (sync) {
                return syncBroadcast(requestPayload, wallet);
            } else {
                return asyncBroadcast(requestPayload, wallet);
            }
        }
    }

    protected Infos convert(NodeInfos nodeInfos) {
        Infos infos = new Infos();

        infos.setNodeInfo(nodeInfos.getNodeInfo());
        infos.setSyncInfo(nodeInfos.getSyncInfo());

        NodeInfos.ValidatorInfo source = nodeInfos.getValidatorInfo();
        ValidatorInfo target = new ValidatorInfo();

        target.setAddress(source.getAddress());
        target.setVotingPower(source.getVotingPower());
        target.setPubKey(convertKey(source.getPubKey().getValue()));

        infos.setValidatorInfo(target);

        return infos;
    }


    protected Account convert(AppAccount appAccount) {
        Account account = new Account();
        account.setAccountNumber(new Long(appAccount.getBase().getAccountNumber()).intValue());
        account.setAddress(Crypto.encodeAddress(hrp, appAccount.getBase().getAddress().toByteArray()));

        byte[] bytes = appAccount.getBase().getPublicKey().toByteArray();

        account.setPublicKey(convertKey(bytes));
        account.setSequence(appAccount.getBase().getSequence());

        Map<String, Long> free = appAccount.getBase().getCoinsList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));
        Map<String, Long> locked = appAccount.getLockedList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));
        Map<String, Long> frozen = appAccount.getFrozenList().stream().collect(Collectors.toMap(Token::getDenom, Token::getAmount));

        Set<String> symbolSet = Sets.union(Sets.union(free.keySet(), locked.keySet()), frozen.keySet());
        List<Balance> balanceList = symbolSet.stream()
                .map(symbol -> new Balance(symbol, free.getOrDefault(symbol, 0L).toString(), locked.getOrDefault(symbol, 0L).toString(), frozen.getOrDefault(symbol, 0L).toString()))
                .collect(Collectors.toList());

        account.setBalances(balanceList);
        return account;
    }

    protected com.binance.dex.api.client.domain.Token convert(TokenInfo tokenInfo) {
        com.binance.dex.api.client.domain.Token token = new com.binance.dex.api.client.domain.Token();
        token.setName(tokenInfo.getName());
        token.setOriginalSymbol(tokenInfo.getOriginalSymbol());
        token.setSymbol(tokenInfo.getSymbol());
        token.setOwner(Crypto.encodeAddress(hrp, tokenInfo.getOwner().toByteArray()));
        token.setTotalSupply(tokenInfo.getTotalSupply());
        token.setMintable(tokenInfo.getMintable());
        return token;
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

    protected List<Integer> convertKey(byte[] bytes) {
        List<Integer> publicKey = Lists.newArrayList();
        for (int i = 2; i < bytes.length; i++) {
            publicKey.add((int) bytes[i]);
        }
        return publicKey;
    }

    protected int getStartIndex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x80) {
                return i + 5;
            }
        }
        return -1;
    }

    protected void checkRpcResult(JsonRpcResponse<?> rpcResponse) {
        if (null != rpcResponse.getError() && null != rpcResponse.getError().getCode() && rpcResponse.getError().getCode().intValue() != 0) {
            throw new RuntimeException(rpcResponse.getError().toString());
        }
    }
}
