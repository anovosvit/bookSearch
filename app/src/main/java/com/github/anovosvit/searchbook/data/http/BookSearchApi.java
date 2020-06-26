package com.github.anovosvit.searchbook.data.http;

import com.github.anovosvit.searchbook.data.model.BooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookSearchApi {

    @GET("/books/v1/volumes")
    Call<BooksResponse> searchBooks(
            @Query("q") String query,
            @Query("maxResults") String pageCount
    );
}
