package com.binance.dex.api.client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static com.binance.dex.api.client.BinanceDexApiClientGenerator.getBinanceApiError;

/**
 * An adapter/wrapper which transforms a Callback from Retrofit into a BinanceDexApiCallback which is exposed to the client.
 */
public class BinanceDexApiCallbackAdapter<T> implements Callback<T> {

    private final BinanceDexApiCallback<T> callback;

    public BinanceDexApiCallbackAdapter(BinanceDexApiCallback<T> callback) {
        this.callback = callback;
    }

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            callback.onResponse(response.body());
        } else {
            if (response.code() == 504) {
                // HTTP 504 return code is used when the API successfully sent the message but not get a response within the timeout period.
                // It is important to NOT treat this as a failure; the execution status is UNKNOWN and could have been a success.
                return;
            }
            try {
                BinanceDexApiError apiError = getBinanceApiError(response);
                onFailure(call, new BinanceDexApiException(apiError));
            } catch (IOException e) {
                onFailure(call, new BinanceDexApiException(e));
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        if (throwable instanceof BinanceDexApiException) {
            callback.onFailure(throwable);
        } else {
            callback.onFailure(new BinanceDexApiException(throwable));
        }
    }
}
