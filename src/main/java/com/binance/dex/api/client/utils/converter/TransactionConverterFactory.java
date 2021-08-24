package com.binance.dex.api.client.utils.converter;

import com.binance.dex.api.client.domain.Transaction;
import com.binance.dex.api.client.domain.TransactionPage;
import com.binance.dex.api.client.domain.TransactionPageV2;
import com.binance.dex.api.client.domain.TransactionV2;
import com.binance.dex.api.client.utils.converter.impl.*;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class TransactionConverterFactory {

    private static Map<com.binance.dex.api.client.domain.broadcast.TxType, TransactionConverter> converterMap = Maps.newHashMap();

    static {
        FBscSubmitEvidenceConverter bscSubmitEvidenceConverter = new FBscSubmitEvidenceConverter();
        converterMap.put(bscSubmitEvidenceConverter.getType(), bscSubmitEvidenceConverter);

        FBurnConverter burnConverter = new FBurnConverter();
        converterMap.put(burnConverter.getType(), burnConverter);

        FCancelOrderConverter cancelOrderConverter = new FCancelOrderConverter();
        converterMap.put(cancelOrderConverter.getType(), cancelOrderConverter);

        FClaimHtlConverter claimHtlConverter = new FClaimHtlConverter();
        converterMap.put(claimHtlConverter.getType(), claimHtlConverter);

        FCreateSideChainValidatorConverter createSideChainValidatorConverter = new FCreateSideChainValidatorConverter();
        converterMap.put(createSideChainValidatorConverter.getType(), createSideChainValidatorConverter);

        FCreateValidatorConverter createValidatorConverter = new FCreateValidatorConverter();
        converterMap.put(createValidatorConverter.getType(), createValidatorConverter);

        FCrossBindConverter crossBindConverter = new FCrossBindConverter();
        converterMap.put(crossBindConverter.getType(), crossBindConverter);

        FCrossTransferOutConverter crossTransferOutConverter = new FCrossTransferOutConverter();
        converterMap.put(crossTransferOutConverter.getType(), crossTransferOutConverter);

        FCrossUnBindConverter crossUnBindConverter = new FCrossUnBindConverter();
        converterMap.put(crossUnBindConverter.getType(), crossUnBindConverter);

        FDepositConverter depositConverter = new FDepositConverter();
        converterMap.put(depositConverter.getType(), depositConverter);

        FDepositHtlConverter depositHtlConverter = new FDepositHtlConverter();
        converterMap.put(depositHtlConverter.getType(), depositHtlConverter);

        FEditSideChainValidatorConverter editSideChainValidatorConverter = new FEditSideChainValidatorConverter();
        converterMap.put(editSideChainValidatorConverter.getType(), editSideChainValidatorConverter);

        FFreezeConverter freezeConverter = new FFreezeConverter();
        converterMap.put(freezeConverter.getType(), freezeConverter);

        FHtlTransferConverter htlTransferConverter = new FHtlTransferConverter();
        converterMap.put(htlTransferConverter.getType(), htlTransferConverter);

        FIssueTokenConverter issueTokenConverter = new FIssueTokenConverter();
        converterMap.put(issueTokenConverter.getType(), issueTokenConverter);

        FListingConverter listingConverter = new FListingConverter();
        converterMap.put(listingConverter.getType(), listingConverter);

        FMiniTokenIssueConverter miniTokenIssueConverter = new FMiniTokenIssueConverter();
        converterMap.put(miniTokenIssueConverter.getType(), miniTokenIssueConverter);

        FMiniTokenListingConverter miniTokenListingConverter = new FMiniTokenListingConverter();
        converterMap.put(miniTokenListingConverter.getType(), miniTokenListingConverter);

        FMiniTokenSetURIConverter miniTokenSetURIConverter = new FMiniTokenSetURIConverter();
        converterMap.put(miniTokenSetURIConverter.getType(), miniTokenSetURIConverter);

        FMintConverter mintConverter = new FMintConverter();
        converterMap.put(mintConverter.getType(), mintConverter);

        FNewOrderConverter newOrderConverter = new FNewOrderConverter();
        converterMap.put(newOrderConverter.getType(), newOrderConverter);

        FOracleClaimConverter oracleClaimConverter = new FOracleClaimConverter();
        converterMap.put(oracleClaimConverter.getType(), oracleClaimConverter);

        FProposalConverter proposalConverter = new FProposalConverter();
        converterMap.put(proposalConverter.getType(), proposalConverter);

        FRefundHtlConverter refundHtlConverter = new FRefundHtlConverter();
        converterMap.put(refundHtlConverter.getType(), refundHtlConverter);

        FRemoveValidatorConverter removeValidatorConverter = new FRemoveValidatorConverter();
        converterMap.put(removeValidatorConverter.getType(), removeValidatorConverter);

        FSetAccountFlagsConverter setAccountFlagsConverter = new FSetAccountFlagsConverter();
        converterMap.put(setAccountFlagsConverter.getType(), setAccountFlagsConverter);

        FSideChainDelegateConverter sideChainDelegateConverter = new FSideChainDelegateConverter();
        converterMap.put(sideChainDelegateConverter.getType(), sideChainDelegateConverter);

        FSideChainRedelegateConverter sideChainRedelegateConverter = new FSideChainRedelegateConverter();
        converterMap.put(sideChainRedelegateConverter.getType(), sideChainRedelegateConverter);

        FSideChainUnJailConverter sideChainUnJailConverter = new FSideChainUnJailConverter();
        converterMap.put(sideChainUnJailConverter.getType(), sideChainUnJailConverter);

        FSideChainUndelegateConverter sideChainUndelegateConverter = new FSideChainUndelegateConverter();
        converterMap.put(sideChainUndelegateConverter.getType(), sideChainUndelegateConverter);

        FSideDepositConverter sideDepositConverter = new FSideDepositConverter();
        converterMap.put(sideDepositConverter.getType(), sideDepositConverter);

        FSideProposalConverter sideProposalConverter = new FSideProposalConverter();
        converterMap.put(sideProposalConverter.getType(), sideProposalConverter);

        FSideVoteConverter sideVoteConverter = new FSideVoteConverter();
        converterMap.put(sideVoteConverter.getType(), sideVoteConverter);

        FTimeLockConverter timeLockConverter = new FTimeLockConverter();
        converterMap.put(timeLockConverter.getType(), timeLockConverter);

        FTimeReLockConverter timeReLockConverter = new FTimeReLockConverter();
        converterMap.put(timeReLockConverter.getType(), timeReLockConverter);

        FTimeUnlockConverter timeUnlockConverter = new FTimeUnlockConverter();
        converterMap.put(timeUnlockConverter.getType(), timeUnlockConverter);

        FTinyTokenIssueConverter tinyTokenIssueConverter = new FTinyTokenIssueConverter();
        converterMap.put(tinyTokenIssueConverter.getType(), tinyTokenIssueConverter);

        FTransferConverter transferConverter = new FTransferConverter();
        converterMap.put(transferConverter.getType(), transferConverter);

        FTransferOwnershipConverter transferOwnershipConverter = new FTransferOwnershipConverter();
        converterMap.put(transferOwnershipConverter.getType(), transferOwnershipConverter);

        FUnfreezeConverter unfreezeConverter = new FUnfreezeConverter();
        converterMap.put(unfreezeConverter.getType(), unfreezeConverter);

        FVoteConverter voteConverter = new FVoteConverter();
        converterMap.put(voteConverter.getType(), voteConverter);
    }

    public static <T extends TransactionConverter> T getConverter(com.binance.dex.api.client.domain.broadcast.TxType txType) {
        if (converterMap.containsKey(txType)) {
            return (T) converterMap.get(txType);
        }
        return null;
    }


    public Transaction convert(TransactionV2 transactionV2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        if (transactionV2 == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setBlockHeight(transactionV2.getBlockHeight());
        transaction.setTxHash(transactionV2.getHash());
        transaction.setTxType(transactionV2.getType());
        transaction.setTimeStamp(formatter.format(new Date(transactionV2.getBlockTime())));
        transaction.setConfirmBlocks(0L);

        transaction.setCode(transactionV2.getCode());
        transaction.setMemo(transactionV2.getMemo());

        transaction.setTxAsset(transactionV2.getAsset());
        transaction.setFromAddr(transactionV2.getFromAddr());
        transaction.setData(transactionV2.getData());

        transaction.setTxAge(DateUtil.betweenSecs(new Date(transactionV2.getBlockTime()), DateUtil.now()));

        transaction.setTxFee(NumberUtil.longToBigDecimalString(transactionV2.getFee()));

        TransactionConverter converter = getConverter(TxType.getTypeByName(transactionV2.getType()));
        converter.doConvert(transactionV2, transaction);

        return transaction;
    }

    public TransactionPage convert(TransactionPageV2 transactionPageV2) {
        TransactionPage transactionPage = new TransactionPage();
        if (transactionPageV2 != null) {
            transactionPage.setTotal(transactionPageV2.getTotal());
            transactionPage.setTx(new ArrayList<>());
            transactionPageV2.getTxs().stream().forEach(tx -> {
                transactionPage.getTx().add(convert(tx));
            });
        }
        return transactionPage;
    }
}
