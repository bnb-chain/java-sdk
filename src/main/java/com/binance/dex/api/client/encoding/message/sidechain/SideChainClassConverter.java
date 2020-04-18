package com.binance.dex.api.client.encoding.message.sidechain;

import com.binance.dex.api.client.domain.sidechain.*;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.SideChainDelegateMessage;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.SideChainRedelegateMessage;
import com.binance.dex.api.client.encoding.message.sidechain.transaction.SideChainUndelegateMessage;
import com.binance.dex.api.client.encoding.message.sidechain.value.*;

/**
 * @author Fitz.Lu
 **/
public class SideChainClassConverter {

    public static DescriptionValue convert(Description description){
        DescriptionValue value = new DescriptionValue();
        value.setMoniker(description.getMoniker());
        value.setDetails(description.getDetails());
        value.setIdentity(description.getIdentity());
        value.setWebsite(description.getWebsite());
        return value;
    }

    public static CommissionMsgValue convert(Commission commission){
        CommissionMsgValue value = new CommissionMsgValue();
        value.setRate(Dec.newInstance(commission.getRate()));
        value.setMaxRate(Dec.newInstance(commission.getMaxRate()));
        value.setMaxChangeRate(Dec.newInstance(commission.getMaxChangeRate()));
        return value;
    }

    public static CoinValue convert(Token token){
        CoinValue value = new CoinValue();
        value.setDenom(token.getDenom());
        value.setAmount(token.getAmount());
        return value;
    }

    public static SideChainDelegateMessage convert(SideChainDelegate sideChainDelegate){
        SideChainDelegateMessage message = new SideChainDelegateMessage();
        message.setDelegatorAddress(AddressValue.from(sideChainDelegate.getDelegatorAddress()));
        message.setValidatorAddress(AddressValue.from(sideChainDelegate.getValidatorAddress()));

        if (sideChainDelegate.getDelegation() != null) {
            message.setDelegation(convert(sideChainDelegate.getDelegation()));
        }

        message.setSideChainId(sideChainDelegate.getSideChainId());
        return message;
    }

    public static SideChainRedelegateMessage convert(SideChainRedelegate redelegate){
        SideChainRedelegateMessage message = new SideChainRedelegateMessage();
        message.setDelegatorAddress(AddressValue.from(redelegate.getDelegatorAddress()));
        message.setSrcValidatorAddress(AddressValue.from(redelegate.getSrcValidatorAddress()));
        message.setDstValidatorAddress(AddressValue.from(redelegate.getDstValidatorAddress()));
        if (redelegate.getAmount() != null) {
            message.setAmount(convert(redelegate.getAmount()));
        }
        message.setSideChainId(redelegate.getSideChainId());
        return message;
    }

    public static SideChainUndelegateMessage convert(SideChainUnBond unBond){
        SideChainUndelegateMessage message = new SideChainUndelegateMessage();
        message.setDelegatorAddress(AddressValue.from(unBond.getDelegatorAddress()));
        message.setValidatorAddress(AddressValue.from(unBond.getValidatorAddress()));
        if (unBond.getAmount() != null) {
            message.setAmount(convert(unBond.getAmount()));
        }
        message.setSideChainId(unBond.getSideChainId());
        return message;
    }

}
