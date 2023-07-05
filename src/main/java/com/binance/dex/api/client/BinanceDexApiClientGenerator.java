package com.binance.dex.api.client;

import com.binance.dex.api.client.impl.InternalInvokeInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

public class BNBDexApiClientGenerator {
    private static final Converter.Factory converterFactory =
            JacksonConverterFactory.create(new ObjectMapper().registerModule(new JodaModule()));

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, BNBDexApiError> errorBodyConverter =
            (Converter<ResponseBody, BNBDexApiError>) converterFactory.responseBodyConverter(
                    BNBDexApiError.class, new Annotation[0], null);

    private static OkHttpClient sharedClient;
    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = new OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .pingInterval(20, TimeUnit.SECONDS)
            .build();
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory);

        retrofitBuilder.client(sharedClient);

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String baseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory);

        if (StringUtils.isEmpty(apiKey)) {
            retrofitBuilder.client(sharedClient);
        } else {
            // `adaptedClient` will use its own interceptor, but share thread pool etc with the 'parent' client
            InternalInvokeInterceptor interceptor = new InternalInvokeInterceptor(apiKey);
            OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
            retrofitBuilder.client(adaptedClient);
        }

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
                    BNBDexApiError apiError = getBNBApiError(response);
                    throw new BNBDexApiException(apiError);
                } catch (IOException e) {
                    throw new BNBDexApiException(response.code(), response.toString());
                }
            }
        } catch (IOException e) {
            throw new BNBDexApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static BNBDexApiError getBNBApiError(Response<?> response) throws IOException {
        return errorBodyConverter.convert(response.errorBody());
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }

    /**
     * Add interceptor to shared client
     */
    public static void addInterceptor(Interceptor interceptor) {
        sharedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
    }
}
