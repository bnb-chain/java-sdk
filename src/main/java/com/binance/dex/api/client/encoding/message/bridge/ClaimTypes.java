package com.binance.dex.api.client.encoding.message.bridge;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fitz.Lu
 **/
public class ClaimTypes {

    public static final int ClaimTypeSkipSequence = 0x1;
    public static final int ClaimTypeUpdateBind = 0x2;
    public static final int ClaimTypeUpdateTransferOut = 0x3;
    public static final int ClaimTypeTransferIn = 0x4;

    private static final String ClaimTypeSkipSequenceName = "SkipSequence";
    private static final String ClaimTypeUpdateBindName = "UpdateBind";
    private static final String ClaimTypeUpdateTransferOutName = "UpdateTransferOut";
    private static final String ClaimTypeTransferInName = "TransferIn";

    private static final Map<Integer, String> claimTypeToName;
    private static final Map<String, Integer> claimNameToType;

    static {
        claimTypeToName = new HashMap<>();
        claimTypeToName.put(ClaimTypeSkipSequence, ClaimTypeSkipSequenceName);
        claimTypeToName.put(ClaimTypeUpdateBind, ClaimTypeUpdateBindName);
        claimTypeToName.put(ClaimTypeUpdateTransferOut, ClaimTypeUpdateTransferOutName);
        claimTypeToName.put(ClaimTypeTransferIn, ClaimTypeTransferInName);

        claimNameToType = new HashMap<>();
        claimNameToType.put(ClaimTypeSkipSequenceName, ClaimTypeSkipSequence);
        claimNameToType.put(ClaimTypeUpdateBindName, ClaimTypeUpdateBind);
        claimNameToType.put(ClaimTypeUpdateTransferOutName, ClaimTypeUpdateTransferOut);
        claimNameToType.put(ClaimTypeTransferInName, ClaimTypeTransferIn);
    }

    public static boolean IsValidClaimType(int type){
        return claimTypeToName.containsKey(type);
    }

}
