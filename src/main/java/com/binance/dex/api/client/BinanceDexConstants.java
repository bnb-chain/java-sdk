package com.binance.dex.api.client;

import org.apache.commons.lang3.builder.ToStringStyle;

public class BinanceDexConstants {

    /**
     * Identifier of this client.
     */
    public static final long BINANCE_DEX_API_CLIENT_JAVA_SOURCE = 0L;

    /**
     * Default ToStringStyle used by toString methods.
     * Override this to change the output format of the overridden toString methods.
     * - Example ToStringStyle.JSON_STYLE
     */
    public static final ToStringStyle BINANCE_DEX_TO_STRING_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;

    /**
     * HTTP Header to be used for API-KEY authentication.
     */
    public static final String API_KEY_HEADER = "apikey";


}
