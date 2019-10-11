package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.HtltReq;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.encoding.EncodeUtils;
import com.binance.dex.api.client.encoding.message.Token;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * @author: fletcher.fan
 * @create: 2019-10-09
 */
public class HtltExample {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String yourPrivateKey = "";
        Wallet wallet =
                new Wallet(yourPrivateKey,
                        BinanceDexEnvironment.TEST_NET);
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        HtltReq htltReq = new HtltReq();
        htltReq.setRecipient(""); // the recipient
        long timestamp = Instant.now().getEpochSecond();
        byte[] randomNumber = RandomUtils.nextBytes(32); // remember your randomNumber
        byte[] originData = ArrayUtils.addAll(randomNumber, EncodeUtils.long2Bytes(timestamp));
        htltReq.setRandomNumberHash(EncodeUtils.hashBySHA256(originData));
        htltReq.setTimestamp(timestamp);
        Token token = new Token();
        token.setDenom("BNB");
        token.setAmount(100L);
        htltReq.setOutAmount(Collections.singletonList(token));
        htltReq.setExpectedIncome("100:CNN-210");
        htltReq.setHeightSpan(1000);
        htltReq.setCrossChain(false);
        TransactionOption options = TransactionOption.DEFAULT_INSTANCE;
        List<TransactionMetadata> resp = client.htlt(htltReq,wallet,options,true);
        System.out.println(resp.get(0).toString());
    }
}
