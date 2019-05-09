package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderType;
import com.binance.dex.api.client.domain.ProposalType;
import com.binance.dex.api.client.domain.TimeInForce;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.proto.Send;
import com.binance.dex.api.proto.StdTx;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;
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
            return stdTx.getMsgsList().stream()
                    .map(byteString -> {
                        byte[] bytes = byteString.toByteArray();
                        Transaction transaction = convert(bytes);
                        if (null == transaction) {
                            return null;
                        }
                        transaction.setHash(txMessage.getHash());
                        transaction.setHeight(txMessage.getHeight());
                        transaction.setCode(txMessage.getTx_result().getCode());
                        transaction.setMemo(stdTx.getMemo());
                        return transaction;
                    }).filter(t -> null != t).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    protected int getStartIndex(byte[] bytes) {
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
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Transaction convertTransfer(byte[] value) throws InvalidProtocolBufferException {
        byte[] array = new byte[value.length - 4];
        System.arraycopy(value, 4, array, 0, array.length);
        Send send = Send.parseFrom(array);
        MultiTransfer transfer = new MultiTransfer();
        transfer.setFromAddress(Crypto.encodeAddress(hrp, send.getInputsList().get(0).getAddress().toByteArray()));
        transfer.setOutputs(send.getOutputsList().stream().map(o -> {
            Output output = new Output();
            output.setAddress(Crypto.encodeAddress(hrp, o.getAddress().toByteArray()));
            output.setTokens(o.getCoinsList().stream()
                    .map(coin -> new OutputToken(coin.getDenom(), "" + coin.getAmount()))
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

}
