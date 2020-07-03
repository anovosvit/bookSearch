package com.github.anovosvit.searchbook.booksearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.listeners.Model;
import com.github.anovosvit.searchbook.data.model.BooksResponse;

public class BookSearchViewModel extends AndroidViewModel {

    private BookRepository repository;

    private LiveData<BooksResponse> volumesResponseLiveData;

    public BookSearchViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
    }

    public void init() {
        volumesResponseLiveData = repository.getBookResponseLiveData();
    }

    public LiveData<BooksResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }

    public void searchBooks(String keyword, String pageCount, Model.OnFinishedListener listener) {
        repository.getBookList(keyword, pageCount, listener);
    }
}