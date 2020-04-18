package com.binance.dex.api.client.encoding;

import com.binance.dex.api.client.encoding.amino.Amino;
import com.binance.dex.api.client.encoding.message.sidechain.query.SideChainRedelegationMessage;
import com.binance.dex.api.client.encoding.message.sidechain.value.RedelegationValue;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Fitz.Lu
 **/
public class AminoTest {

    private final Amino amino = new Amino();

    @Test
    public void testDecodeRedelegation() throws IOException {
        String hex = "6e0a148c7c3a06d11eacc857a7b623ca12ed963c5c53e812148c7c3a06d11eacc857a7b623ca12ed963c5c53e81a147fe359f3eaa4d2627069b32c49f854b736343c7220da522a0c08808094f50510d08dad840332080a03424e4210e8073a0b0a03424e4210d285d8cc04420a4a32";
        SideChainRedelegationMessage message = new SideChainRedelegationMessage();
        amino.decodeWithLengthPrefix(Hex.decode(hex), message);
        Assert.assertNotNull(message.getDelegatorAddress());

        hex = "2a08a554120c08c08994f50510a0b0df93011a080a03424e4210904e22090a03424e4210d099052a0a3232";
        RedelegationValue value = new RedelegationValue();
        amino.decodeWithLengthPrefix(Hex.decode(hex), value);
        Assert.assertNotNull(value.getSharesDst());
    }

}
