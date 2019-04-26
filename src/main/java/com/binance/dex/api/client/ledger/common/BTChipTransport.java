package com.binance.dex.api.client.ledger.common;

public interface BTChipTransport {
	byte[] exchange(byte[] command) throws BTChipException;
	void close() throws BTChipException;
}
