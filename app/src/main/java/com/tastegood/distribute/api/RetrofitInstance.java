package com.tastegood.distribute.api;


import com.tastegood.distribute.gobal.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by surandy on 2016/10/13.
 */

public class RetrofitInstance {

    private static Retrofit retrofitInstance = null;

    public static Retrofit getInstance() {
        if (retrofitInstance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Accept", "*/*")
                                    .addHeader("Cookie", "add cookies here")
                                    .build();
                            return chain.proceed(request);
                        }

                    })
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofitInstance;
    }

}
