package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.BinanceDexConstants;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.Objects;

/**
 * A request interceptor that injects the API Key Header into requests.
 */
public class InternalInvokeInterceptor implements Interceptor {

    private final String apiKey;

    public InternalInvokeInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder newRequestBuilder = original.newBuilder();

        newRequestBuilder.addHeader(BinanceDexConstants.API_KEY_HEADER, apiKey);

        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }

    /**
     * Extracts the request body into a String.
     *
     * @return request body as a string
     */
    @SuppressWarnings("unused")
    private static String bodyToString(RequestBody request) {
        try (final Buffer buffer = new Buffer()) {
            final RequestBody copy = request;
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InternalInvokeInterceptor that = (InternalInvokeInterceptor) o;
        return Objects.equals(apiKey, that.apiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey);
    }
}