package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiNodeClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.Infos;
import com.binance.dex.api.client.domain.StakeValidator;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.Pool;
import com.binance.dex.api.client.domain.stake.beaconchain.*;
import com.binance.dex.api.client.domain.stake.sidechain.CreateSideChainValidator;
import com.binance.dex.api.client.domain.stake.sidechain.EditSideChainValidator;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.sidechain.SideChainIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francis.Liu
 **/
public class BeaconChainStakingNodeExample {

    private Logger logger = LoggerFactory.getLogger(BeaconChainStakingNodeExample.class);

    private BinanceDexApiNodeClient nodeClient = null;

    private BinanceDexEnvironment env;
    private Wallet wallet;

    private List<String> getMnemonicCodeWords(){
        String mnemonicCodeWordStr = "bottom quick strong ranch section decide pepper broken oven demand coin run jacket curious business achieve mule bamboo remain vote kid rigid bench rubber";
        List<String> mnemonicCodeWords = new ArrayList<String>();
        String[] mnemonicCodeWordStrs = mnemonicCodeWordStr.split(" ");
        for (String mnemonicCodeWord : mnemonicCodeWordStrs) {
            mnemonicCodeWords.add(mnemonicCodeWord);
        }
        return mnemonicCodeWords;
    }

    @Before
    public void setup() {
        env = BinanceDexEnvironment.LOCAL_NET;

        nodeClient = BinanceDexApiClientFactory.newInstance().newNodeRpcClient(env.getNodeUrl()
                ,env.getHrp(), env.getValHrp());
        try {
            wallet = Wallet.createWalletFromMnemonicCode(this.getMnemonicCodeWords(), env);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    public void testCreateSideChainValidator() throws IOException, NoSuchAlgorithmException {
//        CreateSideChainValidator createSideChainValidator = new CreateSideChainValidator();
//
//        //create and set description
//        Description validatorDescription = new Description();
//        validatorDescription.setMoniker("rose-moniker");
//        validatorDescription.setIdentity("rose id");
//        validatorDescription.setWebsite("https://www.rose.com");
//        validatorDescription.setDetails("This is rose validator");
//
//        createSideChainValidator.setDescription(validatorDescription);
//
//        //create and set commission
//        Commission commission = new Commission();
//        commission.setRate(5L);
//        commission.setMaxRate(1000L);
//        commission.setMaxChangeRate(10L);
//
//        createSideChainValidator.setCommission(commission);
//
//        //set delegator address, here use self address
//        createSideChainValidator.setDelegatorAddr(wallet.getAddress());
//
//        //set delegation token, here use 1000000 BNB
//        Token delegationToken = new Token();
//        delegationToken.setDenom("BNB");
//        delegationToken.setAmount(2000000000000L);
//
//        createSideChainValidator.setDelegation(delegationToken);
//        //set side-chain id
//        createSideChainValidator.setSideChainId("bsc");
//
//        //set side-chain validator cons address
//        createSideChainValidator.setSideConsAddr("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791");
//
//        //set side-chain validator fee address
//        createSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");
//
//        TransactionOption options = new TransactionOption("", 0, null);
//
//        List<TransactionMetadata> transactionMetadatas = nodeClient.createSideChainValidator(createSideChainValidator, wallet, options, true);
//        Assert.assertEquals(1, transactionMetadatas.size());
//    }
//
//    @Test
//    public void testEditSideChainValidator() throws IOException, NoSuchAlgorithmException {
//        EditSideChainValidator editSideChainValidator = new EditSideChainValidator();
//
//        //set new description if needed
//        Description description = new Description();
//        description.setMoniker("new Moniker");
//        editSideChainValidator.setDescription(description);
//
//        //set new rate if needed
////        editSideChainValidator.setCommissionRate(1L);
//
//        //set new fee address if needed
//        editSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");
//
//        editSideChainValidator.setSideChainId("bsc");
//
//        List<TransactionMetadata> transactionMetadatas = nodeClient.editSideChainValidator(editSideChainValidator, wallet, createTransactionOptionForTest(), true);
//        Assert.assertEquals(1, transactionMetadatas.size());
//    }


    private int pubKeyIndex = 0;

    private String genPubKey() throws IOException {
        ArrayList<String> pubKeys = new ArrayList<>();
        pubKeys.add("bcap1zcjduepq2equ54uaf02j98y9k5ksq2aau9ajns22gfkwgv8ut6ngx5cyteaq6jh5kf");
        pubKeys.add("bcap1zcjduepq6hpw8zdrqdj2cd07p07pktp2lym7fkm9hn5dkwqczm3rplcupgqsq0w7ev");
        pubKeys.add("bcap1zcjduepqcr6zgwf95lmxyj0tq6yh4dz4p3r0dkl4m0zrwj9jzgr2pmmct3qqtepuh3");
        pubKeys.add("bcap1zcjduepqq8600wvagq3n6j3ldk223tzkw433jjwywj4wfjevx74hvtztp8tsndacz7");
        pubKeys.add("bcap1zcjduepqhrmhu3a7f9z4v3a50z3pa39p872hgqud2t3quh2zsvx3fj0plu3qmar9mh");
        String pubKey = pubKeys.get(pubKeyIndex);
        pubKeyIndex = pubKeyIndex + 1;
        return pubKey;
    }

    private Wallet genWalletWithBNB(Wallet tokenFrom) throws IOException, NoSuchAlgorithmException {
        Wallet newWallet = Wallet.createRandomWallet(env);

        String symbol = "BNB";
        Transfer transfer = new Transfer();
        transfer.setCoin(symbol);
        transfer.setFromAddress(tokenFrom.getAddress());
        transfer.setToAddress(newWallet.getAddress());
        transfer.setAmount("150");

        TransactionOption options = new TransactionOption("test", 1, null);
        List<TransactionMetadata> resp = nodeClient.transfer(transfer, tokenFrom, options, true);
        return newWallet;
    }


    @Test
    public void testStaking() throws IOException, NoSuchAlgorithmException {
        logger.info("-------------------- begin staking test -------------------");
        Wallet bob = Wallet.createWalletFromMnemonicCode(this.getMnemonicCodeWords(), env);
        logger.info(String.format("chainId = %s \n", bob.getChainId()));

        Infos infos = nodeClient.getNodeInfo();
        logger.info(String.format("infos = %s\n", infos));
        logger.info(String.format("bob address %s \n", bob.getAddress()));

        // create a random account
        Wallet validator0 = this.genWalletWithBNB(bob);
        logger.info(String.format("validator0 address %s \n", validator0.getAddress()));

        // validators
        List<StakeValidator> validators = nodeClient.getStakeValidator();
        logger.info(String.format("validators %s \n", validators));
        logger.info(String.format("validators length %s \n", validators.size()));
        Assert.assertTrue("validators len should be >= 1", validators.size() >= 1);

        // query validators count (including jailed)
        long validatorCount = nodeClient.getAllValidatorsCount(true);
        logger.info(String.format("validators count: %d \n", validatorCount));

        // query validators count (excluding jailed)
        long validatorCountWithoutJail = nodeClient.getAllValidatorsCount(false);
        logger.info(String.format("validators count: %d \n", validatorCountWithoutJail));

        Assert.assertEquals("there is no jailed validators yet", validatorCount, validatorCountWithoutJail);

        // create validator
        String consensusPubKey = genPubKey();
        this.createBeaconChainValidator(validator0, consensusPubKey);
        long validatorCountAfterCreate = nodeClient.getAllValidatorsCount(true);
        logger.info(String.format("validators count: %d \n", validatorCountAfterCreate));
        Assert.assertEquals("validators count should be +1", validatorCountAfterCreate, validatorCount + 1);

        // query top validators
        int topValidatorNum = 3;
        List<BeaconChainValidator> topValidators = nodeClient.getTopValidators(topValidatorNum);
        logger.info(String.format("top validators %s \n", topValidators));
        Assert.assertTrue("top validators should be 1", topValidators.size() == topValidatorNum);
        BeaconChainValidator topValidator = topValidators.get(0);

        // query validator
        String validator0ValAddr = Crypto.getAddressFromPrivateKey(validator0.getPrivateKey(), env.getValHrp());
        logger.info(String.format("validator addr %s \n", validator0ValAddr));
        BeaconChainValidator validator = nodeClient.getValidator(validator0ValAddr);
        logger.info(String.format("query validator: %s \n", validator));
        Assert.assertNotNull("validator should not be nil", validator);
        Assert.assertEquals("validator address should be equal", validator.getFeeAddr(), validator0.getAddress());
        Assert.assertEquals("validator tokens should be 123e8", validator.getTokens(),  12300000000L);
        Assert.assertEquals("validator description should be equal", validator.getDescription().getMoniker(),  "node1");
        Assert.assertEquals("validator rate should be equal", validator.getCommission().getRate(),  1);
        Assert.assertEquals("validator consensusPubKey should be equal", new String(validator.getConsPubKey()),  consensusPubKey);

        // edit validator todo
        String consensusPubKey2 = genPubKey();
//        this.editBeaconChainValidator(validator0, consensusPubKey2);

        // check edit validator change
//        validator = nodeClient.getValidator(validator0.getAddress());
//        logger.info(String.format("query validator: %s \n", validator));
//        Assert.assertNotNull("validator should not be nil", validator);
//        Assert.assertEquals("validator address should be equal", validator.getFeeAddr(), validator0.getAddress());
//        Assert.assertEquals("validator tokens should be 123e8", validator.getTokens(),  12300000000L);
//        Assert.assertEquals("validator description should be equal", validator.getDescription().getMoniker(),  "node1_v2");
//        Assert.assertEquals("validator rate should be equal", validator.getCommission().getRate(),  1);
//        Assert.assertEquals("validator consensusPubKey should be equal", new String(validator.getConsPubKey()),  consensusPubKey2);
        long tokenBeforeDelegate = validator.getTokens();

        // delegate
        Wallet delegator = this.genWalletWithBNB(bob);
        String delegatorValAddr = Crypto.getAddressFromPrivateKey(delegator.getPrivateKey(), env.getValHrp());
        long delegateAmount = 500000000L;
        this.beaconChainDelegate(delegator, validator0.getAddress(), delegateAmount);

        // check delegation
        validator = nodeClient.getValidator(validator0.getAddress());
        logger.info(String.format("query validator: %s \n", validator));
        long tokenAfterDelegate = validator.getTokens();
        Assert.assertEquals("delegate tokens should be equal", tokenAfterDelegate - tokenBeforeDelegate, delegateAmount);

        // query delegation
        BeaconChainDelegation delegationQuery = nodeClient.getDelegation(delegator.getAddress(), validator0ValAddr);
        logger.info(String.format("query delegation: %s \n", delegationQuery));
        Assert.assertEquals("delegation shares should be equal", delegationQuery.getDelegation().getShares(), delegateAmount);

        // query delegations
        List<BeaconChainDelegation> delegations = nodeClient.getDelegations(delegator.getAddress());
        logger.info(String.format("query delegations: %s \n", delegations));

        // check redelegate preparation
        String topValAddr = topValidator.getOperatorAddr();
        BeaconChainValidator topValidatorBeforeRedelegate = nodeClient.getValidator(topValAddr);
        logger.info(String.format("top validator before redelegate: %s \n", topValidatorBeforeRedelegate));

        // redelegate from validator0 to top validator, should success immediately
        long redelegateAmount = 200000000L;
        this.beaconChainRedelegate(delegator, validator0.getAddress(), topValAddr, redelegateAmount);
        BeaconChainValidator topValidatorAfterRedelegate = nodeClient.getValidator(topValAddr);
        logger.info(String.format("top validator after redelegate: %s \n", topValidatorAfterRedelegate));
        Assert.assertEquals("redelegate tokens should be equal", topValidatorAfterRedelegate.getTokens() - topValidatorBeforeRedelegate.getTokens(), redelegateAmount);

        // undelegate
        this.beaconChainUndelegate(delegator, topValAddr, redelegateAmount);
        BeaconChainValidator topValidatorAfterUndelegate = nodeClient.getValidator(topValAddr);
        logger.info(String.format("top validator after undelegate: %s \n", topValidatorAfterUndelegate));
        Assert.assertEquals("check undelegation token change", topValidatorAfterUndelegate.getTokens(), topValidatorBeforeRedelegate.getTokens());

        // query pool
        Pool pool = nodeClient.getPool();
        logger.info(String.format("pool: %s \n", pool));

        // query unbonding delegation
        BeaconChainUnBondingDelegation unbondingDelegation = nodeClient.getUnBondingDelegation(delegator.getAddress(), topValAddr);
        logger.info(String.format("query unbonding delegation: %s \n", unbondingDelegation));

        // query unbonding delegations
        List<BeaconChainUnBondingDelegation> unbondingDelegations = nodeClient.getUnBondingDelegations(delegator.getAddress());
        logger.info(String.format("query unbonding delegations: %s \n", unbondingDelegations));

        // query unbonding delegations by validator
        List<BeaconChainUnBondingDelegation> unbondingDelegationsByValidator = nodeClient.getUnBondingDelegationsByValidator(topValAddr);
        logger.info(String.format("query unbonding delegations by validator: %s \n", unbondingDelegationsByValidator));

        // delegate to top validator and then redelegate
        Wallet delegator0 = genWalletWithBNB(bob);
        this.beaconChainDelegate(delegator0, topValAddr, delegateAmount);
        logger.info(String.format("dest validator: %s \n", topValAddr));
        logger.info(String.format("validator0 val address: %s \n", validator0.getAddress()));
        logger.info(String.format("delegator address: %s \n", delegator0.getAddress()));

        this.beaconChainRedelegate(delegator0, topValAddr, validator0.getAddress(), delegateAmount);

        // query redelegation
        BeaconChainRedelegation redelegation = nodeClient.getRedelegation(delegator0.getAddress(), topValAddr, validator0ValAddr);
        logger.info(String.format("query redelegation: %s \n", redelegation));
        Assert.assertNotNull("redelegation should not be nil", redelegation);

        // query redelegations
        List<BeaconChainRedelegation> redelegations = nodeClient.getRedelegations(delegator0.getAddress());
        logger.info(String.format("query redelegations: %s \n", redelegations));
        Assert.assertTrue("redelegations should not be empty", redelegations.size() > 0);

        // query redelegations by source validator
        List<BeaconChainRedelegation> redelegationsByValidator = nodeClient.getRedelegationsByValidator(topValAddr);
        logger.info(String.format("query redelegations by validator: %s \n", redelegationsByValidator));
        Assert.assertTrue("redelegations by validator should not be empty", redelegationsByValidator.size() > 0);


        logger.info("-------------------- end staking test -------------------");
    }




    public void createBeaconChainValidator(Wallet wallet, String pubKey) throws IOException, NoSuchAlgorithmException {
        CreateBeaconChainValidator createBeaconChainValidator = new CreateBeaconChainValidator();

        //create and set description
        Description validatorDescription = new Description();
        validatorDescription.setMoniker("node1");
        validatorDescription.setIdentity("rose id");
        validatorDescription.setWebsite("https://www.rose.com");
        validatorDescription.setDetails("This is rose validator");

        createBeaconChainValidator.setDescription(validatorDescription);

        //create and set commission
        Commission commission = new Commission();
        commission.setRate(1);
        commission.setMaxRate(1);
        commission.setMaxChangeRate(1);

        createBeaconChainValidator.setCommission(commission);

        //set delegator address, here use self address
        createBeaconChainValidator.setDelegatorAddr(wallet.getAddress());

        //set delegation token, here use 1000000 BNB
        Token delegationToken = new Token();
        delegationToken.setDenom("BNB");
        delegationToken.setAmount(12300000000L);
        createBeaconChainValidator.setDelegation(delegationToken);


        createBeaconChainValidator.setPubKey(pubKey);

        TransactionOption options = new TransactionOption("", 0, null);

        List<TransactionMetadata> transactionMetadatas = nodeClient.createValidator(createBeaconChainValidator, wallet, options, true);
        logger.info(String.format("create validator res %s \n", transactionMetadatas));
        Assert.assertEquals(1, transactionMetadatas.size());
        Assert.assertEquals(0, transactionMetadatas.get(0).getCode());
    }


    public void editBeaconChainValidator(Wallet wallet, String pubKey) throws IOException, NoSuchAlgorithmException {
        EditBeaconChainValidator editBeaconChainValidator = new EditBeaconChainValidator();

        //set new description if needed
        Description validatorDescription = new Description();
        validatorDescription.setMoniker("node1_v2");

        editBeaconChainValidator.setDescription(validatorDescription);

//        editBeaconChainValidator.setCommissionRate(1L);

        editBeaconChainValidator.setPubKey(pubKey);

        List<TransactionMetadata> transactionMetadatas = nodeClient.editValidator(editBeaconChainValidator, wallet, createTransactionOptionForTest(), true);
        logger.info(String.format("edit validator res %s \n", transactionMetadatas));
        Assert.assertEquals(1, transactionMetadatas.size());
        Assert.assertEquals(0, transactionMetadatas.get(0).getCode());
    }

    public void beaconChainDelegate(Wallet wallet, String validatorAddress, Long amount) throws IOException, NoSuchAlgorithmException {
        BeaconChainDelegate beaconChainDelegate = new BeaconChainDelegate();

        //set delegate token
        Token delegation = new Token("BNB", amount);
        beaconChainDelegate.setDelegation(delegation);

        //set delegator address, here is self
        beaconChainDelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        beaconChainDelegate.setValidatorAddress(validatorAddress);


        List<TransactionMetadata> transactionMetadatas = nodeClient.delegate(beaconChainDelegate, wallet, createTransactionOptionForTest(), true);
        logger.info(String.format("delegate validator res %s \n", transactionMetadatas));
        Assert.assertEquals(1, transactionMetadatas.size());
        Assert.assertEquals(0, transactionMetadatas.get(0).getCode());
    }

    public void beaconChainRedelegate(Wallet wallet, String srcValidatorAddress, String dstValidatorAddress, Long amount) throws IOException, NoSuchAlgorithmException {
        BeaconChainRedelegate redelegate = new BeaconChainRedelegate();

        //set redelegate amount
        redelegate.setAmount(new Token("bnb".toUpperCase(), amount));

        //set delegator address
        redelegate.setDelegatorAddress(wallet.getAddress());

        //set source validator address
        redelegate.setSrcValidatorAddress(srcValidatorAddress);

        //set destination validator address
        redelegate.setDstValidatorAddress(dstValidatorAddress);

        List<TransactionMetadata> transactionMetadatas = nodeClient.redelegate(redelegate, wallet, createTransactionOptionForTest(), true);
        logger.info(String.format("redelegate res %s \n", transactionMetadatas));
        Assert.assertEquals(1, transactionMetadatas.size());
        Assert.assertEquals(0, transactionMetadatas.get(0).getCode());
    }

    public void beaconChainUndelegate(Wallet wallet, String validatorAddress, Long amount) throws IOException, NoSuchAlgorithmException {
        BeaconChainUndelegate beaconChainUndelegate = new BeaconChainUndelegate();

        //set unbond amount
        beaconChainUndelegate.setAmount(new Token("BNB", amount));

        //set delegator address
        beaconChainUndelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        beaconChainUndelegate.setValidatorAddress(validatorAddress);


        List<TransactionMetadata> transactionMetadatas = nodeClient.undelegate(beaconChainUndelegate, wallet, createTransactionOptionForTest(), true);
        logger.info(String.format("undelegate res %s \n", transactionMetadatas));
        Assert.assertEquals(1, transactionMetadatas.size());
        Assert.assertEquals(0, transactionMetadatas.get(0).getCode());
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
