package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.domain.stake.sidechain.Delegation;
import com.binance.dex.api.client.domain.stake.sidechain.SideChainDelegation;
import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class BeaconChainDelegation {

    private Delegation delegation;

    private Token balance;

    public BeaconChainDelegation() {
    }

    public BeaconChainDelegation(Delegation delegation, Token balance) {
        this.delegation = delegation;
        this.balance = balance;
    }

    public static BeaconChainDelegation createBySideChainDelegation(SideChainDelegation sideChainDelegation) {
        BeaconChainDelegation delegation = new BeaconChainDelegation();
        delegation.setDelegation(sideChainDelegation.getDelegation());
        delegation.setBalance(sideChainDelegation.getBalance());
        return delegation;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public Token getBalance() {
        return balance;
    }

    public void setBalance(Token balance) {
        this.balance = balance;
    }
}
