package com.binance.dex.api.client.domain.stake;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class Delegation {

    private DelegationItem delegation;

    private Token balance;

    public Delegation() {
    }

    public Delegation(DelegationItem delegation, Token balance) {
        this.delegation = delegation;
        this.balance = balance;
    }

    public DelegationItem getDelegation() {
        return delegation;
    }

    public void setDelegation(DelegationItem delegation) {
        this.delegation = delegation;
    }

    public Token getBalance() {
        return balance;
    }

    public void setBalance(Token balance) {
        this.balance = balance;
    }
}
