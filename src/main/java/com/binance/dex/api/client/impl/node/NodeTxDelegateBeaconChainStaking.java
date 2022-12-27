package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.beaconchain.*;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.beaconchain.transaction.*;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValueStr;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.message.sidechain.value.CommissionMsgValue;
import com.binance.dex.api.client.encoding.message.sidechain.value.DescriptionValue;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Francis.Liu
 **/
public class NodeTxDelegateBeaconChainStaking extends NodeTx {

    public NodeTxDelegateBeaconChainStaking(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        super(binanceDexNodeApi, hrp, valHrp);
    }

    public List<TransactionMetadata> createBeaconChainValidator(CreateBeaconChainValidator createBeaconChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        CreateBeaconChainValidatorMessage message = convert(createBeaconChainValidator);
        if (StringUtils.isEmpty(createBeaconChainValidator.getValidatorAddr())) {
            message.setValidatorOperatorAddr(Bech32AddressValue.fromBech32StringWithNewHrp(wallet.getAddress(), valHrp));
        }else{
            message.setValidatorOperatorAddr(Bech32AddressValue.fromBech32StringWithNewHrp(createBeaconChainValidator.getValidatorAddr(), valHrp));
        }
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> editBeaconChainValidator(EditBeaconChainValidator editBeaconChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        EditBeaconChainValidatorMessage message = convert(editBeaconChainValidator);
        if (StringUtils.isEmpty(editBeaconChainValidator.getValidatorAddress())) {
            message.setValidatorOperatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(wallet.getAddress(), valHrp));
        }else{
            message.setValidatorOperatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(editBeaconChainValidator.getValidatorAddress(), valHrp));
        }
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> beaconChainDelegate(BeaconChainDelegate beaconChainDelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        BeaconChainDelegateMessage message = convert(beaconChainDelegate);
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> beaconChainRedelegate(BeaconChainRedelegate beaconChainRedelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        BeaconChainRedelegateMessage message = convert(beaconChainRedelegate);
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> beaconChainUndelegate(BeaconChainUndelegate beaconChainUndelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        BeaconChainUndelegateMessage message = convert(beaconChainUndelegate);
        return broadcast(message, wallet, options, sync);
    }

    @VisibleForTesting
    CreateBeaconChainValidatorMessage convert(CreateBeaconChainValidator createBeaconChainValidator){
        CreateBeaconChainValidatorMessage message = new CreateBeaconChainValidatorMessage();

        if (createBeaconChainValidator.getDescription() != null){
            message.setDescription(convert(createBeaconChainValidator.getDescription()));
        }
        if (createBeaconChainValidator.getCommission() != null){
            message.setCommission(convert(createBeaconChainValidator.getCommission()));
        }

        message.setDelegatorAddr(Bech32AddressValue.fromBech32String(createBeaconChainValidator.getDelegatorAddr()));

        if (createBeaconChainValidator.getDelegation() != null){
            message.setDelegation(convert(createBeaconChainValidator.getDelegation()));
        }

        message.setPubKey(createBeaconChainValidator.getPubKey());
        return message;
    }

    @VisibleForTesting
    EditBeaconChainValidatorMessage convert(EditBeaconChainValidator editBeaconChainValidator){
        EditBeaconChainValidatorMessage message = new EditBeaconChainValidatorMessage();

        if (editBeaconChainValidator.getDescription() != null){
            message.setDescription(convert(editBeaconChainValidator.getDescription()));
        }

        message.setCommissionRate(Dec.newInstance(editBeaconChainValidator.getCommissionRate()));
        if (editBeaconChainValidator.getPubKey() != null){
            message.setPubKey(editBeaconChainValidator.getPubKey());
        }

        return message;
    }

    private byte[] decodeHexAddress(String address){
        String addr = address;
        if (addr.startsWith("0x")){
            addr = address.substring(2);
        }
        return Hex.decode(addr);
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

    private BeaconChainDelegateMessage convert(BeaconChainDelegate beaconChainDelegate){
        BeaconChainDelegateMessage message = new BeaconChainDelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(beaconChainDelegate.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(beaconChainDelegate.getValidatorAddress(), valHrp));

        if (beaconChainDelegate.getDelegation() != null) {
            message.setDelegation(convert(beaconChainDelegate.getDelegation()));
        }

        return message;
    }

    private BeaconChainRedelegateMessage convert(BeaconChainRedelegate redelegate){
        BeaconChainRedelegateMessage message = new BeaconChainRedelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(redelegate.getDelegatorAddress()));
        message.setSrcValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(redelegate.getSrcValidatorAddress(), valHrp));
        message.setDstValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(redelegate.getDstValidatorAddress(), valHrp));
        if (redelegate.getAmount() != null) {
            message.setAmount(convert(redelegate.getAmount()));
        }
        return message;
    }

    private BeaconChainUndelegateMessage convert(BeaconChainUndelegate unBond){
        BeaconChainUndelegateMessage message = new BeaconChainUndelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(unBond.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(unBond.getValidatorAddress(), valHrp));
        if (unBond.getAmount() != null) {
            message.setAmount(convert(unBond.getAmount()));
        }
        return message;
    }


}
