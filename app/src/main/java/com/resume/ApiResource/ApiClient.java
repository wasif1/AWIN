package com.resume.ApiResource;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resume.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    // RETROFIT OBJECT
    public static ApiClient Instance;
    public static Retrofit retrofit;
    private static OkHttpClient client;

    /**
     * GET API CLIENT
     */
    private ApiClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().
                            newBuilder()
                            .addHeader("platform", "android")
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    return response;
                });

        Gson gson = new GsonBuilder()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    /**
     * CREATE API INSTANCE WITHOUT CONTEXT
     * only for widgets
     */
    public static synchronized ApiClient getInstance() {
        Instance = new ApiClient();
        return Instance;
    }

    public static void cancelAll() {
        if (client != null) {
            client.dispatcher().cancelAll();
        }
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }

}
