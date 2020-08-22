package com.binance.dex.api.client.encoding;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.TransactionConverter;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.sidechain.*;
import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.message.MessageType;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.bridge.*;
import com.binance.dex.api.client.encoding.message.common.*;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.*;
import com.binance.dex.api.client.encoding.message.sidechain.value.CommissionMsgValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.RedelegationValue;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class AminoTest {

    private final Amino amino = new Amino();

    private final Wallet wallet = new Wallet("bce1c934173e84cb4a0abf06b371f7568d3c4686da8f3ea7e1e8d230ae681920", BinanceDexEnvironment.PROD);
    private final TransactionConverter transactionConverter = new TransactionConverter("bnb", "bva");

    @Test
    public void testDecodeRedelegation() throws IOException {
        String hex = "2a08a554120c08c08994f50510a0b0df93011a080a03424e4210904e22090a03424e4210d099052a0a3232";
        RedelegationValue value = new RedelegationValue();
        amino.decodeWithLengthPrefix(Hex.decode(hex), value);
        Assert.assertNotNull(value.getSharesDst());
    }

    public void testClaim() throws IOException {
        ClaimMsgMessage message = new ClaimMsgMessage();
        message.setChainId(ClaimTypes.ClaimTypeTransferIn);
        message.setPayload("claim content".getBytes());
        message.setSequence(1);
        message.setValidatorAddress(Bech32AddressValue.fromBech32String("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t"));
        byte[] data = amino.encode(message, MessageType.Claim.getTypePrefixBytes(), false);
        Transaction transaction = transactionConverter.convert(data);
        Assert.assertNotNull(transaction.getRealTx());
    }

    @Test
    public void testTransferOut() throws IOException {
        TransferOutMsgMessage message = new TransferOutMsgMessage();
        message.setAmount(new CoinValue("BNB", 1000000L));
        message.setExpireTime(System.currentTimeMillis());
        message.setFrom(Bech32AddressValue.fromBech32StringWithNewHrp("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t", "bnb"));
        message.setToAddress(EthAddressValue.from("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08"));
        byte[] data = amino.encode(message, MessageType.TransferOut.getTypePrefixBytes(), false);
        Transaction transaction = transactionConverter.convert(data);
        Assert.assertNotNull(transaction.getRealTx());
    }

    @Test
    public void testBind() throws IOException {
        BindMsgMessage message = new BindMsgMessage();
        message.setAmount(1000000L);
        message.setContractAddress(EthAddressValue.from("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08"));
        message.setContractDecimal(8);
        message.setExpireTime(System.currentTimeMillis());
        message.setFrom(Bech32AddressValue.fromBech32StringWithNewHrp("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t", "bnb"));
        message.setSymbol("BNB");
        byte[] data = amino.encode(message, MessageType.Bind.getTypePrefixBytes(), false);
        Transaction transaction = transactionConverter.convert(data);
        Assert.assertNotNull(transaction.getRealTx());
    }

    @Test
    public void testUnBind() throws IOException {
        UnbindMsgMessage message = new UnbindMsgMessage();
        message.setFrom(Bech32AddressValue.fromBech32StringWithNewHrp("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t", "bnb"));
        message.setSymbol("BNB");
        byte[] data = amino.encode(message, MessageType.UnBind.getTypePrefixBytes(), false);
        Transaction transaction = transactionConverter.convert(data);
        Assert.assertNotNull(transaction.getRealTx());
    }

    @Test
    public void testCreateSideChainValidator() throws IOException {
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

        createSideChainValidator.setValidatorAddr(wallet.getAddress());

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

        byte[] msg = amino.encode(convert(createSideChainValidator), MessageType.CreateSideChainValidator.getTypePrefixBytes(), false);

        Transaction tx = transactionConverter.convert(msg);
        Assert.assertNotNull(tx.getRealTx());
    }

    @Test
    public void testDecodeEditSideChainValidator() throws IOException {
        EditSideChainValidator editSideChainValidator = new EditSideChainValidator();

        //set new description if needed
        Description description = new Description();
        description.setMoniker("new Moniker");
        editSideChainValidator.setDescription(description);

        editSideChainValidator.setValidatorAddress(wallet.getAddress());

        //set new rate if needed
        editSideChainValidator.setCommissionRate(1L);

        //set new fee address if needed
        editSideChainValidator.setSideFeeAddr("0xd1B22dCC24C55f4d728E7aaA5c9b5a22e1512C08");

        editSideChainValidator.setSideChainId("bsc");

        EditSideChainValidatorMessage message = new EditSideChainValidatorMessage();
        byte[] msg = amino.encode(convert(editSideChainValidator), MessageType.EditSideChainValidator.getTypePrefixBytes(), false);

        Transaction tx = transactionConverter.convert(msg);
        Assert.assertNotNull(tx.getRealTx());
    }

    @Test
    public void testDecodeSideChainDelegate() throws IOException {
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

        byte[] msg = amino.encode(convert(sideChainDelegate), MessageType.SideChainDelegate.getTypePrefixBytes(), false);

        Transaction tx = transactionConverter.convert(msg);
        Assert.assertNotNull(tx.getRealTx());
    }

    @Test
    public void testDecodeSideChainRedelegate() throws IOException {
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

        byte[] msg = amino.encode(convert(redelegate), MessageType.SideChainRedelegate.getTypePrefixBytes(), false);

        Transaction tx = transactionConverter.convert(msg);
        Assert.assertNotNull(tx.getRealTx());
    }

    @Test
    public void testDecodeSideChainUnBond() throws IOException {
        SideChainUnBond sideChainUndelegate = new SideChainUnBond();

        //set unbond amount
        sideChainUndelegate.setAmount(new Token("BNB", 5000L));

        //set delegator address
        sideChainUndelegate.setDelegatorAddress(wallet.getAddress());

        //set validator address
        sideChainUndelegate.setValidatorAddress("bva1337r5pk3r6kvs4a8kc3u5yhdjc79c5lg78343t");

        //set side-chain id
        sideChainUndelegate.setSideChainId("bsc");

        byte[] msg = amino.encode(convert(sideChainUndelegate), MessageType.SideChainUndelegate.getTypePrefixBytes(), false);

        Transaction tx = transactionConverter.convert(msg);
        Assert.assertNotNull(tx.getRealTx());
    }

    EditSideChainValidatorMessage convert(EditSideChainValidator editSideChainValidator){
        EditSideChainValidatorMessage message = new EditSideChainValidatorMessage();

        if (editSideChainValidator.getDescription() != null){
            message.setDescription(convert(editSideChainValidator.getDescription()));
        }

        message.setValidatorOperatorAddress(Bech32AddressValue.fromBech32String(editSideChainValidator.getValidatorAddress()));

        message.setCommissionRate(Dec.newInstance(editSideChainValidator.getCommissionRate()));
        message.setSideChainId(editSideChainValidator.getSideChainId());
        message.setSideFeeAddr(decodeHexAddress(editSideChainValidator.getSideFeeAddr()));

        return message;
    }

    CreateSideChainValidatorMessage convert(CreateSideChainValidator createSideChainValidator){
        CreateSideChainValidatorMessage message = new CreateSideChainValidatorMessage();

        if (createSideChainValidator.getDescription() != null){
            message.setDescription(convert(createSideChainValidator.getDescription()));
        }
        if (createSideChainValidator.getCommission() != null){
            message.setCommission(convert(createSideChainValidator.getCommission()));
        }

        message.setDelegatorAddr(Bech32AddressValue.fromBech32String(createSideChainValidator.getDelegatorAddr()));
        message.setValidatorOperatorAddr(Bech32AddressValue.fromBech32String(createSideChainValidator.getValidatorAddr()));

        if (createSideChainValidator.getDelegation() != null){
            message.setDelegation(convert(createSideChainValidator.getDelegation()));
        }

        message.setSideChainId(createSideChainValidator.getSideChainId());
        message.setSideConsAddr(decodeHexAddress(createSideChainValidator.getSideConsAddr()));
        message.setSideFeeAddr(decodeHexAddress(createSideChainValidator.getSideFeeAddr()));
        return message;
    }

    private DescriptionValue convert(Description description){
        DescriptionValue value = new DescriptionValue();
        value.setMoniker(description.getMoniker());
        value.setDetails(description.getDetails());
        value.setIdentity(description.getIdentity());
        value.setWebsite(description.getWebsite());
        return value;
    }

    private CommissionMsgValue convert(Commission commission){
        CommissionMsgValue value = new CommissionMsgValue();
        value.setRate(Dec.newInstance(commission.getRate()));
        value.setMaxRate(Dec.newInstance(commission.getMaxRate()));
        value.setMaxChangeRate(Dec.newInstance(commission.getMaxChangeRate()));
        return value;
    }

    private CoinValueStr convert(Token token){
        CoinValueStr value = new CoinValueStr();
        value.setDenom(token.getDenom());
        value.setAmount(token.getAmount());
        return value;
    }

    private SideChainDelegateMessage convert(SideChainDelegate sideChainDelegate){
        SideChainDelegateMessage message = new SideChainDelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(sideChainDelegate.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32String(sideChainDelegate.getValidatorAddress()));

        if (sideChainDelegate.getDelegation() != null) {
            message.setDelegation(convert(sideChainDelegate.getDelegation()));
        }

        message.setSideChainId(sideChainDelegate.getSideChainId());
        return message;
    }

    private SideChainRedelegateMessage convert(SideChainRedelegate redelegate){
        SideChainRedelegateMessage message = new SideChainRedelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(redelegate.getDelegatorAddress()));
        message.setSrcValidatorAddress(Bech32AddressValue.fromBech32String(redelegate.getSrcValidatorAddress()));
        message.setDstValidatorAddress(Bech32AddressValue.fromBech32String(redelegate.getDstValidatorAddress()));
        if (redelegate.getAmount() != null) {
            message.setAmount(convert(redelegate.getAmount()));
        }
        message.setSideChainId(redelegate.getSideChainId());
        return message;
    }

    private SideChainUndelegateMessage convert(SideChainUnBond unBond){
        SideChainUndelegateMessage message = new SideChainUndelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(unBond.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32String(unBond.getValidatorAddress()));
        if (unBond.getAmount() != null) {
            message.setAmount(convert(unBond.getAmount()));
        }
        message.setSideChainId(unBond.getSideChainId());
        return message;
    }

    private byte[] decodeHexAddress(String address){
        String addr = address;
        if (addr.startsWith("0x")){
            addr = address.substring(2);
        }
        return Hex.decode(addr);
    }

}
