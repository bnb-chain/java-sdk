package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.bridge.TransferIn;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.binance.dex.api.client.encoding.message.bridge.*;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class NodeTxDelegateBridge extends NodeTx {

    public NodeTxDelegateBridge(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        super(binanceDexNodeApi, hrp, valHrp);
    }

    public List<TransactionMetadata> claim(int chainId, byte[] payload, long sequence, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        ClaimMsgMessage message = new ClaimMsgMessage();
        message.setChainId(chainId);
        message.setSequence(sequence);
        message.setPayload(payload);
        message.setValidatorAddress(Bech32AddressValue.fromBech32String(wallet.getAddress()));
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> transferIn(long sequence, TransferIn transferIn, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        TransferInClaimMessage message = convert(transferIn);
        String claim = EncodeUtils.getObjectMapper().writeValueAsString(message);
        ClaimMsgMessage claimMsg = new ClaimMsgMessage(ClaimTypes.ClaimTypeTransferIn, sequence, claim.getBytes(), Bech32AddressValue.fromBech32String(wallet.getAddress()));

        return broadcast(claimMsg, wallet, options, sync);
    }

    public List<TransactionMetadata> transferOut(String toAddress, Token amount, long expireTimeInSeconds, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        TransferOutMsgMessage message = new TransferOutMsgMessage();
        message.setFrom(Bech32AddressValue.fromBech32String(wallet.getAddress()));
        message.setToAddress(EthAddressValue.from(toAddress));
        message.setAmount(convert(amount));
        message.setExpireTime(expireTimeInSeconds);

        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> bind(String symbol, long amount, String contractAddress, int contractDecimal, long expireTimeInSeconds, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        BindMsgMessage message = new BindMsgMessage();
        message.setFrom(Bech32AddressValue.fromBech32String(wallet.getAddress()));
        message.setSymbol(symbol);
        message.setAmount(amount);
        message.setContractAddress(EthAddressValue.from(contractAddress));
        message.setContractDecimal(contractDecimal);
        message.setExpireTime(expireTimeInSeconds);

        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> unBind(String symbol, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        UnbindMsgMessage message = new UnbindMsgMessage();
        message.setFrom(Bech32AddressValue.fromBech32String(wallet.getAddress()));
        message.setSymbol(symbol);

        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> updateTransferOut(long sequence, String refundAddress, Token amount, int refundReason, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        UpdateTransferOutClaimMessage message = new UpdateTransferOutClaimMessage();
        message.setAmount(convert(amount));
        message.setRefundAddress(Bech32AddressValue.fromBech32String(refundAddress));
        message.setRefundReason(refundReason);

        ClaimMsgMessage claimMsg = new ClaimMsgMessage(ClaimTypes.ClaimTypeUpdateTransferOut, sequence,
                EncodeUtils.getObjectMapper().writeValueAsString(message).getBytes(), Bech32AddressValue.fromBech32String(wallet.getAddress()));

        return broadcast(claimMsg, wallet, options, sync);
    }

    public List<TransactionMetadata> updateBind(long sequence, String symbol, String contractAddress, int status, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        UpdateBindClaimMessage message  = new UpdateBindClaimMessage();
        message.setStatus(status);
        message.setSymbol(symbol);
        message.setContractAddress(EthAddressValue.from(contractAddress));

        ClaimMsgMessage claimMsg = new ClaimMsgMessage(ClaimTypes.ClaimTypeUpdateBind, sequence,
                EncodeUtils.getObjectMapper().writeValueAsString(message).getBytes(), Bech32AddressValue.fromBech32String(wallet.getAddress()));

        return broadcast(claimMsg, wallet, options, sync);
    }

    private CoinValue convert(Token token){
        CoinValue value = new CoinValue();
        value.setDenom(token.getDenom());
        value.setAmount(token.getAmount());
        return value;
    }

    private TransferInClaimMessage convert(TransferIn transferIn){
        TransferInClaimMessage message = new TransferInClaimMessage();
        message.setContractAddress(transferIn.getContractAddress());
        message.setRefundAddresses(transferIn.getRefundAddresses());
        message.setReceiverAddresses(transferIn.getReceiverAddresses());
        message.setAmounts(transferIn.getAmounts());
        message.setSymbol(transferIn.getSymbol());

        CoinValue coinValue = new CoinValue();
        if (transferIn.getRelayFee() != null){
            coinValue.setDenom(transferIn.getRelayFee().getDenom());
            coinValue.setAmount(transferIn.getRelayFee().getAmount());
        }
        message.setRelayFee(coinValue);

        message.setExpireTime(transferIn.getExpireTime());

        return message;
    }

}
