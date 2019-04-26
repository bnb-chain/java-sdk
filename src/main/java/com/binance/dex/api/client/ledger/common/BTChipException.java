package com.binance.dex.api.client.ledger.common;

public class BTChipException extends Exception {

    private static final long serialVersionUID = 5512803003827126405L;

    public BTChipException(String reason) {
        super(reason);
    }

    public BTChipException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public BTChipException(String reason, int sw) {
        super(reason);
        this.sw = sw;
    }

    public int getSW() {
        return sw;
    }

    public String toString() {
        if (sw == 0) {
            return "BTChip Exception : " + getMessage();
        }
        else {
            return "BTChip Exception : " + getMessage() + " " + Integer.toHexString(sw);
        }
    }

    private int sw;
}
