package com.binance.dex.api.client.impl.node;

import com.binance.dex.api.client.BinanceDexNodeApi;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.domain.stake.sidechain.*;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.CoinValueStr;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.*;
import com.binance.dex.api.client.encoding.message.sidechain.value.*;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Fitz.Lu
 **/
public class NodeTxDelegateSideChainStaking extends NodeTx {

    public NodeTxDelegateSideChainStaking(BinanceDexNodeApi binanceDexNodeApi, String hrp, String valHrp) {
        super(binanceDexNodeApi, hrp, valHrp);
    }

    public List<TransactionMetadata> createSideChainValidator(CreateSideChainValidator createSideChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        CreateSideChainValidatorMessage message = convert(createSideChainValidator);
        if (StringUtils.isEmpty(createSideChainValidator.getValidatorAddr())) {
            message.setValidatorOperatorAddr(Bech32AddressValue.fromBech32StringWithNewHrp(wallet.getAddress(), valHrp));
        }else{
            message.setValidatorOperatorAddr(Bech32AddressValue.fromBech32StringWithNewHrp(createSideChainValidator.getValidatorAddr(), valHrp));
        }
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> editSideChainValidator(EditSideChainValidator editSideChainValidator, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        EditSideChainValidatorMessage message = convert(editSideChainValidator);
        if (StringUtils.isEmpty(editSideChainValidator.getValidatorAddress())) {
            message.setValidatorOperatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(wallet.getAddress(), valHrp));
        }else{
            message.setValidatorOperatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(editSideChainValidator.getValidatorAddress(), valHrp));
        }
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> sideChainDelegate(SideChainDelegate sideChainDelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        SideChainDelegateMessage message = convert(sideChainDelegate);
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> sideChainRedelagate(SideChainRedelegate sideChainRedelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        SideChainRedelegateMessage message = convert(sideChainRedelegate);
        return broadcast(message, wallet, options, sync);
    }

    public List<TransactionMetadata> sideChainUnbond(SideChainUnBond sideChainUndelegate, Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException {
        SideChainUndelegateMessage message = convert(sideChainUndelegate);
        return broadcast(message, wallet, options, sync);
    }

    @VisibleForTesting
    CreateSideChainValidatorMessage convert(CreateSideChainValidator createSideChainValidator){
        CreateSideChainValidatorMessage message = new CreateSideChainValidatorMessage();

        if (createSideChainValidator.getDescription() != null){
            message.setDescription(convert(createSideChainValidator.getDescription()));
        }
        if (createSideChainValidator.getCommission() != null){
            message.setCommission(convert(createSideChainValidator.getCommission()));
        }

        message.setDelegatorAddr(Bech32AddressValue.fromBech32String(createSideChainValidator.getDelegatorAddr()));

        if (createSideChainValidator.getDelegation() != null){
            message.setDelegation(convert(createSideChainValidator.getDelegation()));
        }

        message.setSideChainId(createSideChainValidator.getSideChainId());
        message.setSideConsAddr(decodeHexAddress(createSideChainValidator.getSideConsAddr()));
        message.setSideFeeAddr(decodeHexAddress(createSideChainValidator.getSideFeeAddr()));
        return message;
    }

    @VisibleForTesting
    EditSideChainValidatorMessage convert(EditSideChainValidator editSideChainValidator){
        EditSideChainValidatorMessage message = new EditSideChainValidatorMessage();

        if (editSideChainValidator.getDescription() != null){
            message.setDescription(convert(editSideChainValidator.getDescription()));
        }

        message.setCommissionRate(Dec.newInstance(editSideChainValidator.getCommissionRate()));
        message.setSideChainId(editSideChainValidator.getSideChainId());
        message.setSideFeeAddr(decodeHexAddress(editSideChainValidator.getSideFeeAddr()));

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

    private SideChainDelegateMessage convert(SideChainDelegate sideChainDelegate){
        SideChainDelegateMessage message = new SideChainDelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(sideChainDelegate.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(sideChainDelegate.getValidatorAddress(), valHrp));

        if (sideChainDelegate.getDelegation() != null) {
            message.setDelegation(convert(sideChainDelegate.getDelegation()));
        }

        message.setSideChainId(sideChainDelegate.getSideChainId());
        return message;
    }

    private SideChainRedelegateMessage convert(SideChainRedelegate redelegate){
        SideChainRedelegateMessage message = new SideChainRedelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(redelegate.getDelegatorAddress()));
        message.setSrcValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(redelegate.getSrcValidatorAddress(), valHrp));
        message.setDstValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(redelegate.getDstValidatorAddress(), valHrp));
        if (redelegate.getAmount() != null) {
            message.setAmount(convert(redelegate.getAmount()));
        }
        message.setSideChainId(redelegate.getSideChainId());
        return message;
    }

    private SideChainUndelegateMessage convert(SideChainUnBond unBond){
        SideChainUndelegateMessage message = new SideChainUndelegateMessage();
        message.setDelegatorAddress(Bech32AddressValue.fromBech32String(unBond.getDelegatorAddress()));
        message.setValidatorAddress(Bech32AddressValue.fromBech32StringWithNewHrp(unBond.getValidatorAddress(), valHrp));
        if (unBond.getAmount() != null) {
            message.setAmount(convert(unBond.getAmount()));
        }
        message.setSideChainId(unBond.getSideChainId());
        return message;
    }


}
