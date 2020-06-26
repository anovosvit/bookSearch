package com.github.anovosvit.searchbook.bookinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.List;

public class BookInfoViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<VolumeInfo>> allBooks;

    public BookInfoViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
    }

    public void init() {
        allBooks = repository.getAll();
    }

    public LiveData<List<VolumeInfo>> getAllBooks() {
        return allBooks;
    }

    public void deleteAllBooks(){
        repository.deleteAll();
    }

    public void addToFavorite(VolumeInfo book) {
        repository.insert(book);
    }

    public void deleteBook(VolumeInfo book) {
        repository.deleteBook(book);
    }

    public LiveData<Boolean> isFavorite(String bookTitle) {
        return repository.isFavorite(bookTitle);
    }
}
