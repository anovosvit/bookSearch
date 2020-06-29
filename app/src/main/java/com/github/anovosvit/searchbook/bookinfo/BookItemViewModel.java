package com.github.anovosvit.searchbook.bookinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;


public class BookItemViewModel extends AndroidViewModel {

    private BookRepository repository;

    public BookItemViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
    }

    public void addToFavorite(VolumeInfo book) {
        repository.insert(book);
    }

    public void deleteSelectedBook(VolumeInfo book) {
        repository.deleteBook(book);
    }

    public LiveData<Boolean> isFavorite(String bookTitle) {
        return repository.isFavorite(bookTitle);
    }
}