package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.amino.AminoSerializable;
import com.binance.dex.api.client.encoding.amino.InternalAmino;
import com.binance.dex.api.client.encoding.amino.WireType;
import com.binance.dex.api.proto.StdSignature;
import com.binance.dex.api.proto.StdTx;
import com.binance.dex.api.proto.TransferTokenOwnershipMsg;
import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.ByteString;
import okhttp3.RequestBody;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Assemble a transaction message body.
 * https://testnet-dex.binance.org/doc/encoding.html
 */
public class TransactionRequestAssembler {
    private static final okhttp3.MediaType MEDIA_TYPE = okhttp3.MediaType.parse("text/plain; charset=utf-8");
    private static final BigDecimal MULTIPLY_FACTOR = BigDecimal.valueOf(1e8);
    private static final BigDecimal MAX_NUMBER = new BigDecimal(Long.MAX_VALUE);

    private Wallet wallet;
    private TransactionOption options;

    private final Amino amino;

    public TransactionRequestAssembler(Wallet wallet, TransactionOption options) {
        this.wallet = wallet;
        this.options = options;
        this.amino = InternalAmino.get();
    }

    public static long doubleToLong(String d) {
        BigDecimal encodeValue = new BigDecimal(d).multiply(MULTIPLY_FACTOR);
        if (encodeValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(d + " is less or equal to zero.");
        }
        if (encodeValue.compareTo(MAX_NUMBER) > 0) {
            throw new IllegalArgumentException(d + " is too large.");
        }
        return encodeValue.longValue();

    }

    public static String longToDouble(long l) {
        return BigDecimal.valueOf(l).divide(MULTIPLY_FACTOR).toString();
    }

    @VisibleForTesting
    byte[] signTx(BinanceDexTransactionMessage msg) throws IOException, NoSuchAlgorithmException {
        return sign(assembleMessage4Sign(msg));
    }

    private <T extends BinanceDexTransactionMessage> BinanceDexTransactionMessage assembleMessage4Sign(T t) {
        if (t.useAminoJson()) {
            if (WireType.isRegistered(t.getClass())) {
                return new TransactionMessageWithType<T>(WireType.getRegisteredTypeName(t.getClass()), t);
            } else {
                throw new IllegalStateException("Class " + t.getClass().getCanonicalName() + " has not been registered into amino");
            }
        } else {
            return t;
        }
    }

    @VisibleForTesting
    byte[] sign(BinanceDexTransactionMessage msg)
            throws NoSuchAlgorithmException, IOException {
        SignData sd = new SignData();
        sd.setChainId(wallet.getChainId());
        sd.setAccountNumber(String.valueOf(wallet.getAccountNumber()));
        sd.setSequence(String.valueOf(wallet.getSequence()));
        sd.setMsgs(new BinanceDexTransactionMessage[]{msg});

        sd.setMemo(options.getMemo());
        sd.setSource(String.valueOf(options.getSource()));
        sd.setData(options.getData());
        if (wallet.getEcKey() == null && wallet.getLedgerKey() != null) {
            return Crypto.sign(EncodeUtils.toJsonEncodeBytes(sd), wallet.getLedgerKey());
        }

        return Crypto.sign(EncodeUtils.toJsonEncodeBytes(sd), wallet.getEcKey());
    }

    @VisibleForTesting
    byte[] encodeSignature(byte[] signatureBytes) throws IOException {
        StdSignature stdSignature = StdSignature.newBuilder().setPubKey(ByteString.copyFrom(wallet.getPubKeyForSign()))
                .setSignature(ByteString.copyFrom(signatureBytes))
                .setAccountNumber(wallet.getAccountNumber())
                .setSequence(wallet.getSequence())
                .build();

        return EncodeUtils.aminoWrap(
                stdSignature.toByteArray(), MessageType.StdSignature.getTypePrefixBytes(), false);
    }

