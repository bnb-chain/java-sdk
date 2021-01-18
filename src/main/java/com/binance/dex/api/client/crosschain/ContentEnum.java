package com.binance.dex.api.client.crosschain;

import com.binance.dex.api.client.crosschain.content.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ContentEnum {
    ApproveBindSynPack(1, 0, ApproveBindSyn.class),
    BindSynPack(1, 2, BindSyn.class),
    TransferOutRefundPack(2, 1, TransferOutRefund.class),
    TransferOutSynPack(2, 2, TransferOutSyn.class),
    TransferInSynPack(3, 0, TransferInSyn.class),
    StakingCommonAckPack(8, 1, CommonAck.class),
    IbcValidatorSetPack(8, 2, IbcValidatorSet.class),
    GovCommonAckPack(9, 1, CommonAck.class),
    SideDowntimeSlashPack(11, 0, com.binance.dex.api.client.crosschain.content.SideDowntimeSlash.class),
    MirrorSynPack(4, 0, MirrorSyn.class),
    MirrorSynAckPack(4, 1, MirrorAck.class),
    MirrorSyncSynPack(5, 0, MirrorSyncSyn.class),
    MirrorSyncAckPack(5, 1, MirrorSyncAck.class)
    ;

    private Integer channelId;
    private Integer packType;
    private Class<? extends Content> clazz;

    public static Class<? extends Content> getClass(Integer channelId, Integer packType) {
        Optional<ContentEnum> optional = Arrays.stream(ContentEnum.values())
                .filter(contentEnum -> channelId.equals(contentEnum.channelId) && packType.equals(contentEnum.packType))
                .findAny();
        return optional.<Class<? extends Content>>map(ContentEnum::getClazz).orElse(null);
    }
}
