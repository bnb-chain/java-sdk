package com.binance.dex.api.client.rlp;

public interface RlpDecodable {

    void decode(byte[] raw, Object superInstance) throws Exception;

}