    @VisibleForTesting
    byte[] encodeStdTx(byte[] msg, byte[] signature) throws IOException {
        StdTx.Builder stdTxBuilder = StdTx.newBuilder()
                .addMsgs(ByteString.copyFrom(msg))
                .addSignatures(ByteString.copyFrom(signature))
                .setMemo(options.getMemo())
                .setSource(options.getSource());
        if (options.getData() != null) {
            stdTxBuilder = stdTxBuilder.setData(ByteString.copyFrom(options.getData()));
        }
        StdTx stdTx = stdTxBuilder.build();
        return EncodeUtils.aminoWrap(stdTx.toByteArray(), MessageType.StdTx.getTypePrefixBytes(), true);
    }

    public RequestBody createRequestBody(String payload) {
        return RequestBody.create(MEDIA_TYPE, payload);
    }

    private String generateOrderId() {
        return EncodeUtils.bytesToHex(wallet.getAddressBytes()).toUpperCase() + "-" + (wallet.getSequence() + 1);
    }

    @VisibleForTesting
    NewOrderMessage createNewOrderMessage(
            com.binance.dex.api.client.domain.broadcast.NewOrder newOrder) {
        return NewOrderMessage.newBuilder()
                .setId(generateOrderId())
                .setOrderType(newOrder.getOrderType())
                .setPrice(newOrder.getPrice())
                .setQuantity(newOrder.getQuantity())
                .setSender(wallet.getAddress())
                .setSide(newOrder.getSide())
                .setSymbol(newOrder.getSymbol())
                .setTimeInForce(newOrder.getTimeInForce())
                .build();
    }

    @VisibleForTesting
    VoteMessage createVoteMessage(Vote vote) {
        VoteMessage voteMessage = new VoteMessage();
        voteMessage.setProposalId(vote.getProposalId());
        voteMessage.setOption(vote.getOption());
        voteMessage.setVoter(wallet.getAddress());
        return voteMessage;
    }

    @VisibleForTesting
    SideVoteMessage createSideVoteMessage(SideVote vote) {
        SideVoteMessage voteMessage = new SideVoteMessage();
        voteMessage.setProposalId(vote.getProposalId());
        voteMessage.setOption(vote.getOption());
        voteMessage.setVoter(wallet.getAddress());
        voteMessage.setSideChainId(vote.getSideChainId());
        return voteMessage;
    }

