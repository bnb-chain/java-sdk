package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.broadcast.Burn;
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.CreateValidator;
import com.binance.dex.api.client.domain.broadcast.RemoveValidator;
import com.binance.dex.api.client.domain.broadcast.Deposit;
import com.binance.dex.api.client.domain.broadcast.Issue;
import com.binance.dex.api.client.domain.broadcast.Mint;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.SetAccountFlag;
import com.binance.dex.api.client.domain.broadcast.SubmitProposal;
import com.binance.dex.api.client.domain.broadcast.TokenFreeze;
import com.binance.dex.api.client.domain.broadcast.TokenUnfreeze;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.Vote;
import com.binance.dex.api.client.domain.jsonrpc.TxResult;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.InputOutput;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.proto.*;
import com.binance.dex.api.proto.TimeLock;
import com.binance.dex.api.proto.TimeRelock;
import com.binance.dex.api.proto.TimeUnlock;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.util.encoders.Hex;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionConverter {

    private String hrp;

    public TransactionConverter(String hrp){
        this.hrp = hrp;
    }

    public List<Transaction> convert(com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult.Transaction txMessage) {
        try {
            byte[] value = txMessage.getTx();
            int startIndex = getStartIndex(value);
            byte[] array = new byte[value.length - startIndex];
            System.arraycopy(value, startIndex, array, 0, array.length);
            StdTx stdTx = StdTx.parseFrom(array);
            StdSignature stdSignature = StdSignature.parseFrom(stdTx.getSignatures(0));
            return stdTx.getMsgsList().stream()
                    .map(byteString -> {
                        byte[] bytes = byteString.toByteArray();
                        Transaction transaction = convert(bytes);
                        if (null == transaction) {
                            return null;
                        }
                        transaction.setHash(txMessage.getHash());
                        transaction.setHeight(txMessage.getHeight());
                        transaction.setCode(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getCode).orElse(null));
                        transaction.setLog(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getLog).orElse(null));
                        transaction.setTags(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getTags).orElse(null));
                        transaction.setEvents(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getEvents).orElse(null));
                        transaction.setMemo(stdTx.getMemo());
                        transaction.setResultData(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getData).orElse(null));
                        transaction.setSource(stdTx.getSource());
                        transaction.setSequence(stdSignature.getSequence());
                        return transaction;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public int getStartIndex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x80) {
                return i + 5;
            }
        }
        return -1;
    }

    public Transaction convert(byte[] bytes) {
        try {
            MessageType messageType = MessageType.getMessageType(bytes);
            if (null == messageType) {
                return null;
            }
            switch (messageType) {
                case Send:
                    return convertTransfer(bytes);
                case NewOrder:
                    return convertNewOrder(bytes);
                case CancelOrder:
                    return convertCancelOrder(bytes);
                case TokenFreeze:
                    return convertTokenFreeze(bytes);
                case TokenUnfreeze:
                    return convertTokenUnfreeze(bytes);
                case Vote:
                    return convertVote(bytes);
                case Issue:
                    return convertIssue(bytes);
                case Burn:
                    return convertBurn(bytes);
                case Mint:
                    return convertMint(bytes);
                case SubmitProposal:
                    return convertSubmitProposal(bytes);
                case Deposit:
                    return convertDeposit(bytes);
                case CreateValidator:
                    return convertCreateValidator(bytes);
                case RemoveValidator:
                    return convertRemoveValidator(bytes);
                case Listing:
                    return convertListing(bytes);
                case TimeLock:
                    return convertTimeLock(bytes);
                case TimeUnlock:
                    return convertTimeUnlock(bytes);
                case TimeRelock:
                    return convertTimeRelock(bytes);
                case SetAccountFlag:
                    return convertSetAccountFlag(bytes);
                case HashTimerLockTransferMsg:
                    return convertHashTimerLockTransfer(bytes);
                case DepositHashTimerLockMsg:
                    return convertDepositHashTimerLock(bytes);
                case ClaimHashTimerLockMsg:
                    return convertClaimHashTimerLock(bytes);
                case RefundHashTimerLockMsg:
                    return convertRefundHashTimerLock(bytes);

            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction convertRefundHashTimerLock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        RefundHashTimerLockMsg refundHtlMsg = RefundHashTimerLockMsg.parseFrom(array);

        RefundHashTimerLock refundHashTimerLock = new RefundHashTimerLock();
        refundHashTimerLock.setFrom(Crypto.encodeAddress(hrp,refundHtlMsg.getFrom().toByteArray()));
        refundHashTimerLock.setSwapID(Hex.toHexString(refundHtlMsg.getSwapId().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.REFUND_HTL);
        transaction.setRealTx(refundHashTimerLock);
        return transaction;
    }

    private Transaction convertClaimHashTimerLock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        ClaimHashTimerLockMsg claimHtlMsg = ClaimHashTimerLockMsg.parseFrom(array);

        ClaimHashTimerLock claimHashTimerLock = new ClaimHashTimerLock();
        claimHashTimerLock.setFrom(Crypto.encodeAddress(hrp,claimHtlMsg.getFrom().toByteArray()));
        claimHashTimerLock.setSwapID(Hex.toHexString(claimHtlMsg.getSwapId().toByteArray()));
        claimHashTimerLock.setRandomNumber(Hex.toHexString(claimHtlMsg.getRandomNumber().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.CLAIM_HTL);
        transaction.setRealTx(claimHashTimerLock);
        return transaction;
    }

    private Transaction convertDepositHashTimerLock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        DepositHashTimerLockMsg depositHtlMsg = DepositHashTimerLockMsg.parseFrom(array);

        DepositHashTimerLock depositHashTimerLock = new DepositHashTimerLock();
        depositHashTimerLock.setFrom(Crypto.encodeAddress(hrp,depositHtlMsg.getFrom().toByteArray()));
        depositHashTimerLock.setAmount(depositHtlMsg.getAmountList().stream().map(Token::of).collect(Collectors.toList()));
        depositHashTimerLock.setSwapID(Hex.toHexString(depositHtlMsg.getSwapId().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.DEPOSIT_HTL);
        transaction.setRealTx(depositHashTimerLock);
        return transaction;
    }


    private Transaction convertHashTimerLockTransfer(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        HashTimerLockTransferMsg htlTransferMsg = HashTimerLockTransferMsg.parseFrom(array);

        HashTimerLockTransfer hashTimerLockTransfer = new HashTimerLockTransfer();
        hashTimerLockTransfer.setFrom(Crypto.encodeAddress(hrp,htlTransferMsg.getFrom().toByteArray()));
        hashTimerLockTransfer.setTo(Crypto.encodeAddress(hrp,htlTransferMsg.getTo().toByteArray()));
        hashTimerLockTransfer.setRecipientOtherChain(htlTransferMsg.getRecipientOtherChain());
        hashTimerLockTransfer.setSenderOtherChain(htlTransferMsg.getSenderOtherChain());
        hashTimerLockTransfer.setRandomNumberHash(Hex.toHexString(htlTransferMsg.getRandomNumberHash().toByteArray()));
        hashTimerLockTransfer.setTimestamp(htlTransferMsg.getTimestamp());
        hashTimerLockTransfer.setOutAmount(htlTransferMsg.getAmountList().stream().map(Token::of).collect(Collectors.toList()));
        hashTimerLockTransfer.setExpectedIncome(htlTransferMsg.getExpectedIncome());
        hashTimerLockTransfer.setHeightSpan(htlTransferMsg.getHeightSpan());
        hashTimerLockTransfer.setCrossChain(htlTransferMsg.getCrossChain());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.HTL_TRANSFER);
        transaction.setRealTx(hashTimerLockTransfer);
        return transaction;
    }

    private Transaction convertSetAccountFlag(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.SetAccountFlag setAccountFlag = com.binance.dex.api.proto.SetAccountFlag.parseFrom(array);
        SetAccountFlag saf = new SetAccountFlag();
        saf.setFromAddr(Crypto.encodeAddress(hrp,setAccountFlag.getFrom().toByteArray()));
        saf.setFlags(setAccountFlag.getFlags());
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SetAccountFlag);
        transaction.setRealTx(saf);
        return transaction;
    }

    private Transaction convertTimeRelock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        TimeRelock timeRelock = TimeRelock.parseFrom(array);
        com.binance.dex.api.client.domain.broadcast.TimeRelock trl = new com.binance.dex.api.client.domain.broadcast.TimeRelock();
        trl.setFromAddr(Crypto.encodeAddress(hrp,timeRelock.getFrom().toByteArray()));
        trl.setLockId(timeRelock.getTimeLockId());
        trl.setLockTime(Date.from(Instant.ofEpochSecond(timeRelock.getLockTime())));
        trl.setDescription(timeRelock.getDescription());
        List<Token> amount = timeRelock.getAmountList().stream().map(token -> {
            Token msgToken = new Token();
            msgToken.setAmount(token.getAmount());
            msgToken.setDenom(token.getDenom());
            return msgToken;
        }).collect(Collectors.toList());
        trl.setAmount(amount);
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TimeRelock);
        transaction.setRealTx(trl);
        return transaction;
    }

    private Transaction convertTimeUnlock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        TimeUnlock timeUnlock = TimeUnlock.parseFrom(array);
        com.binance.dex.api.client.domain.broadcast.TimeUnlock tul = new com.binance.dex.api.client.domain.broadcast.TimeUnlock();
        tul.setFromAddr(Crypto.encodeAddress(hrp,timeUnlock.getFrom().toByteArray()));
        tul.setLockId(timeUnlock.getTimeLockId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TimeUnlock);
        transaction.setRealTx(tul);
        return transaction;
    }

    private Transaction convertTimeLock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        TimeLock timeLock = TimeLock.parseFrom(array);
        com.binance.dex.api.client.domain.broadcast.TimeLock tl = new com.binance.dex.api.client.domain.broadcast.TimeLock();
        tl.setFromAddr(Crypto.encodeAddress(hrp,timeLock.getFrom().toByteArray()));
        tl.setDescription(timeLock.getDescription());
        tl.setLockTime(Date.from(Instant.ofEpochSecond(timeLock.getLockTime())));
        List<Token> amount = timeLock.getAmountList().stream().map(token -> {
            Token msgToken = new Token();
            msgToken.setAmount(token.getAmount());
            msgToken.setDenom(token.getDenom());
            return msgToken;
        }).collect(Collectors.toList());
        tl.setAmount(amount);
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TimeLock);
        transaction.setRealTx(tl);
        return transaction;
    }


    protected Transaction convertTransfer(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        Send send = Send.parseFrom(array);
        TransferInfo transfer = new TransferInfo();
        transfer.setInputs(send.getInputsList().stream().map(i -> {
            InputOutput input = new InputOutput();
            input.setAddress(Crypto.encodeAddress(hrp, i.getAddress().toByteArray()));
            input.setCoins(i.getCoinsList().stream()
                    .map(Token::of)
                    .collect(Collectors.toList()));
            return input;
        }).collect(Collectors.toList()));

        transfer.setOutputs(send.getOutputsList().stream().map(o -> {
            InputOutput output = new InputOutput();
            output.setAddress(Crypto.encodeAddress(hrp, o.getAddress().toByteArray()));
            output.setCoins(o.getCoinsList().stream()
                    .map(Token::of)
                    .collect(Collectors.toList()));
            return output;
        }).collect(Collectors.toList()));
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TRANSFER);
        transaction.setRealTx(transfer);
        return transaction;
    }

    protected Transaction convertNewOrder(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.NewOrder newOrderMessage = com.binance.dex.api.proto.NewOrder.parseFrom(array);

        NewOrder newOrder = new NewOrder();
        newOrder.setSender(Crypto.encodeAddress(hrp, newOrderMessage.getSender().toByteArray()));
        newOrder.setSymbol(newOrderMessage.getSymbol());
        newOrder.setOrderType(OrderType.fromValue(newOrderMessage.getOrdertype()));
        newOrder.setPrice("" + newOrderMessage.getPrice());
        newOrder.setQuantity("" + newOrderMessage.getQuantity());
        newOrder.setSide(OrderSide.fromValue(newOrderMessage.getSide()));
        newOrder.setTimeInForce(TimeInForce.fromValue(newOrderMessage.getTimeinforce()));
        newOrder.setOrderId(newOrderMessage.getId());
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.NEW_ORDER);
        transaction.setRealTx(newOrder);
        return transaction;
    }

    protected Transaction convertCancelOrder(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.CancelOrder cancelOrderOrderMessage = com.binance.dex.api.proto.CancelOrder.parseFrom(array);

        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.setSender(Crypto.encodeAddress(hrp, cancelOrderOrderMessage.getSender().toByteArray()));
        cancelOrder.setRefId(cancelOrderOrderMessage.getRefid());
        cancelOrder.setSymbol(cancelOrderOrderMessage.getSymbol());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.CANCEL_ORDER);
        transaction.setRealTx(cancelOrder);
        return transaction;
    }

    protected Transaction convertTokenFreeze(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.TokenFreeze tokenFreezeMessage = com.binance.dex.api.proto.TokenFreeze.parseFrom(array);

        TokenFreeze tokenFreeze = new TokenFreeze();
        tokenFreeze.setFrom(Crypto.encodeAddress(hrp, tokenFreezeMessage.getFrom().toByteArray()));
        tokenFreeze.setAmount("" + tokenFreezeMessage.getAmount());
        tokenFreeze.setSymbol(tokenFreezeMessage.getSymbol());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.FREEZE_TOKEN);
        transaction.setRealTx(tokenFreeze);
        return transaction;
    }

    protected Transaction convertTokenUnfreeze(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.TokenUnfreeze tokenUnfreezeMessage = com.binance.dex.api.proto.TokenUnfreeze.parseFrom(array);

        TokenUnfreeze tokenUnfreeze = new TokenUnfreeze();
        tokenUnfreeze.setFrom(Crypto.encodeAddress(hrp, tokenUnfreezeMessage.getFrom().toByteArray()));
        tokenUnfreeze.setSymbol(tokenUnfreezeMessage.getSymbol());
        tokenUnfreeze.setAmount("" + tokenUnfreezeMessage.getAmount());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.UNFREEZE_TOKEN);
        transaction.setRealTx(tokenUnfreeze);
        return transaction;
    }

    protected Transaction convertVote(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Vote voteMessage = com.binance.dex.api.proto.Vote.parseFrom(array);

        Vote vote = new Vote();

        vote.setVoter(Crypto.encodeAddress(hrp, voteMessage.getVoter().toByteArray()));
        vote.setOption((int) voteMessage.getOption());
        vote.setProposalId(voteMessage.getProposalId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.VOTE);
        transaction.setRealTx(vote);
        return transaction;
    }

    protected Transaction convertIssue(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Issue issueMessage = com.binance.dex.api.proto.Issue.parseFrom(array);

        Issue issue = new Issue();
        issue.setFrom(Crypto.encodeAddress(hrp, issueMessage.getFrom().toByteArray()));
        issue.setName(issueMessage.getName());
        issue.setSymbol(issueMessage.getSymbol());
        issue.setTotalSupply(issueMessage.getTotalSupply());
        issue.setMintable(issueMessage.getMintable());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.ISSUE);
        transaction.setRealTx(issue);
        return transaction;
    }

    protected Transaction convertBurn(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Burn burnMessage = com.binance.dex.api.proto.Burn.parseFrom(array);

        Burn burn = new Burn();
        burn.setFrom(Crypto.encodeAddress(hrp, burnMessage.getFrom().toByteArray()));
        burn.setSymbol(burnMessage.getSymbol());
        burn.setAmount(burnMessage.getAmount());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.BURN);
        transaction.setRealTx(burn);
        return transaction;
    }

    protected Transaction convertMint(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Mint mintMessage = com.binance.dex.api.proto.Mint.parseFrom(array);

        Mint mint = new Mint();
        mint.setFrom(Crypto.encodeAddress(hrp, mintMessage.getFrom().toByteArray()));
        mint.setSymbol(mintMessage.getSymbol());
        mint.setAmount(mintMessage.getAmount());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.MINT);
        transaction.setRealTx(mint);
        return transaction;
    }

    protected Transaction convertSubmitProposal(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.SubmitProposal proposalMessage = com.binance.dex.api.proto.SubmitProposal.parseFrom(array);

        SubmitProposal proposal = new SubmitProposal();
        proposal.setTitle(proposalMessage.getTitle());
        proposal.setDescription(proposalMessage.getDescription());
        proposal.setProposalType(ProposalType.fromValue(proposalMessage.getProposalType()));
        proposal.setProposer(Crypto.encodeAddress(hrp, proposalMessage.getProposer().toByteArray()));

        if (null != proposalMessage.getInitialDepositList()) {
            proposal.setInitDeposit(proposalMessage.getInitialDepositList().stream()
                    .map(com.binance.dex.api.client.encoding.message.Token::of).collect(Collectors.toList()));
        }
        proposal.setVotingPeriod(proposalMessage.getVotingPeriod());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SUBMIT_PROPOSAL);
        transaction.setRealTx(proposal);
        return transaction;
    }

    private Transaction convertDeposit(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Deposit depositMessage = com.binance.dex.api.proto.Deposit.parseFrom(array);

        Deposit deposit = new Deposit();
        deposit.setProposalId(depositMessage.getProposalId());
        deposit.setDepositer(Crypto.encodeAddress(hrp,depositMessage.getDepositer().toByteArray()));
        if(null != depositMessage.getAmountList()){
            deposit.setAmount(depositMessage.getAmountList().stream()
            .map(com.binance.dex.api.client.encoding.message.Token::of).collect(Collectors.toList()));
        }
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.DEPOSIT);
        transaction.setRealTx(deposit);
        return transaction;
    }

    private Transaction convertCreateValidator(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        //com.binance.dex.api.proto.CreateValidator createValidatorMessage = com.binance.dex.api.proto.CreateValidator.parseFrom(array);
        RealCreateValidator realCreateValidator = RealCreateValidator.parseFrom(array);

        CreateValidator createValidator = new CreateValidator();
        createValidator.setDelegatorAddress(Crypto.encodeAddress(hrp,realCreateValidator.getCreateValidator().getDelegatorAddress().toByteArray()));
        createValidator.setValidatorAddress(Crypto.encodeAddress(hrp,realCreateValidator.getCreateValidator().getValidatorAddress().toByteArray()));
        createValidator.setDelegation(com.binance.dex.api.client.encoding.message.Token.of(realCreateValidator.getCreateValidator().getDelegation()));
        createValidator.setProposalId(realCreateValidator.getProposalId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.CREATE_VALIDATOR);
        transaction.setRealTx(createValidator);
        return transaction;
    }

    private Transaction convertRemoveValidator(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.RemoveValidator removeValidatorMessage = com.binance.dex.api.proto.RemoveValidator.parseFrom(array);

        RemoveValidator removeValidator = new RemoveValidator();
        removeValidator.setLauncherAddr(Crypto.encodeAddress(hrp,removeValidatorMessage.getLauncherAddr().toByteArray()));
        removeValidator.setValAddr(Crypto.encodeAddress(hrp,removeValidatorMessage.getValAddr().toByteArray()));
        removeValidator.setValConsAddr(Crypto.encodeAddress(hrp,removeValidatorMessage.getValConsAddr().toByteArray()));
        removeValidator.setProposalId(removeValidatorMessage.getProposalId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.REMOVE_VALIDATOR);
        transaction.setRealTx(removeValidator);
        return transaction;
    }


    private Transaction convertListing(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.List listMessage = com.binance.dex.api.proto.List.parseFrom(array);

        Listing listing = new Listing();
        listing.setProposalId(listMessage.getProposalId());
        listing.setBaseAssetSymbol(listMessage.getBaseAssetSymbol());
        listing.setQuoteAssetSymbol(listMessage.getQuoteAssetSymbol());
        listing.setInitPrice(listMessage.getInitPrice());
        listing.setFromAddr(Crypto.encodeAddress(hrp,listMessage.getFrom().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.LISTING);
        transaction.setRealTx(listing);
        return transaction;

    }

}
