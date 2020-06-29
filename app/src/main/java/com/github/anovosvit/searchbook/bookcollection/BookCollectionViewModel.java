package com.github.anovosvit.searchbook.bookcollection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.anovosvit.searchbook.data.BookRepository;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.List;

public class BookCollectionViewModel extends AndroidViewModel {
    private BookRepository repository;
    private LiveData<List<VolumeInfo>> allFavBooks;

    public BookCollectionViewModel(@NonNull Application application) {
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
}