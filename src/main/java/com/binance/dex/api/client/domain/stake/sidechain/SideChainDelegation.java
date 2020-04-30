package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class SideChainDelegation {

    private Delegation delegation;

    private Token balance;

    public SideChainDelegation() {
    }

    public SideChainDelegation(Delegation delegation, Token balance) {
        this.delegation = delegation;
        this.balance = balance;
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
