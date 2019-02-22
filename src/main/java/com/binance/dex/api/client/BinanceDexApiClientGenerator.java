package com.binance.dex.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

public class BinanceDexApiClientGenerator {
    private static final OkHttpClient sharedClient = new OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .build();

    private static final Converter.Factory converterFactory =
            JacksonConverterFactory.create(new ObjectMapper().registerModule(new JodaModule()));

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, BinanceDexApiError> errorBodyConverter =
            (Converter<ResponseBody, BinanceDexApiError>) converterFactory.responseBodyConverter(
                    BinanceDexApiError.class, new Annotation[0], null);

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory);

        retrofitBuilder.client(sharedClient);

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                try {
                    BinanceDexApiError apiError = getBinanceApiError(response);
                    throw new BinanceDexApiException(apiError);
                } catch (IOException e) {
                    throw new BinanceDexApiException(response.toString(), e);
                }
            }
        } catch (IOException e) {
            throw new BinanceDexApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static BinanceDexApiError getBinanceApiError(Response<?> response) throws IOException {
        return errorBodyConverter.convert(response.errorBody());
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }
}
