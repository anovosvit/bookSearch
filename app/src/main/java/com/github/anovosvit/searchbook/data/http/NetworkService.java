package com.github.anovosvit.searchbook.data.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private BookSearchApi bookSearchApi;
    private static final String BOOK_SEARCH_API_BASE_URL = "https://www.googleapis.com/";


    private NetworkService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        bookSearchApi = new Retrofit.Builder()
                .baseUrl(BOOK_SEARCH_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(BookSearchApi.class);
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public BookSearchApi getBookSearchApi() {
        return bookSearchApi;
    }

}