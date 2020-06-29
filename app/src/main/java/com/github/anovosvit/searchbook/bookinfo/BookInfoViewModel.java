package com.github.anovosvit.searchbook.bookinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.List;

public class BookInfoViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<VolumeInfo>> allFavBooks;

    public BookInfoViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
        allFavBooks = repository.getAll();
    }

    public LiveData<List<VolumeInfo>> getAllFavBooks() {
        return allFavBooks;
    }

    public void deleteAllFavBooks(){
        repository.deleteAll();
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