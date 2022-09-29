package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.oracle.Prophecy;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;
import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.domain.stake.beaconchain.*;
import com.binance.dex.api.client.domain.stake.Delegation;
import com.binance.dex.api.client.domain.stake.Redelegation;
import com.binance.dex.api.client.domain.stake.UnBondingDelegation;
import com.binance.dex.api.client.domain.stake.Validator;
import com.binance.dex.api.client.domain.stake.sidechain.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BinanceDexApiNodeClient extends BinanceDexApiRestClient {

    List<Transaction> getBlockTransactions(Long height);

    Transaction getTransaction(String hash);

    BlockMeta getBlockMetaByHeight(Long height);

    BlockMeta getBlockMetaByHash(String hash);

    Token getTokenInfoBySymbol(String symbol);

    List<StakeValidator> getStakeValidator();

    Proposal getProposalById(String proposalId);

    Proposal getSideProposalById(String proposalId, String sideChainId);

    Account getCommittedAccount(String address);

    AtomicSwap getSwapByID(String swapID);

    MiniToken getMiniTokenInfoBySymbol(String symbol);

    @Override
    default Time getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Validators getValidators() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Peer> getPeers() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Market> getMarkets(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionMetadata getTransactionMetadata(String hash) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Token> getTokens(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderBook getOrderBook(String symbol, Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getOpenOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getOpenOrders(OpenOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getClosedOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getClosedOrders(ClosedOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Order getOrder(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TickerStatistics> get24HrPriceStatistics() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TickerStatistics> get24HrPriceStatistics(String symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getTrades() {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getTrades(TradesRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionPageV2 getTransactions(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionPageV2 getTransactions(TransactionsRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TransactionPageV2 getTransactionsInBlock(long blockHeight) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<MiniToken> getMiniTokens(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Market> getMiniMarkets(Integer limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getMiniCandleStickBars(String symbol, CandlestickInterval interval) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<Candlestick> getMiniCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getMiniOpenOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getMiniOpenOrders(OpenOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getMiniClosedOrders(String address) {
        throw new UnsupportedOperationException();
    }

    @Override
    default OrderList getMiniClosedOrders(ClosedOrdersRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Order getMiniOrder(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TickerStatistics> getMini24HrPriceStatistics() {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TickerStatistics> getMini24HrPriceStatistics(String symbol) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getMiniTrades() {
        throw new UnsupportedOperationException();
    }

    @Override
    default TradePage getMiniTrades(TradesRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> newOrder(NewOrder newOrder, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> vote(Vote vote, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> cancelOrder(CancelOrder cancelOrder, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }


    @Override
    default List<TransactionMetadata> freeze(TokenFreeze freeze, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    default List<TransactionMetadata> unfreeze(TokenUnfreeze unfreeze, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }
    List<TransactionMetadata> createValidator(CreateBeaconChainValidator createBeaconChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> editValidator(EditBeaconChainValidator editBeaconChainValidator, Wallet wallet, TransactionOption option, boolean sync) throws IOException, NoSuchAlgorithmException ;

    List<TransactionMetadata> delegate(BeaconChainDelegate beaconChainDelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> redelegate(BeaconChainRedelegate beaconChainRedelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> undelegate(BeaconChainUndelegate beaconChainUndelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    Validator getValidator(String validatorAddress) throws IOException;

    List<Validator> getTopValidators(int top) throws IOException ;

    Delegation getDelegation(String delegatorAddress, String validatorAddress) throws IOException;

    List<Delegation> getDelegations(String delegatorAddress) throws IOException;

    Redelegation getRedelegation(String delegatorAddress, String srcValidatorAddress, String dstValidatorAddress) throws IOException;

    List<Redelegation> getRedelegations(String delegatorAddress) throws IOException;

    UnBondingDelegation getUnBondingDelegation(String delegatorAddress, String validatorAddress) throws IOException;

    List<UnBondingDelegation> getUnBondingDelegations(String delegatorAddress) throws IOException;

    List<UnBondingDelegation> getUnBondingDelegationsByValidator(String validatorAddress)  throws IOException ;

    List<Redelegation> getRedelegationsByValidator(String validatorAddress) throws IOException;

    Pool getPool() throws IOException;

    long getAllValidatorsCount(boolean jailInvolved) throws IOException;

    List<TransactionMetadata> createSideChainValidator(CreateSideChainValidator createSideChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> editSideChainValidator(EditSideChainValidator editSideChainValidator, Wallet wallet, TransactionOption option, boolean synv) throws IOException, NoSuchAlgorithmException ;

    List<TransactionMetadata> sideChainDelegate(SideChainDelegate sideChainDelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> sideChainRedelagate(SideChainRedelegate sideChainRedelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> sideChainUnbond(SideChainUnBond sideChainUndelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    Validator getSideChainValidator(String sideChainId, String validatorAddress) throws IOException;

    List<Validator> getSideChainTopValidators(String sideChainId, int top) throws IOException ;

    Delegation getSideChainDelegation(String sideChainId, String delegatorAddress, String validatorAddress) throws IOException;

    List<Delegation> getSideChainDelegations(String sideChainId, String delegatorAddress) throws IOException;

    Redelegation getSideChainRedelegation(String sideChainId, String delegatorAddress, String srcValidatorAddress, String dstValidatorAddress) throws IOException;

    List<Redelegation> getSideChainRedelegations(String sideChainId, String delegatorAddress) throws IOException;

    UnBondingDelegation getSideChainUnBondingDelegation(String sideChainId, String delegatorAddress, String validatorAddress) throws IOException;

    List<UnBondingDelegation> getSideChainUnBondingDelegations(String sideChainId, String delegatorAddress) throws IOException;

    List<UnBondingDelegation> getSideChainUnBondingDelegationsByValidator(String sideChainId, String validatorAddress)  throws IOException ;

    List<Redelegation> getSideChainRedelegationsByValidator(String sideChainId, String validatorAddress) throws IOException;

    Pool getSideChainPool(String sideChainId) throws IOException;

    long getAllSideChainValidatorsCount(String sideChainId, boolean jailInvolved) throws IOException;

    List<TransactionMetadata> claim(int chainId, byte[] payload, long sequence, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    @Nullable
    Prophecy getProphecy(int claimType, long sequence) throws IOException;

    long getCurrentSequence(int claimType);

    List<TransactionMetadata> transferOut(String toAddress, com.binance.dex.api.client.encoding.message.Token amount, long expireTimeInSeconds, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> bind(String symbol, long amount, String contractAddress, int contractDecimal, long expireTimeInSeconds, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> unBind(String symbol, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

}
