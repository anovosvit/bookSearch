package com.github.anovosvit.searchbook.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.anovosvit.searchbook.data.database.BookDao;
import com.github.anovosvit.searchbook.data.database.BookRoomDatabase;
import com.github.anovosvit.searchbook.data.http.Model;
import com.github.anovosvit.searchbook.data.http.NetworkService;
import com.github.anovosvit.searchbook.data.model.BooksResponse;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {
    private final String TAG = "BookRepository";

    private BookDao bookDao;
    private MutableLiveData<BooksResponse> booksResponseLiveData = new MutableLiveData<>();
    private LiveData<List<VolumeInfo>> allBooks;
    private static BookRepository instance;

    public static BookRepository getInstance(Context context) {
        if (instance == null) {
            instance = new BookRepository(context);
        }
        return instance;
    }

    private BookRepository(Context context) {
        bookDao = BookRoomDatabase.getDatabase(context).bookDao();
        allBooks = bookDao.getAll();
    }

    public LiveData<List<VolumeInfo>> getAll() {
        return allBooks;
    }

    public void insert(VolumeInfo book) {
        new InsertAsyncTask(bookDao).execute(book);
    }

    public void deleteAll() {
        new DeleteAllBooksAsyncTask(bookDao).execute();
    }

    public void deleteBook(VolumeInfo book) {
        new DeleteBookAsyncTask(bookDao).execute(book);
    }

    public LiveData<BooksResponse> getBookResponseLiveData() {
        return booksResponseLiveData;
    }

    public void getBookList(String keyword, String pageCount, Model.OnFinishedListener onFinishedListener) {
        Log.i("Fragment", "Сейчас будет отправляться запрос");
        NetworkService.getInstance()
                .getBookSearchApi()
                .searchBooks(keyword, pageCount)
                .enqueue(new Callback<BooksResponse>() {
                    @Override
                    public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                        if (response.body() != null) {
                            Log.i("TAG", "Зашло в репозиторий");
                            booksResponseLiveData.postValue(response.body());
                            onFinishedListener.onFinished();
                        }
                    }

                    @Override
                    public void onFailure(Call<BooksResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                        booksResponseLiveData.postValue(null);
                        onFinishedListener.onFailure(t);
                    }
                });
    }

    private static class InsertAsyncTask extends AsyncTask<VolumeInfo, Void, Void> {

        private BookDao mAsyncTaskDao;

        InsertAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final VolumeInfo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAllBooksAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDao mAsyncTaskDao;

        DeleteAllBooksAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class DeleteBookAsyncTask extends AsyncTask<VolumeInfo, Void, Void> {
        private BookDao mAsyncTaskDao;

        DeleteBookAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final VolumeInfo... params) {
            mAsyncTaskDao.deleteBook(params[0]);
            return null;
        }
    }

}
