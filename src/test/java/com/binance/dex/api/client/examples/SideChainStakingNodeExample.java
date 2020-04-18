package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.sidechain.*;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class SideChainStakingNodeExample {

    private BinanceDexApiNodeClient nodeClient = null;
    private final Wallet wallet = new Wallet("66d08fc209b31090ea21a8cf9a6e2680b008e2b905b2358ae31e0f42b140de99", BinanceDexEnvironment.TEST_NET);

    @Before
    public void setup() {
        nodeClient = BinanceDexApiClientFactory.newInstance().newNodeRpcClient(BinanceDexEnvironment.TEST_NET.getNodeUrl(),BinanceDexEnvironment.TEST_NET.getHrp());
    }

    @Test
    public void testCreateSideChainValidator(){
        CreateSideChainValidator createSideChainValidator = new CreateSideChainValidator();

        //create and set description
        Description validatorDescription = new Description();
        validatorDescription.setMoniker("custom-moniker");
        validatorDescription.setIdentity("ide");
        validatorDescription.setWebsite("https://www.website.com");
        validatorDescription.setDetails("This is a side-chain validator");

        createSideChainValidator.setDescription(validatorDescription);

        //create and set commission
        Commission commission = new Commission();
        commission.setRate(5L);
        commission.setMaxRate(100L);
        commission.setMaxChangeRate(5L);

        createSideChainValidator.setCommission(commission);

        //set delegator address, here use self address
        createSideChainValidator.setDelegatorAddr(wallet.getAddress());

        //set delegation token, here use 1000000 BNB
        Token delegationToken = new Token();
        delegationToken.setDenom("BNB");
        delegationToken.setAmount(10000000000L);
        createSideChainValidator.setDelegation(delegationToken);

        //set side-chain id
        createSideChainValidator.setSideChainId("bsc");

        //set side-chain validator cons address
        createSideChainValidator.setSideConsAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        //set side-chain validator fee address
        createSideChainValidator.setSideFeeAddr("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791");

        TransactionOption options = new TransactionOption("", 0, null);

        try {
            List<TransactionMetadata> transactionMetadatas = nodeClient.createSideChainValidator(createSideChainValidator, wallet, options, true);
            for (TransactionMetadata transactionMetadata : transactionMetadatas) {
                System.out.println(transactionMetadata.toString());
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEditSideChainValidator(){
        EditSideChainValidator editSideChainValidator = new EditSideChainValidator();

        //set new description if needed
        Description description = new Description();
        description.setMoniker("new Moniker");
        editSideChainValidator.setDescription(description);

        //set new rate if needed
        editSideChainValidator.setCommissionRate(1L);

        //set new cons address needed
        editSideChainValidator.setSideConsAddr("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791");

        //set new fee address if needed
        editSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        editSideChainValidator.setSideChainId("bsc");

        try {
            List<TransactionMetadata> transactionMetadatas = nodeClient.editSideChainValidator(editSideChainValidator, wallet, createTransactionOptionForTest(), true);
            for (TransactionMetadata transactionMetadata : transactionMetadatas) {
                consolePrintln(transactionMetadata);
            }
        }catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSideChainDelegate(){
        SideChainDelegate sideChainDelegate = new SideChainDelegate();

        //set delegate token
        Token delegation = new Token("BNB", 100000000L);
        sideChainDelegate.setDelegation(delegation);

        //set delegator address, here is self
        sideChainDelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        sideChainDelegate.setValidatorAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t");

        //set side-chain id
        sideChainDelegate.setSideChainId("bsc");

        try {
            List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainDelegate(sideChainDelegate, wallet, createTransactionOptionForTest(), true);
            for (TransactionMetadata transactionMetadata : transactionMetadatas) {
                consolePrintln(transactionMetadata);
            }
        }catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSideChainRedelegate(){
        SideChainRedelegate redelegate = new SideChainRedelegate();

        //set redelegate amount
        redelegate.setAmount(new Token("bnb".toUpperCase(), 100000000L));

        //set delegator address
        redelegate.setDelegatorAddress(wallet.getAddress());

        //set source validator address
        redelegate.setSrcValidatorAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t");

        //set destination validator address
        redelegate.setDstValidatorAddress("bva10l34nul25nfxyurfkvkyn7z5kumrg0rjun409e");

        //set side-chain id
        redelegate.setSideChainId("bsc");

        try {
            List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainRedelagate(redelegate, wallet, createTransactionOptionForTest(), true);
            consolePrintln(transactionMetadatas.get(0));
        }catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSideChainUnbond(){
        SideChainUnBond sideChainUndelegate = new SideChainUnBond();

        //set unbond amount
        sideChainUndelegate.setAmount(new Token("BNB", 5000L));

        //set delegator address
        sideChainUndelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        sideChainUndelegate.setValidatorAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t");

        //set side-chain id
        sideChainUndelegate.setSideChainId("bsc");

        try {
            List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainUnbond(sideChainUndelegate, wallet, createTransactionOptionForTest(), true);
            consolePrintln(transactionMetadatas.get(0));
        }catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSideChainValidator() throws IOException {
        SideChainValidator validator = nodeClient.getSideChainValidator("bsc", decodeAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t"));
        Assert.assertNotNull(validator);
        consolePrintln(validator);
    }

    @Test
    public void testGetSideChainTopValidators() throws IOException {
        List<SideChainValidator> validators = nodeClient.getSideChainTopValidators("bsc", 5);
        for (SideChainValidator validator : validators) {
            consolePrintln(validator);
        }
    }

    @Test
    public void testGetSideChainDelegation() throws IOException {
        SideChainDelegation delegation = nodeClient.getSideChainDelegation("bsc", wallet.getAddressBytes(), decodeAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t"));
        Assert.assertNotNull(delegation);
        consolePrintln(delegation);
    }

    @Test
    public void testGetSideChainDelegations() throws IOException {
        List<SideChainDelegation> delegations = nodeClient.getSideChainDelegations("bsc", wallet.getAddressBytes());
        for (SideChainDelegation delegation : delegations) {
            consolePrintln(delegation);
        }
    }

    @Test
    public void getSideChainRedelegation() throws IOException {
        byte[] srcValidatorAddress = decodeAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t");
        byte[] dstValidatorAddress = decodeAddress("bva10l34nul25nfxyurfkvkyn7z5kumrg0rjun409e");

        SideChainRedelegation redelegation = nodeClient.getSideChainRedelegation("bsc", wallet.getAddressBytes(), srcValidatorAddress, dstValidatorAddress);
        if (redelegation != null) {
            consolePrintln(redelegation);
        }
    }

    @Test
    public void getSideChainRedelegations() throws IOException {
        List<SideChainRedelegation> redelegations = nodeClient.getSideChainRedelegations("bsc", wallet.getAddressBytes());
        for (SideChainRedelegation redelegation : redelegations) {
            consolePrintln(redelegation);
        }
    }

    @Test
    public void getSideChainUnBondingDelegation() throws IOException {
        SideChainUnBondingDelegation unBondingDelegation = nodeClient.getSideChainUnBondingDelegation("bsc", wallet.getAddressBytes(), decodeAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t"));
        consolePrintln(unBondingDelegation);
    }

    @Test
    public void getSideChainUnBondingDelegations() throws IOException {
        List<SideChainUnBondingDelegation> unBondingDelegations = nodeClient.getSideChainUnBondingDelegations("bsc", wallet.getAddressBytes());
        for (SideChainUnBondingDelegation unBondingDelegation : unBondingDelegations) {
            consolePrintln(unBondingDelegation);
        }
    }

    private byte[] decodeAddress(String address){
        return Crypto.decodeAddress(address);
    }

    /**
     * Print message to console for debug, call
     * its toString() method.
     *
     * @param message the specify message object
     * */
    private void consolePrintln(Object message){
        System.out.println(message.toString());
    }

    /**
     * Create a transaction option for test
     *
     * @return TransactionOption
     * */
    private TransactionOption createTransactionOptionForTest(){
        return new TransactionOption("", 0, null);
    }

}
