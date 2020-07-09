package com.binance.dex.api.client.encoding.rlp;


import com.binance.dex.api.client.crosschain.Package;
import com.binance.dex.api.client.crosschain.Payload;
import com.binance.dex.api.client.crosschain.content.ApproveBindSyn;
import com.binance.dex.api.client.crosschain.content.SideDowntimeSlash;
import com.binance.dex.api.client.crosschain.content.TransferInSyn;
import com.binance.dex.api.client.crosschain.content.TransferOutRefund;
import com.binance.dex.api.client.encoding.Crypto;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.rlp.Decoder;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;


import java.math.BigInteger;
import java.util.List;

public class RLPTest {

    @Test
    public void testApproveBindSynDecode() throws Exception {
        byte[] raw = Hex.decode("f84af8480110b8440000000000000000000000000000000000000000000000000000000000000f4240e280a04142432d44453700000000000000000000000000000000000000000000000000");
        List<Package> result = Decoder.decodeList(raw, Package.class);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).getChannelId().getValue());
        Assert.assertEquals(16, result.get(0).getSequence().getNumber().longValue());
        Payload payload = result.get(0).getPayload();
        Assert.assertEquals(0, (int) payload.getPackageType());
        Assert.assertEquals(new BigInteger("1000000"), payload.getCrossChainFee());
        Assert.assertTrue(payload.getContent() instanceof ApproveBindSyn);
        ApproveBindSyn approveBindSyn = (ApproveBindSyn) payload.getContent();
        Assert.assertEquals(0, approveBindSyn.getStatus().getValue());
        Assert.assertEquals("ABC-DE7", approveBindSyn.getSymbol().getSymbol());
    }

    @Test
    public void testTransferOutRefundDecode() throws Exception {
        byte[] raw = Hex.decode("f865f8630280b85f010000000000000000000000000000000000000000000000000000000000000000f83ca0424e4200000000000000000000000000000000000000000000000000000000008401312d009425016278faceb917ed90ec5d940352965ac096ad01");
        List<Package> result = Decoder.decodeList(raw, Package.class);
        result.forEach(pack -> pack.setHrp("tbnb"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(2, result.get(0).getChannelId().getValue());
        Assert.assertEquals(0, result.get(0).getSequence().getNumber().longValue());
        Payload payload = result.get(0).getPayload();
        Assert.assertEquals(1, (int) payload.getPackageType());
        Assert.assertEquals(new BigInteger("0"), payload.getCrossChainFee());
        Assert.assertTrue(payload.getContent() instanceof TransferOutRefund);
        TransferOutRefund transferOutRefund = (TransferOutRefund) payload.getContent();
        Assert.assertEquals("BNB", transferOutRefund.getSymbol().getSymbol());
        Assert.assertEquals(20000000L, transferOutRefund.getRefundAmount().getNumber().longValue());
        Assert.assertEquals("tbnb1y5qky786e6u30mvsa3wegq6jjedvp94dfeyvkw", Crypto.encodeAddress("tbnb", transferOutRefund.getRefundAddr().getRaw()));
        Assert.assertEquals(1, transferOutRefund.getRefundReason().getValue());
    }

    @Test
    public void testTransferInSyncDecode() throws Exception {
        byte[] raw = Hex.decode("f8c7f8c5038306985ab8be0000000000000000000000000000000000000000000000000000000000001e8480f89ba0424e420000000000000000000000000000000000000000000000000000000000940000000000000000000000000000000000000000c9831e84808401312d00ea9425016278faceb917ed90ec5d940352965ac096ad943ece21ac0d97a24d004a6778babda59bbb167deaea9456ec2590d32b63a04f0a8613a85ab829dfc0ef259456ec2590d32b63a04f0a8613a85ab829dfc0ef25845f0415ea");
        List<Package> result = Decoder.decodeList(raw, Package.class);
        result.forEach(pack -> pack.setHrp("tbnb"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).getChannelId().getValue());
        Assert.assertEquals(432218, result.get(0).getSequence().getNumber().longValue());
        Payload payload = result.get(0).getPayload();
        Assert.assertEquals(0, (int) payload.getPackageType());
        Assert.assertEquals(new BigInteger("2000000"), payload.getCrossChainFee());
        Assert.assertTrue(payload.getContent() instanceof TransferInSyn);
        TransferInSyn transferInSyn = (TransferInSyn) payload.getContent();
        Assert.assertEquals("BNB", transferInSyn.getSymbol().getSymbol());
        Assert.assertEquals("0x0000000000000000000000000000000000000000", transferInSyn.getContractAddress().getAddress());
        Assert.assertEquals(2000000, transferInSyn.getAmounts().get(0).getNumber().longValue());
        Assert.assertEquals(20000000, transferInSyn.getAmounts().get(1).getNumber().longValue());
        Assert.assertEquals("tbnb1y5qky786e6u30mvsa3wegq6jjedvp94dfeyvkw", Crypto.encodeAddress("tbnb", transferInSyn.getReceiverAddresses().get(0).getRaw()));
        Assert.assertEquals("tbnb18m8zrtqdj73y6qz2vaut40d9nwa3vl02vmyvez", Crypto.encodeAddress("tbnb", transferInSyn.getReceiverAddresses().get(1).getRaw()));
        Assert.assertEquals("0x56ec2590d32b63a04f0a8613a85ab829dfc0ef25",  transferInSyn.getRefundAddresses().get(0).getAddress());
        Assert.assertEquals("0x56ec2590d32b63a04f0a8613a85ab829dfc0ef25",  transferInSyn.getRefundAddresses().get(1).getAddress());
        Assert.assertEquals(1594103274L, transferInSyn.getExpireTime().getValue());
    }

    @Test
    public void testSlashDowntimeDecode() throws Exception {
        byte[] raw = Hex.decode("f847f8450b80b841000000000000000000000000000000000000000000000000000000000000000000df940dd11a413972d8b1e1367c4b9196f75348424e708301c23c60845f02fca7");
        List<Package> result = Decoder.decodeList(raw, Package.class);
        result.forEach(pack -> pack.setHrp("tbnb"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(11, result.get(0).getChannelId().getValue());
        Assert.assertEquals(0, result.get(0).getSequence().getNumber().longValue());
        Payload payload = result.get(0).getPayload();
        Assert.assertEquals(0, (int) payload.getPackageType());
        Assert.assertEquals(new BigInteger("0"), payload.getCrossChainFee());
        Assert.assertTrue(payload.getContent() instanceof SideDowntimeSlash);
        SideDowntimeSlash sideDowntimeSlash = (SideDowntimeSlash) payload.getContent();
        Assert.assertEquals("0x0dd11a413972d8b1e1367c4b9196f75348424e70", EncodeUtils.bytesToPrefixHex(sideDowntimeSlash.getSideConsAddr()));
        Assert.assertEquals(115260, sideDowntimeSlash.getSideHeight().getValue());
        Assert.assertEquals(96, sideDowntimeSlash.getSideChainId().getValue());
        Assert.assertEquals(1594031271, sideDowntimeSlash.getSideTimestamp().getValue());
    }

}