    @VisibleForTesting
    byte[] encodeNewOrderMessage(NewOrderMessage newOrder)
            throws IOException {
        com.binance.dex.api.proto.NewOrder proto = com.binance.dex.api.proto.NewOrder.newBuilder()
                .setSender(ByteString.copyFrom(wallet.getAddressBytes()))
                .setId(newOrder.getId())
                .setSymbol(newOrder.getSymbol())
                .setOrdertype(newOrder.getOrderType().toValue())
                .setSide(newOrder.getSide().toValue())
                .setPrice(newOrder.getPrice())
                .setQuantity(newOrder.getQuantity())
                .setTimeinforce(newOrder.getTimeInForce().toValue())
                .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.NewOrder.getTypePrefixBytes(), false);
    }

    @VisibleForTesting
    byte[] encodeVoteMessage(VoteMessage voteMessage)
            throws IOException {
        com.binance.dex.api.proto.Vote proto = com.binance.dex.api.proto.Vote.newBuilder()
                .setVoter(ByteString.copyFrom(wallet.getAddressBytes()))
                .setProposalId(voteMessage.getProposalId())
                .setOption(voteMessage.getOption())
                .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.Vote.getTypePrefixBytes(), false);
    }

    @VisibleForTesting
    byte[] encodeSideVoteMessage(SideVoteMessage voteMessage)
            throws IOException {
        com.binance.dex.api.proto.SideVote proto = com.binance.dex.api.proto.SideVote.newBuilder()
                .setVoter(ByteString.copyFrom(wallet.getAddressBytes()))
                .setProposalId(voteMessage.getProposalId())
                .setOption(voteMessage.getOption())
                .setSideChainId(voteMessage.getSideChainId())
                .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.SideVote.getTypePrefixBytes(), false);
    }

    public RequestBody buildNewOrder(com.binance.dex.api.client.domain.broadcast.NewOrder newOrder)
            throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildNewOrderPayload(newOrder));
    }

    public String buildNewOrderPayload(com.binance.dex.api.client.domain.broadcast.NewOrder newOrder)
            throws IOException, NoSuchAlgorithmException {
        NewOrderMessage msgBean = createNewOrderMessage(newOrder);
        byte[] msg = encodeNewOrderMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    public RequestBody buildVote(Vote vote) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildVotePayload(vote));
    }

    public String buildVotePayload(Vote vote) throws IOException, NoSuchAlgorithmException {
        VoteMessage msgBean = createVoteMessage(vote);
        byte[] msg = encodeVoteMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    public RequestBody buildSideVote(SideVote vote) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildSideVotePayload(vote));
    }

    public String buildSideVotePayload(SideVote vote) throws IOException, NoSuchAlgorithmException {
        SideVoteMessage msgBean = createSideVoteMessage(vote);
        byte[] msg = encodeSideVoteMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    CancelOrderMessage createCancelOrderMessage(
            com.binance.dex.api.client.domain.broadcast.CancelOrder cancelOrder) {
        CancelOrderMessage bean =
                new CancelOrderMessage();
        bean.setRefId(cancelOrder.getRefId());
        bean.setSymbol(cancelOrder.getSymbol());
        bean.setSender(wallet.getAddress());
        return bean;
    }

    @VisibleForTesting
    byte[] encodeCancelOrderMessage(CancelOrderMessage cancelOrder)
            throws IOException {
        com.binance.dex.api.proto.CancelOrder proto = com.binance.dex.api.proto.CancelOrder.newBuilder()
                .setSender(ByteString.copyFrom(wallet.getAddressBytes()))
                .setSymbol(cancelOrder.getSymbol())
                .setRefid(cancelOrder.getRefId())
                .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.CancelOrder.getTypePrefixBytes(), false);
    }

    public RequestBody buildCancelOrder(com.binance.dex.api.client.domain.broadcast.CancelOrder cancelOrder)
            throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildCancelOrderPayload(cancelOrder));
    }

    public String buildCancelOrderPayload(com.binance.dex.api.client.domain.broadcast.CancelOrder cancelOrder)
            throws IOException, NoSuchAlgorithmException {
        CancelOrderMessage msgBean = createCancelOrderMessage(cancelOrder);
        byte[] msg = encodeCancelOrderMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    TransferMessage createTransferMessage(Transfer transfer) {
        Token token = new Token();
        token.setDenom(transfer.getCoin());
        token.setAmount(doubleToLong(transfer.getAmount()));
        List<Token> coins = Collections.singletonList(token);

        InputOutput input = new InputOutput();
        input.setAddress(transfer.getFromAddress());
        input.setCoins(coins);
        InputOutput output = new InputOutput();
        output.setAddress(transfer.getToAddress());
        output.setCoins(coins);

        TransferMessage msgBean = new TransferMessage();
        msgBean.setInputs(Collections.singletonList(input));
        msgBean.setOutputs(Collections.singletonList(output));
        return msgBean;
    }

    private com.binance.dex.api.proto.Send.Input toProtoInput(InputOutput input) {
        byte[] address = Crypto.decodeAddress(input.getAddress());
        com.binance.dex.api.proto.Send.Input.Builder builder =
                com.binance.dex.api.proto.Send.Input.newBuilder().setAddress(ByteString.copyFrom(address));

        for (Token coin : input.getCoins()) {
            com.binance.dex.api.proto.Send.Token protCoin =
                    com.binance.dex.api.proto.Send.Token.newBuilder().setAmount(coin.getAmount())
                            .setDenom(coin.getDenom()).build();
            builder.addCoins(protCoin);
        }
        return builder.build();
    }

    private com.binance.dex.api.proto.Send.Output toProtoOutput(InputOutput output) {
        byte[] address = Crypto.decodeAddress(output.getAddress());
        com.binance.dex.api.proto.Send.Output.Builder builder =
                com.binance.dex.api.proto.Send.Output.newBuilder().setAddress(ByteString.copyFrom(address));

        for (Token coin : output.getCoins()) {
            com.binance.dex.api.proto.Send.Token protCoin =
                    com.binance.dex.api.proto.Send.Token.newBuilder().setAmount(coin.getAmount())
                            .setDenom(coin.getDenom()).build();
            builder.addCoins(protCoin);
        }
        return builder.build();
    }

    @VisibleForTesting
    byte[] encodeTransferMessage(TransferMessage msg)
            throws IOException {
        com.binance.dex.api.proto.Send.Builder builder = com.binance.dex.api.proto.Send.newBuilder();
        for (InputOutput input : msg.getInputs()) {
            builder.addInputs(toProtoInput(input));
        }
        for (InputOutput output : msg.getOutputs()) {
            builder.addOutputs(toProtoOutput(output));
        }
        com.binance.dex.api.proto.Send proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.Send.getTypePrefixBytes(), false);
    }

    public RequestBody buildTransfer(Transfer transfer)
            throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildTransferPayload(transfer));
    }

    public String buildTransferPayload(Transfer transfer)
            throws IOException, NoSuchAlgorithmException {
        TransferMessage msgBean = createTransferMessage(transfer);
        byte[] msg = encodeTransferMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    TokenFreezeMessage createTokenFreezeMessage(TokenFreeze freeze) {
        TokenFreezeMessage msg = new TokenFreezeMessage();
        msg.setAmount(doubleToLong(freeze.getAmount()));
        msg.setFrom(wallet.getAddress());
        msg.setSymbol(freeze.getSymbol());
        return msg;
    }

    @VisibleForTesting
    byte[] encodeTokenFreezeMessage(TokenFreezeMessage freeze) throws IOException {
        byte[] address = Crypto.decodeAddress(freeze.getFrom());
        com.binance.dex.api.proto.TokenFreeze proto =
                com.binance.dex.api.proto.TokenFreeze.newBuilder().setFrom(ByteString.copyFrom(address))
                        .setAmount(freeze.getAmount())
                        .setSymbol(freeze.getSymbol())
                        .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.TokenFreeze.getTypePrefixBytes(), false);
    }

    public RequestBody buildTokenFreeze(TokenFreeze freeze)
            throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildTokenFreezePayload(freeze));
    }

    public String buildTokenFreezePayload(TokenFreeze freeze)
            throws IOException, NoSuchAlgorithmException {
        TokenFreezeMessage msgBean = createTokenFreezeMessage(freeze);
        byte[] msg = encodeTokenFreezeMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    TokenUnfreezeMessage createTokenUnfreezeMessage(TokenUnfreeze unfreeze) {
        TokenUnfreezeMessage msg = new TokenUnfreezeMessage();
        msg.setAmount(doubleToLong(unfreeze.getAmount()));
        msg.setFrom(wallet.getAddress());
        msg.setSymbol(unfreeze.getSymbol());
        return msg;
    }

    @VisibleForTesting
    byte[] encodeTokenUnfreezeMessage(TokenUnfreezeMessage unfreeze) throws IOException {
        byte[] address = Crypto.decodeAddress(unfreeze.getFrom());
        com.binance.dex.api.proto.TokenUnfreeze proto =
                com.binance.dex.api.proto.TokenUnfreeze.newBuilder().setFrom(ByteString.copyFrom(address))
                        .setAmount(unfreeze.getAmount())
                        .setSymbol(unfreeze.getSymbol())
                        .build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.TokenUnfreeze.getTypePrefixBytes(), false);
    }

    public RequestBody buildTokenUnfreeze(TokenUnfreeze unfreeze)
            throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildTokenUnfreezePayload(unfreeze));
    }

    public String buildTokenUnfreezePayload(TokenUnfreeze unfreeze)
            throws IOException, NoSuchAlgorithmException {
        TokenUnfreezeMessage msgBean = createTokenUnfreezeMessage(unfreeze);
        byte[] msg = encodeTokenUnfreezeMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    TransferMessage createMultiTransferMessage(MultiTransfer multiTransfer) {
        Map<String, Long> inputsCoins = new TreeMap<String, Long>();
        ArrayList<InputOutput> outputs = new ArrayList<>();
        for (Output o : multiTransfer.getOutputs()) {
            InputOutput out = new InputOutput();
            out.setAddress(o.getAddress());
            List<Token> tokens = new ArrayList<>(o.getTokens().size());
            for (OutputToken t : o.getTokens()) {
                Token token = new Token();
                token.setDenom(t.getCoin());
                long amount = doubleToLong(t.getAmount());
                token.setAmount(amount);
                tokens.add(token);

                long inputSum = inputsCoins.getOrDefault(t.getCoin(), 0L);
                long newSum = inputSum + amount;
                if (newSum < 0L) {
                    throw new IllegalArgumentException("Transfer amount overflow");
                }
                inputsCoins.put(t.getCoin(), newSum);
            }
            tokens.sort(Comparator.comparing(Token::getDenom));
            out.setCoins(tokens);
            outputs.add(out);
        }

        InputOutput input = new InputOutput();
        input.setAddress(multiTransfer.getFromAddress());
        List<Token> inputTokens = new ArrayList<>(inputsCoins.size());
        for (String coin : inputsCoins.keySet()) {
            Token token = new Token();
            token.setDenom(coin);
            token.setAmount(inputsCoins.get(coin));
            inputTokens.add(token);
        }
        input.setCoins(inputTokens);

        TransferMessage msgBean = new TransferMessage();
        msgBean.setInputs(Collections.singletonList(input));
        msgBean.setOutputs(outputs);
        return msgBean;
    }

    public RequestBody buildMultiTransfer(MultiTransfer multiTransfer) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildMultiTransferPayload(multiTransfer));
    }

    public String buildMultiTransferPayload(MultiTransfer multiTransfer) throws IOException, NoSuchAlgorithmException {
        TransferMessage msgBean = createMultiTransferMessage(multiTransfer);
        byte[] msg = encodeTransferMessage(msgBean);
        byte[] signature = encodeSignature(sign(msgBean));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    public RequestBody buildHtlt(HtltReq htltReq) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildHtltPayload(htltReq));
    }

    public String buildHtltPayload(HtltReq htltReq) throws IOException, NoSuchAlgorithmException {
        htltReq.setRecipientOtherChain(Optional.ofNullable(htltReq.getRecipientOtherChain()).orElse(""));
        htltReq.setSenderOtherChain(Optional.ofNullable(htltReq.getSenderOtherChain()).orElse(""));
        HtltMessage htltMessage = createHtltMessage(htltReq);
        byte[] msg = encodeHtltMessage(htltMessage);
        byte[] signature = encodeSignature(sign(htltMessage));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    public HtltMessage createHtltMessage(HtltReq htltReq) {
        HtltMessage message = new HtltMessage();
        message.setFrom(wallet.getAddress());
        message.setTo(htltReq.getRecipient());
        message.setRecipientOtherChain(htltReq.getRecipientOtherChain());
        message.setSenderOtherChain(htltReq.getSenderOtherChain());
        message.setRandomNumberHash(htltReq.getRandomNumberHash());
        message.setTimestamp(htltReq.getTimestamp());
        message.setAmount(htltReq.getOutAmount());
        message.setExpectedIncome(htltReq.getExpectedIncome());
        message.setHeightSpan(htltReq.getHeightSpan());
        message.setCrossChain(htltReq.isCrossChain());
        return message;
    }

    @VisibleForTesting
    byte[] encodeHtltMessage(HtltMessage msg)
            throws IOException {
        byte[] address = Crypto.decodeAddress(msg.getFrom());
        byte[] toAddress = Crypto.decodeAddress(msg.getTo());
        com.binance.dex.api.proto.HashTimerLockTransferMsg.Builder builder = com.binance.dex.api.proto.HashTimerLockTransferMsg.newBuilder();
        builder.setFrom(ByteString.copyFrom(address));
        builder.setTo(ByteString.copyFrom(toAddress));
        builder.setRecipientOtherChain(msg.getRecipientOtherChain());
        builder.setSenderOtherChain(msg.getSenderOtherChain());
        builder.setRandomNumberHash(ByteString.copyFrom(msg.getRandomNumberHash()));
        builder.setTimestamp(msg.getTimestamp());
        for (Token token : msg.getAmount()) {
            builder.addAmount(com.binance.dex.api.proto.Token.newBuilder().setAmount(token.getAmount()).setDenom(token.getDenom()));
        }
        builder.setExpectedIncome(msg.getExpectedIncome());
        builder.setHeightSpan(msg.getHeightSpan());
        builder.setCrossChain(msg.getCrossChain());
        com.binance.dex.api.proto.HashTimerLockTransferMsg proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.HashTimerLockTransferMsg.getTypePrefixBytes(), false);
    }

    public RequestBody buildDepositHtlt(String swapId, List<Token> amount) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildDepositHtltPayload(swapId, amount));
    }

    public String buildDepositHtltPayload(String swapId, List<Token> amount) throws IOException, NoSuchAlgorithmException {
        DepositHtltMessage depositHtltMessage = createDepositHtltMessage(swapId, amount);
        byte[] msg = encodeDepositHtltMessage(depositHtltMessage);
        byte[] signature = encodeSignature(sign(depositHtltMessage));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    public DepositHtltMessage createDepositHtltMessage(String swapId, List<Token> amount) {
        DepositHtltMessage message = new DepositHtltMessage();
        message.setFrom(wallet.getAddress());
        message.setSwapId(swapId);
        message.setAmount(amount);
        return message;
    }

    @VisibleForTesting
    public byte[] encodeDepositHtltMessage(DepositHtltMessage msg)
            throws IOException {
        byte[] address = Crypto.decodeAddress(msg.getFrom());
        com.binance.dex.api.proto.DepositHashTimerLockMsg.Builder builder = com.binance.dex.api.proto.DepositHashTimerLockMsg.newBuilder();
        builder.setFrom(ByteString.copyFrom(address));
        builder.setSwapId(ByteString.copyFrom(Hex.decode(msg.getSwapId())));
        for (Token token : msg.getAmount()) {
            builder.addAmount(com.binance.dex.api.proto.Token.newBuilder().setAmount(token.getAmount()).setDenom(token.getDenom()));
        }
        com.binance.dex.api.proto.DepositHashTimerLockMsg proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.DepositHashTimerLockMsg.getTypePrefixBytes(), false);
    }

    public RequestBody buildClaimHtlt(String swapId, byte[] randomNumber) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildClaimHtltPayload(swapId, randomNumber));
    }

    public String buildClaimHtltPayload(String swapId, byte[] randomNumber) throws IOException, NoSuchAlgorithmException {
        ClaimHtltMessage claimHtltMessage = new ClaimHtltMessage();
        claimHtltMessage.setFrom(wallet.getAddress());
        claimHtltMessage.setRandomNumber(randomNumber);
        claimHtltMessage.setSwapId(swapId);
        byte[] msg = encodeClaimHtltMessage(claimHtltMessage);
        byte[] signature = encodeSignature(sign(claimHtltMessage));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    public byte[] encodeClaimHtltMessage(ClaimHtltMessage msg) throws IOException {
        byte[] address = Crypto.decodeAddress(msg.getFrom());
        com.binance.dex.api.proto.ClaimHashTimerLockMsg.Builder builder = com.binance.dex.api.proto.ClaimHashTimerLockMsg.newBuilder();
        builder.setFrom(ByteString.copyFrom(address));
        builder.setSwapId(ByteString.copyFrom(Hex.decode(msg.getSwapId())));
        builder.setRandomNumber(ByteString.copyFrom(msg.getRandomNumber()));
        com.binance.dex.api.proto.ClaimHashTimerLockMsg proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.ClaimHashTimerLockMsg.getTypePrefixBytes(), false);
    }

    public RequestBody buildRefundHtlt(String swapId) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildRefundHtltPayload(swapId));
    }

    public String buildRefundHtltPayload(String swapId) throws IOException, NoSuchAlgorithmException {
        RefundHtltMessage refundHtltMessage = new RefundHtltMessage();
        refundHtltMessage.setFrom(wallet.getAddress());
        refundHtltMessage.setSwapId(swapId);
        byte[] msg = encodeRefundHtltMessage(refundHtltMessage);
        byte[] signature = encodeSignature(sign(refundHtltMessage));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    public byte[] encodeRefundHtltMessage(RefundHtltMessage msg) throws IOException {
        byte[] address = Crypto.decodeAddress(msg.getFrom());
        com.binance.dex.api.proto.RefundHashTimerLockMsg.Builder builder = com.binance.dex.api.proto.RefundHashTimerLockMsg.newBuilder();
        builder.setFrom(ByteString.copyFrom(address));
        builder.setSwapId(ByteString.copyFrom(Hex.decode(msg.getSwapId())));
        com.binance.dex.api.proto.RefundHashTimerLockMsg proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.RefundHashTimerLockMsg.getTypePrefixBytes(), false);
    }

    public RequestBody buildTransferTokenOwnership(String symbol, String newOwner) throws IOException, NoSuchAlgorithmException {
        return createRequestBody(buildTransferTokenOwnershipPayload(symbol, newOwner));
    }

    public String buildTransferTokenOwnershipPayload(String symbol, String newOwner) throws IOException, NoSuchAlgorithmException {
        TransferTokenOwnershipMessage message = new TransferTokenOwnershipMessage();
        message.setFrom(wallet.getAddress());
        message.setSymbol(symbol);
        message.setNewOwner(newOwner);
        byte[] msg = encodeTransferTokenOwnershipMessage(message);
        byte[] signature = encodeSignature(sign(message));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

    @VisibleForTesting
    public byte[] encodeTransferTokenOwnershipMessage(TransferTokenOwnershipMessage msg) throws IOException {
        byte[] address = Crypto.decodeAddress(msg.getFrom());
        byte[] newOwnerB = Crypto.decodeAddress(msg.getNewOwner());
        TransferTokenOwnershipMsg.Builder builder = TransferTokenOwnershipMsg.newBuilder();
        builder.setFrom(ByteString.copyFrom(address));
        builder.setSymbol(msg.getSymbol());
        builder.setNewOwner(ByteString.copyFrom(newOwnerB));
        TransferTokenOwnershipMsg proto = builder.build();
        return EncodeUtils.aminoWrap(proto.toByteArray(), MessageType.TransferTokenOwnership.getTypePrefixBytes(), false);
    }


    /**
     * Used for amino serializable message
     */
    public String buildTxPayload(BinanceDexTransactionMessage message) throws IOException, NoSuchAlgorithmException {
        if (!AminoSerializable.class.isAssignableFrom(message.getClass())) {
            throw new IllegalArgumentException("Class " + message.getClass() + " should also implement AminoSerializable to support amino encoding");
        }

        byte[] typePrefix = WireType.getTypePrefix(message.getClass());
        byte[] msg = amino.encode(((AminoSerializable) message), typePrefix, false);
        byte[] signature = encodeSignature(signTx(message));
        byte[] stdTx = encodeStdTx(msg, signature);
        return EncodeUtils.bytesToHex(stdTx);
    }

}
