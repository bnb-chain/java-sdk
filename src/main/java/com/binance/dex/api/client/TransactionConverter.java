package com.binance.dex.api.client;

import com.binance.dex.api.client.crosschain.Package;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.bridge.Bind;
import com.binance.dex.api.client.domain.bridge.TransferOut;
import com.binance.dex.api.client.domain.bridge.Unbind;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.broadcast.Burn;
import com.binance.dex.api.client.domain.broadcast.CancelOrder;
import com.binance.dex.api.client.domain.broadcast.CreateValidator;
import com.binance.dex.api.client.domain.broadcast.MiniTokenIssue;
import com.binance.dex.api.client.domain.broadcast.MiniTokenSetURI;
import com.binance.dex.api.client.domain.broadcast.RemoveValidator;
import com.binance.dex.api.client.domain.broadcast.Deposit;
import com.binance.dex.api.client.domain.broadcast.Issue;
import com.binance.dex.api.client.domain.broadcast.Mint;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.SetAccountFlag;
import com.binance.dex.api.client.domain.broadcast.SideDeposit;
import com.binance.dex.api.client.domain.broadcast.SideSubmitProposal;
import com.binance.dex.api.client.domain.broadcast.SideVote;
import com.binance.dex.api.client.domain.broadcast.SubmitProposal;
import com.binance.dex.api.client.domain.broadcast.TinyTokenIssue;
import com.binance.dex.api.client.domain.broadcast.TokenFreeze;
import com.binance.dex.api.client.domain.broadcast.TokenUnfreeze;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.broadcast.Vote;
import com.binance.dex.api.client.domain.jsonrpc.TxResult;
import com.binance.dex.api.client.domain.oracle.ClaimMsg;
import com.binance.dex.api.client.domain.slash.BscSubmitEvidence;
import com.binance.dex.api.client.domain.slash.SideChainUnJail;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.sidechain.*;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.message.InputOutput;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.bridge.BindMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.ClaimMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.TransferOutMsgMessage;
import com.binance.dex.api.client.encoding.message.bridge.UnbindMsgMessage;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.*;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.proto.*;
import com.binance.dex.api.proto.TimeLock;
import com.binance.dex.api.proto.TimeRelock;
import com.binance.dex.api.proto.TimeUnlock;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionConverter {

    private String hrp;

    private String valHrp;

    private final Amino amino = new Amino();

    public TransactionConverter(String hrp, String valHrp) {
        this.hrp = hrp;
        this.valHrp = valHrp;
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
                        transaction.setMemo(stdTx.getMemo());
                        transaction.setResultData(Optional.ofNullable(txMessage.getTx_result()).map(TxResult::getData).orElse(null));
                        transaction.setSource(stdTx.getSource());
                        transaction.setSequence(stdSignature.getSequence());
                        fillTagsAndEvents(txMessage.getTx_result(), transaction);
                        return transaction;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillTagsAndEvents(TxResult txResult, Transaction transaction) {
        boolean hasTags = txResult.getTags() != null && txResult.getTags().size() > 0;
        boolean hasEvents = txResult.getEvents() != null && txResult.getEvents().size() > 0
                && txResult.getEvents().get(0).getAttributes() != null
                && txResult.getEvents().get(0).getAttributes().size() > 0;
        if (hasTags && !hasEvents) {
            transaction.setTags(txResult.getTags());
            List<TxResult.Attribute> attributes = txResult.getTags().stream().map(this::convertOf).collect(Collectors.toList());
            TxResult.Event event = new TxResult.Event();
            event.setAttributes(attributes);
            transaction.setEvents(Collections.singletonList(event));
        } else if (hasEvents && !hasTags) {
            transaction.setEvents(txResult.getEvents());
            List<TxResult.Tag> tags = txResult.getEvents().get(0).getAttributes().stream().map(this::convertOf).collect(Collectors.toList());
            transaction.setTags(tags);
        }
    }

    private TxResult.Attribute convertOf(TxResult.Tag tag) {
        TxResult.Attribute attribute = new TxResult.Attribute();
        attribute.setKey(tag.getKey());
        attribute.setValue(tag.getValue());
        return attribute;
    }

    private TxResult.Tag convertOf(TxResult.Attribute attribute) {
        TxResult.Tag tag = new TxResult.Tag();
        tag.setKey(attribute.getKey());
        tag.setValue(attribute.getValue());
        return tag;
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
                case SideVote:
                    return convertSideVote(bytes);
                case Issue:
                    return convertIssue(bytes);
                case Burn:
                    return convertBurn(bytes);
                case Mint:
                    return convertMint(bytes);
                case TransferTokenOwnership:
                    return convertTransferTokenOwnership(bytes);
                case SubmitProposal:
                    return convertSubmitProposal(bytes);
                case SideSubmitProposal:
                    return convertSideSubmitProposal(bytes);
                case Deposit:
                    return convertDeposit(bytes);
                case SideDeposit:
                    return convertSideDeposit(bytes);
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
                case CreateSideChainValidator:
                    return convertCreateSideChainValidator(bytes);
                case EditSideChainValidator:
                    return convertEditSideChainValidator(bytes);
                case SideChainDelegate:
                    return convertSideChainDelegate(bytes);
                case SideChainRedelegate:
                    return convertSideChainRedelegate(bytes);
                case SideChainUndelegate:
                    return convertSideChainUnBond(bytes);
                case Claim:
                    //transfer in,  update transfer out, update bind
                    return convertClaimMsg(bytes);
                case TransferOut:
                    return convertTransferOutMsg(bytes);
                case Bind:
                    return convertBindMsg(bytes);
                case UnBind:
                    return convertUnBindMsg(bytes);
                case BscSubmitEvidence:
                    return convertBscSubmitEvidence(bytes);
                case SideChainUnJail:
                    return convertSideChainUnJail(bytes);
                case TinyTokenIssue:
                    return convertTinyTokenIssue(bytes);
                case MiniTokenIssue:
                    return convertMiniTokenIssue(bytes);
                case MiniTokenSetURI:
                    return convertMiniTokenSetURI(bytes);
                case MiniTokenList:
                    return convertMiniTokenList(bytes);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction convertTransferTokenOwnership(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        TransferTokenOwnershipMsg msg = TransferTokenOwnershipMsg.parseFrom(raw);

        TransferTokenOwnership transferTokenOwnership = new TransferTokenOwnership();
        transferTokenOwnership.setFrom(Crypto.encodeAddress(hrp, msg.getFrom().toByteArray()));
        transferTokenOwnership.setSymbol(msg.getSymbol());
        transferTokenOwnership.setNewOwner(Crypto.encodeAddress(hrp, msg.getNewOwner().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TRANSFER_TOKEN_OWNERSHIP);
        transaction.setRealTx(transferTokenOwnership);
        return transaction;
    }

    private Transaction convertSideChainUnJail(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        SideChainUnJailMsg message = SideChainUnJailMsg.parseFrom(raw);

        SideChainUnJail unJail = new SideChainUnJail();
        unJail.setSideChainId(message.getSideChainId());
        unJail.setValidatorAddr(Crypto.encodeAddress(valHrp, message.getAddress().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDECHAIN_UNJAIL);
        transaction.setRealTx(unJail);
        return transaction;
    }

    private Transaction convertBscSubmitEvidence(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        SubmitEvidenceMsg message = SubmitEvidenceMsg.parseFrom(raw);

        BscSubmitEvidence bscSubmitEvidence = new BscSubmitEvidence();
        if (message.getSubmitter() != null && raw != null) {
            bscSubmitEvidence.setSubmitter(Crypto.encodeAddress(hrp, message.getSubmitter().toByteArray()));
        }

        List<BscHeader> headers = message.getHeadersList();
        if (headers != null && headers.size() > 0) {
            com.binance.dex.api.client.domain.slash.BscHeader[] bscHeaders = new com.binance.dex.api.client.domain.slash.BscHeader[headers.size()];
            com.binance.dex.api.client.domain.slash.BscHeader bscHeader;
            for (int i = 0; i < headers.size(); i++) {
                bscHeader = new com.binance.dex.api.client.domain.slash.BscHeader();
                bscHeader.setParentHash(headers.get(i).getParentHash().toByteArray());
                bscHeader.setSha3Uncles(headers.get(i).getSha3Uncles().toByteArray());
                bscHeader.setMiner(headers.get(i).getMiner().toByteArray());
                bscHeader.setStateRoot(headers.get(i).getStateRoot().toByteArray());
                bscHeader.setTransactionsRoot(headers.get(i).getTransactionsRoot().toByteArray());
                bscHeader.setReceiptsRoot(headers.get(i).getReceiptsRoot().toByteArray());
                bscHeader.setLogsBloom(headers.get(i).getLogsBloom().toByteArray());
                bscHeader.setDifficulty(headers.get(i).getDifficulty());
                bscHeader.setNumber(headers.get(i).getNumber());
                bscHeader.setGasLimit(headers.get(i).getGasLimit());
                bscHeader.setGasUsed(headers.get(i).getGasUsed());
                bscHeader.setTimestamp(headers.get(i).getTimestamp());
                bscHeader.setExtra(headers.get(i).getExtraData().toByteArray());
                bscHeader.setMixHash(headers.get(i).getMixHash().toByteArray());
                bscHeader.setNonce(headers.get(i).getNonce().toByteArray());
                bscHeaders[i] = bscHeader;
            }
            bscSubmitEvidence.setHeaders(bscHeaders);
        }

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.BSC_SUBMIT_EVIDENCE);
        transaction.setRealTx(bscSubmitEvidence);
        return transaction;
    }

    private Transaction convertBindMsg(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        BindMsgMessage message = new BindMsgMessage();
        amino.decodeBare(raw, message);

        Bind bind = new Bind();
        if (message.getFrom() != null && message.getFrom().getRaw() != null) {
            bind.setFrom(Crypto.encodeAddress(hrp, message.getFrom().getRaw()));
        }
        bind.setSymbol(message.getSymbol());
        bind.setAmount(message.getAmount());
        if (message.getContractAddress() != null) {
            bind.setContractAddress(message.getContractAddress().getAddress());
        }
        bind.setContractDecimal(message.getContractDecimal());
        bind.setExpireTime(message.getExpireTime());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.BIND);
        transaction.setRealTx(bind);

        return transaction;
    }

    private Transaction convertUnBindMsg(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        UnbindMsgMessage message = new UnbindMsgMessage();
        amino.decodeBare(raw, message);

        Unbind bind = new Unbind();
        if (message.getFrom() != null && message.getFrom().getRaw() != null) {
            bind.setFrom(Crypto.encodeAddress(hrp, message.getFrom().getRaw()));
        }
        bind.setSymbol(message.getSymbol());


        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.UNBIND);
        transaction.setRealTx(bind);

        return transaction;
    }

    private Transaction convertTransferOutMsg(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        TransferOutMsgMessage message = new TransferOutMsgMessage();
        amino.decodeBare(raw, message);

        TransferOut transferOut = new TransferOut();
        if (message.getFrom() != null && message.getFrom().getRaw() != null) {
            transferOut.setFrom(Crypto.encodeAddress(hrp, message.getFrom().getRaw()));
        }
        if (message.getToAddress() != null) {
            transferOut.setToAddress(message.getToAddress().getAddress());
        }

        Token token = new Token();
        if (message.getAmount() != null) {
            token.setAmount(message.getAmount().getAmount());
            token.setDenom(message.getAmount().getDenom());
        }
        transferOut.setAmount(token);

        transferOut.setExpireTime(message.getExpireTime());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TRANSFER_OUT);
        transaction.setRealTx(transferOut);

        return transaction;
    }

    private Transaction convertClaimMsg(byte[] value) throws Exception {
        byte[] raw = ByteUtil.cut(value, 4);
        ClaimMsgMessage message = new ClaimMsgMessage();
        amino.decodeBare(raw, message);
        ClaimMsg claimMsg = new ClaimMsg();
        claimMsg.setChainId(message.getChainId());
        claimMsg.setSequence(message.getSequence());
        List<Package> packages = Decoder.decodeList(message.getPayload(), Package.class);
        packages.forEach(pack -> pack.setHrp(this.hrp));
        claimMsg.setPayload(packages);
        if (message.getValidatorAddress().getRaw() != null) {
            claimMsg.setValidatorAddress(Crypto.encodeAddress(valHrp, message.getValidatorAddress().getRaw()));
        }
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.CLAIM);
        transaction.setRealTx(claimMsg);

        return transaction;
    }

    private Transaction convertSideChainUnBond(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        SideChainUndelegateMessage message = new SideChainUndelegateMessage();
        amino.decodeBare(raw, message);

        SideChainUnBond unBond = new SideChainUnBond();

        if (message.getDelegatorAddress() != null && message.getDelegatorAddress().getRaw() != null) {
            unBond.setDelegatorAddress(Crypto.encodeAddress(hrp, message.getDelegatorAddress().getRaw()));
        }

        if (message.getValidatorAddress() != null && message.getValidatorAddress().getRaw() != null) {
            unBond.setValidatorAddress(Crypto.encodeAddress(valHrp, message.getValidatorAddress().getRaw()));
        }

        Token amount = new Token();
        if (message.getAmount() != null) {
            amount.setAmount(message.getAmount().getAmount());
            amount.setDenom(message.getAmount().getDenom());
        }
        unBond.setAmount(amount);

        unBond.setSideChainId(message.getSideChainId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDECHAIN_UNBOND);
        transaction.setRealTx(unBond);

        return transaction;
    }

    private Transaction convertSideChainRedelegate(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        SideChainRedelegateMessage message = new SideChainRedelegateMessage();
        amino.decodeBare(raw, message);

        SideChainRedelegate redelegate = new SideChainRedelegate();
        if (message.getDelegatorAddress() != null && message.getDelegatorAddress().getRaw() != null) {
            redelegate.setDelegatorAddress(Crypto.encodeAddress(hrp, message.getDelegatorAddress().getRaw()));
        }

        if (message.getSrcValidatorAddress() != null && message.getSrcValidatorAddress().getRaw() != null) {
            redelegate.setSrcValidatorAddress(Crypto.encodeAddress(valHrp, message.getSrcValidatorAddress().getRaw()));
        }

        if (message.getDstValidatorAddress() != null && message.getDstValidatorAddress().getRaw() != null) {
            redelegate.setDstValidatorAddress(Crypto.encodeAddress(valHrp, message.getDstValidatorAddress().getRaw()));
        }

        Token amount = new Token();
        if (message.getAmount() != null) {
            amount.setAmount(message.getAmount().getAmount());
            amount.setDenom(message.getAmount().getDenom());
        }
        redelegate.setAmount(amount);

        redelegate.setSideChainId(message.getSideChainId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDECHAIN_REDELEGATE);
        transaction.setRealTx(redelegate);

        return transaction;
    }

    private Transaction convertSideChainDelegate(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        SideChainDelegateMessage message = new SideChainDelegateMessage();
        amino.decodeBare(raw, message);

        SideChainDelegate sideChainDelegate = new SideChainDelegate();
        if (message.getDelegatorAddress() != null && message.getDelegatorAddress().getRaw() != null) {
            sideChainDelegate.setDelegatorAddress(Crypto.encodeAddress(hrp, message.getDelegatorAddress().getRaw()));
        }

        if (message.getValidatorAddress() != null && message.getValidatorAddress().getRaw() != null) {
            sideChainDelegate.setValidatorAddress(Crypto.encodeAddress(valHrp, message.getValidatorAddress().getRaw()));
        }

        Token token = new Token();
        if (message.getDelegation() != null) {
            token.setDenom(message.getDelegation().getDenom());
            token.setAmount(message.getDelegation().getAmount());
        }
        sideChainDelegate.setDelegation(token);

        sideChainDelegate.setSideChainId(message.getSideChainId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDECHAIN_DELEGATE);
        transaction.setRealTx(sideChainDelegate);

        return transaction;
    }

    private Transaction convertEditSideChainValidator(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        EditSideChainValidatorMessage message = new EditSideChainValidatorMessage();
        amino.decodeBare(raw, message);

        EditSideChainValidator editSideChainValidator = new EditSideChainValidator();

        Description description = new Description();
        if (message.getDescription() != null) {
            description.setMoniker(message.getDescription().getMoniker());
            description.setDetails(message.getDescription().getDetails());
            description.setIdentity(message.getDescription().getIdentity());
            description.setWebsite(message.getDescription().getWebsite());
        }
        editSideChainValidator.setDescription(description);

        if (message.getValidatorOperatorAddress() != null && message.getValidatorOperatorAddress().getRaw() != null) {
            editSideChainValidator.setValidatorAddress(Crypto.encodeAddress(valHrp, message.getValidatorOperatorAddress().getRaw()));
        }

        if (message.getCommissionRate() != null) {
            editSideChainValidator.setCommissionRate(message.getCommissionRate().getValue());
        }

        editSideChainValidator.setSideChainId(message.getSideChainId());

        if (message.getSideFeeAddr() != null) {
            editSideChainValidator.setSideFeeAddr("0x" + Hex.toHexString(message.getSideFeeAddr()));
        }

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.EDIT_SIDECHAIN_VALIDATOR);
        transaction.setRealTx(editSideChainValidator);

        return transaction;
    }

    private Transaction convertCreateSideChainValidator(byte[] value) throws IOException {
        byte[] raw = ByteUtil.cut(value, 4);
        CreateSideChainValidatorMessage message = new CreateSideChainValidatorMessage();
        amino.decodeBare(raw, message);

        CreateSideChainValidator createSideChainValidator = new CreateSideChainValidator();

        Description description = new Description();
        if (message.getDescription() != null) {
            description.setMoniker(message.getDescription().getMoniker());
            description.setDetails(message.getDescription().getDetails());
            description.setIdentity(message.getDescription().getIdentity());
            description.setWebsite(message.getDescription().getWebsite());
        }
        createSideChainValidator.setDescription(description);

        Commission commission = new Commission();
        if (message.getCommission() != null) {
            try {
                commission.setRate(message.getCommission().getRate().getValue());
                commission.setMaxRate(message.getCommission().getMaxRate().getValue());
                commission.setMaxChangeRate(message.getCommission().getMaxChangeRate().getValue());
            } catch (NullPointerException e) {
                //ignore
            }
        }
        createSideChainValidator.setCommission(commission);

        if (message.getDelegatorAddr() != null && message.getDelegatorAddr().getRaw() != null) {
            createSideChainValidator.setDelegatorAddr(Crypto.encodeAddress(hrp, message.getDelegatorAddr().getRaw()));
        }

        if (message.getValidatorOperatorAddr() != null && message.getValidatorOperatorAddr().getRaw() != null) {
            createSideChainValidator.setValidatorAddr(Crypto.encodeAddress(valHrp, message.getValidatorOperatorAddr().getRaw()));
        }

        Token delegation = new Token();
        if (message.getDelegation() != null) {
            delegation.setAmount(message.getDelegation().getAmount());
            delegation.setDenom(message.getDelegation().getDenom());
        }
        createSideChainValidator.setDelegation(delegation);

        createSideChainValidator.setSideChainId(message.getSideChainId());

        if (message.getSideConsAddr() != null) {
            createSideChainValidator.setSideConsAddr("0x" + Hex.toHexString(message.getSideConsAddr()));
        }

        if (message.getSideFeeAddr() != null) {
            createSideChainValidator.setSideFeeAddr("0x" + Hex.toHexString(message.getSideFeeAddr()));
        }

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.CREATE_SIDECHAIN_VALIDATOR);
        transaction.setRealTx(createSideChainValidator);
        return transaction;
    }


    private Transaction convertRefundHashTimerLock(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        RefundHashTimerLockMsg refundHtlMsg = RefundHashTimerLockMsg.parseFrom(array);

        RefundHashTimerLock refundHashTimerLock = new RefundHashTimerLock();
        refundHashTimerLock.setFrom(Crypto.encodeAddress(hrp, refundHtlMsg.getFrom().toByteArray()));
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
        claimHashTimerLock.setFrom(Crypto.encodeAddress(hrp, claimHtlMsg.getFrom().toByteArray()));
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
        depositHashTimerLock.setFrom(Crypto.encodeAddress(hrp, depositHtlMsg.getFrom().toByteArray()));
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
        hashTimerLockTransfer.setFrom(Crypto.encodeAddress(hrp, htlTransferMsg.getFrom().toByteArray()));
        hashTimerLockTransfer.setTo(Crypto.encodeAddress(hrp, htlTransferMsg.getTo().toByteArray()));
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
        saf.setFromAddr(Crypto.encodeAddress(hrp, setAccountFlag.getFrom().toByteArray()));
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
        trl.setFromAddr(Crypto.encodeAddress(hrp, timeRelock.getFrom().toByteArray()));
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
        tul.setFromAddr(Crypto.encodeAddress(hrp, timeUnlock.getFrom().toByteArray()));
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
        tl.setFromAddr(Crypto.encodeAddress(hrp, timeLock.getFrom().toByteArray()));
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

    protected Transaction convertSideVote(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.SideVote voteMessage = com.binance.dex.api.proto.SideVote.parseFrom(array);

        SideVote vote = new SideVote();

        vote.setVoter(Crypto.encodeAddress(hrp, voteMessage.getVoter().toByteArray()));
        vote.setOption((int) voteMessage.getOption());
        vote.setProposalId(voteMessage.getProposalId());
        vote.setSideChainId(voteMessage.getSideChainId());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDE_VOTE);
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

    protected Transaction convertSideSubmitProposal(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.SideSubmitProposal proposalMessage = com.binance.dex.api.proto.SideSubmitProposal.parseFrom(array);

        SideSubmitProposal proposal = new SideSubmitProposal();
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
        transaction.setTxType(TxType.SIDE_SUBMIT_PROPOSAL);
        transaction.setRealTx(proposal);
        proposal.setSideChainId(proposalMessage.getSideChainId());
        return transaction;
    }

    private Transaction convertDeposit(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.Deposit depositMessage = com.binance.dex.api.proto.Deposit.parseFrom(array);

        Deposit deposit = new Deposit();
        deposit.setProposalId(depositMessage.getProposalId());
        deposit.setDepositer(Crypto.encodeAddress(hrp, depositMessage.getDepositer().toByteArray()));
        if (null != depositMessage.getAmountList()) {
            deposit.setAmount(depositMessage.getAmountList().stream()
                    .map(com.binance.dex.api.client.encoding.message.Token::of).collect(Collectors.toList()));
        }
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.DEPOSIT);
        transaction.setRealTx(deposit);
        return transaction;
    }

    private Transaction convertSideDeposit(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.SideDeposit depositMessage = com.binance.dex.api.proto.SideDeposit.parseFrom(array);

        SideDeposit deposit = new SideDeposit();
        deposit.setProposalId(depositMessage.getProposalId());
        deposit.setDepositer(Crypto.encodeAddress(hrp, depositMessage.getDepositer().toByteArray()));
        if (null != depositMessage.getAmountList()) {
            deposit.setAmount(depositMessage.getAmountList().stream()
                    .map(com.binance.dex.api.client.encoding.message.Token::of).collect(Collectors.toList()));
        }
        deposit.setSideChainId(depositMessage.getSideChainId());
        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.SIDE_DEPOSIT);
        transaction.setRealTx(deposit);
        return transaction;
    }

    private Transaction convertCreateValidator(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        //com.binance.dex.api.proto.CreateValidator createValidatorMessage = com.binance.dex.api.proto.CreateValidator.parseFrom(array);
        RealCreateValidator realCreateValidator = RealCreateValidator.parseFrom(array);

        CreateValidator createValidator = new CreateValidator();
        createValidator.setDelegatorAddress(Crypto.encodeAddress(hrp, realCreateValidator.getCreateValidator().getDelegatorAddress().toByteArray()));
        createValidator.setValidatorAddress(Crypto.encodeAddress(hrp, realCreateValidator.getCreateValidator().getValidatorAddress().toByteArray()));
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
        removeValidator.setLauncherAddr(Crypto.encodeAddress(hrp, removeValidatorMessage.getLauncherAddr().toByteArray()));
        removeValidator.setValAddr(Crypto.encodeAddress(hrp, removeValidatorMessage.getValAddr().toByteArray()));
        removeValidator.setValConsAddr(Crypto.encodeAddress(hrp, removeValidatorMessage.getValConsAddr().toByteArray()));
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
        listing.setFromAddr(Crypto.encodeAddress(hrp, listMessage.getFrom().toByteArray()));

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.LISTING);
        transaction.setRealTx(listing);
        return transaction;

    }

    private Transaction convertTinyTokenIssue(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.TinyTokenIssue issueMessage = com.binance.dex.api.proto.TinyTokenIssue.parseFrom(array);

        TinyTokenIssue issue = new TinyTokenIssue();
        issue.setFrom(Crypto.encodeAddress(hrp, issueMessage.getFrom().toByteArray()));
        issue.setName(issueMessage.getName());
        issue.setSymbol(issueMessage.getSymbol());
        issue.setTotalSupply(issueMessage.getTotalSupply());
        issue.setMintable(issueMessage.getMintable());
        issue.setTokenURI(issueMessage.getTokenUri());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.TINY_TOKEN_ISSUE);
        transaction.setRealTx(issue);
        return transaction;
    }


    private Transaction convertMiniTokenIssue(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.MiniTokenIssue issueMessage = com.binance.dex.api.proto.MiniTokenIssue.parseFrom(array);

        MiniTokenIssue issue = new MiniTokenIssue();
        issue.setFrom(Crypto.encodeAddress(hrp, issueMessage.getFrom().toByteArray()));
        issue.setName(issueMessage.getName());
        issue.setSymbol(issueMessage.getSymbol());
        issue.setTotalSupply(issueMessage.getTotalSupply());
        issue.setMintable(issueMessage.getMintable());
        issue.setTokenURI(issueMessage.getTokenUri());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.MINI_TOKEN_ISSUE);
        transaction.setRealTx(issue);
        return transaction;
    }


    private Transaction convertMiniTokenSetURI(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.MiniTokenSetURI uriMessage = com.binance.dex.api.proto.MiniTokenSetURI.parseFrom(array);

        MiniTokenSetURI setURI = new MiniTokenSetURI();
        setURI.setFrom(Crypto.encodeAddress(hrp, uriMessage.getFrom().toByteArray()));
        setURI.setSymbol(uriMessage.getSymbol());
        setURI.setTokenURI(uriMessage.getTokenUri());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.MINI_TOKEN_SET_URI);
        transaction.setRealTx(setURI);
        return transaction;
    }

    private Transaction convertMiniTokenList(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        com.binance.dex.api.proto.MiniTokenList listMessage = com.binance.dex.api.proto.MiniTokenList.parseFrom(array);

        MiniTokenListing listing = new MiniTokenListing();
        listing.setFromAddr(Crypto.encodeAddress(hrp, listMessage.getFrom().toByteArray()));
        listing.setBaseAssetSymbol(listMessage.getBaseAssetSymbol());
        listing.setQuoteAssetSymbol(listMessage.getQuoteAssetSymbol());
        listing.setInitPrice(listMessage.getInitPrice());

        Transaction transaction = new Transaction();
        transaction.setTxType(TxType.MINI_TOKEN_LIST);
        transaction.setRealTx(listing);
        return transaction;
    }
}
