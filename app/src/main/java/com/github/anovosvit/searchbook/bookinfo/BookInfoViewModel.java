package com.github.anovosvit.searchbook.bookinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

public class BookInfoViewModel extends AndroidViewModel {

    private BookRepository repository;

    public BookInfoViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
    }

    public void addToFavorite(VolumeInfo book) {
        repository.insert(book);
    }

    public void deleteBook(VolumeInfo book) {
        repository.deleteBook(book);
    }
}
