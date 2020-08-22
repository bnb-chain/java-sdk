package com.binance.dex.api.client.encoding.message.bridge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fitz.Lu
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface ClaimType {
}
