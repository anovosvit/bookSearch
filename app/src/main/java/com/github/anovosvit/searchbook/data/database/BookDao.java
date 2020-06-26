package com.github.anovosvit.searchbook.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VolumeInfo book);

    @Query("SELECT * FROM book_table")
    LiveData<List<VolumeInfo>> getAll();

    @Query("SELECT EXISTS(SELECT 1 FROM book_table WHERE title = :bookTitle LIMIT 1)")
    LiveData<Boolean> isFavorite(String bookTitle);

    @Query("DELETE FROM book_table")
    void deleteAll();

    @Delete
    void deleteBook(VolumeInfo book);

}