package com.binance.dex.api.client.domain.broadcast;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class RemoveValidator {

    private String launcherAddr;
    private String valAddr;
    private String valConsAddr;
    private Long proposalId;

    public String getLauncherAddr() {
        return launcherAddr;
    }

    public void setLauncherAddr(String launcherAddr) {
        this.launcherAddr = launcherAddr;
    }

    public String getValAddr() {
        return valAddr;
    }

    public void setValAddr(String valAddr) {
        this.valAddr = valAddr;
    }


    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getValConsAddr() {
        return valConsAddr;
    }

    public void setValConsAddr(String valConsAddr) {
        this.valConsAddr = valConsAddr;
    }
}
