package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.domain.stake.sidechain.*;
import com.binance.dex.api.client.encoding.message.*;
import com.binance.dex.api.client.encoding.message.sidechain.SideChainIds;
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

    private String jackPrivKey = "bce1c934173e84cb4a0abf06b371f7568d3c4686da8f3ea7e1e8d230ae681920";
    private String rosePrivKey = "50111155e4d9adca98c3f9318e896aedeac7fbec8c9f416af076ff042c56b4c5";
    private String markPrivKey = "68931b8fe68ebec5b8fe81420b8de3d47f4ad1dd01619c7e0c93ce9c3a0e3d22";

    private String jackValidator = "bva1lrzg56jhtkqu7fmca3394vdx00r7apx4gjdzy2";
    private String roseValidator = "bva1rxnydtfjccaz2tck7wrentntdylrnnqzmspush";

    private final Wallet wallet = new Wallet(markPrivKey, BinanceDexEnvironment.PROD);

    @Before
    public void setup() {
        nodeClient = BinanceDexApiClientFactory.newInstance().newNodeRpcClient(BinanceDexEnvironment.TEST_NET.getNodeUrl()
                ,BinanceDexEnvironment.TEST_NET.getHrp(), BinanceDexEnvironment.TEST_NET.getValHrp());
    }

    @Test
    public void testCreateSideChainValidator() throws IOException, NoSuchAlgorithmException {
        CreateSideChainValidator createSideChainValidator = new CreateSideChainValidator();

        //create and set description
        Description validatorDescription = new Description();
        validatorDescription.setMoniker("rose-moniker");
        validatorDescription.setIdentity("rose id");
        validatorDescription.setWebsite("https://www.rose.com");
        validatorDescription.setDetails("This is rose validator");

        createSideChainValidator.setDescription(validatorDescription);

        //create and set commission
        Commission commission = new Commission();
        commission.setRate(5L);
        commission.setMaxRate(1000L);
        commission.setMaxChangeRate(10L);

        createSideChainValidator.setCommission(commission);

        //set delegator address, here use self address
        createSideChainValidator.setDelegatorAddr(wallet.getAddress());

        //set delegation token, here use 1000000 BNB
        Token delegationToken = new Token();
        delegationToken.setDenom("BNB");
        delegationToken.setAmount(1000000000000L);
        createSideChainValidator.setDelegation(delegationToken);

        //set side-chain id
        createSideChainValidator.setSideChainId(SideChainIds.RIALTO);

        //set side-chain validator cons address
        createSideChainValidator.setSideConsAddr("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791");

        //set side-chain validator fee address
        createSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        TransactionOption options = new TransactionOption("", 0, null);

        List<TransactionMetadata> transactionMetadatas = nodeClient.createSideChainValidator(createSideChainValidator, wallet, options, true);
        Assert.assertEquals(1, transactionMetadatas.size());
    }

    @Test
    public void testEditSideChainValidator() throws IOException, NoSuchAlgorithmException {
        EditSideChainValidator editSideChainValidator = new EditSideChainValidator();

        //set new description if needed
        Description description = new Description();
        description.setMoniker("new Moniker");
        editSideChainValidator.setDescription(description);

        //set new rate if needed
        editSideChainValidator.setCommissionRate(1L);

        //set new fee address if needed
        editSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        editSideChainValidator.setSideChainId(SideChainIds.RIALTO);

        List<TransactionMetadata> transactionMetadatas = nodeClient.editSideChainValidator(editSideChainValidator, wallet, createTransactionOptionForTest(), true);
        Assert.assertEquals(1, transactionMetadatas.size());
    }

    @Test
    public void testSideChainDelegate() throws IOException, NoSuchAlgorithmException {
        SideChainDelegate sideChainDelegate = new SideChainDelegate();

        //set delegate token
        Token delegation = new Token("BNB", 123456700L);
        sideChainDelegate.setDelegation(delegation);

        //set delegator address, here is self
        sideChainDelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        sideChainDelegate.setValidatorAddress(roseValidator);

        //set side-chain id
        sideChainDelegate.setSideChainId(SideChainIds.RIALTO);

        List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainDelegate(sideChainDelegate, wallet, createTransactionOptionForTest(), true);
        Assert.assertEquals(1, transactionMetadatas.size());
    }

    @Test
    public void testSideChainRedelegate() throws IOException, NoSuchAlgorithmException {
        SideChainRedelegate redelegate = new SideChainRedelegate();

        //set redelegate amount
        redelegate.setAmount(new Token("bnb".toUpperCase(), 100000000L));

        //set delegator address
        redelegate.setDelegatorAddress(wallet.getAddress());

        //set source validator address
        redelegate.setSrcValidatorAddress(jackValidator);

        //set destination validator address
        redelegate.setDstValidatorAddress(roseValidator);

        //set side-chain id
        redelegate.setSideChainId(SideChainIds.RIALTO);

        List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainRedelagate(redelegate, wallet, createTransactionOptionForTest(), true);
        Assert.assertEquals(1, transactionMetadatas.size());
    }

    @Test
    public void testSideChainUnbond() throws IOException, NoSuchAlgorithmException {
        SideChainUnBond sideChainUndelegate = new SideChainUnBond();

        //set unbond amount
        sideChainUndelegate.setAmount(new Token("BNB", 1000L));

        //set delegator address
        sideChainUndelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        sideChainUndelegate.setValidatorAddress(jackValidator);

        //set side-chain id
        sideChainUndelegate.setSideChainId(SideChainIds.RIALTO);

        List<TransactionMetadata> transactionMetadatas = nodeClient.sideChainUnbond(sideChainUndelegate, wallet, createTransactionOptionForTest(), true);
        Assert.assertEquals(1, transactionMetadatas.size());
    }

    @Test
    public void testGetSideChainValidator() throws IOException {
        SideChainValidator validator = nodeClient.getSideChainValidator(SideChainIds.RIALTO, jackValidator);
        if (validator != null){
            Assert.assertNotNull(validator.getOperatorAddr());
        }
    }

    @Test
    public void testGetSideChainTopValidators() throws IOException {
        List<SideChainValidator> validators = nodeClient.getSideChainTopValidators(SideChainIds.RIALTO, 5);
        Assert.assertNotNull(validators);
    }

    @Test
    public void testGetSideChainDelegation() throws IOException {
        SideChainDelegation delegation = nodeClient.getSideChainDelegation(SideChainIds.RIALTO, wallet.getAddress(), roseValidator);
        if (delegation != null) {
            Assert.assertNotNull(delegation.getDelegation());
            Assert.assertNotNull(delegation.getBalance());
        }
    }

    @Test
    public void testGetSideChainDelegations() throws IOException {
        List<SideChainDelegation> delegations = nodeClient.getSideChainDelegations(SideChainIds.RIALTO, wallet.getAddress());
        Assert.assertNotNull(delegations);
    }

    @Test
    public void getSideChainRedelegation() throws IOException {
        SideChainRedelegation redelegation = nodeClient.getSideChainRedelegation(SideChainIds.RIALTO, wallet.getAddress(), jackValidator, roseValidator);
        if (redelegation != null) {
            Assert.assertNotNull(redelegation.getDelegatorAddress());
        }
    }

    @Test
    public void getSideChainRedelegations() throws IOException {
        List<SideChainRedelegation> redelegations = nodeClient.getSideChainRedelegations(SideChainIds.RIALTO, wallet.getAddress());
        Assert.assertNotNull(redelegations);
    }

    @Test
    public void getSideChainUnBondingDelegation() throws IOException {
        UnBondingDelegation unBondingDelegation = nodeClient.getSideChainUnBondingDelegation(SideChainIds.RIALTO, wallet.getAddress(), jackValidator);
        if (unBondingDelegation != null) {
            Assert.assertNotNull(unBondingDelegation.getDelegatorAddress());
        }
    }

    @Test
    public void getSideChainUnBondingDelegations() throws IOException {
        List<UnBondingDelegation> unBondingDelegations = nodeClient.getSideChainUnBondingDelegations(SideChainIds.RIALTO, wallet.getAddress());
        Assert.assertNotNull(unBondingDelegations);
    }

    @Test
    public void testGetSideChainUnBondingDelegationsByValidator() throws IOException {
        List<UnBondingDelegation> unBondingDelegations = nodeClient.getSideChainUnBondingDelegationsByValidator(SideChainIds.RIALTO, jackValidator);
        Assert.assertNotNull(unBondingDelegations);
    }

    @Test
    public void testGetSideChainRedelegationsByValidator() throws IOException {
        List<SideChainRedelegation> redelegations = nodeClient.getSideChainRedelegationsByValidator(SideChainIds.RIALTO, jackValidator);
        Assert.assertNotNull(redelegations);
    }

    @Test
    public void testGetSideChainPool() throws IOException {
        Pool pool = nodeClient.getSideChainPool(SideChainIds.RIALTO);
        if (pool != null){
            Assert.assertTrue(pool.getBondedTokens() >= 0L);
        }
    }

    @Test
    public void testGetSideChainValidatorsCount() throws IOException {
        long count = nodeClient.getAllSideChainValidatorsCount(SideChainIds.RIALTO, false);
        Assert.assertTrue(count >= 0);
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
