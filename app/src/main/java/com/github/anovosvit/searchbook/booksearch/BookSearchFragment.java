package com.github.anovosvit.searchbook.booksearch;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.databinding.FragmentBooksearchBinding;
import com.github.anovosvit.searchbook.data.http.Model;

public class BookSearchFragment extends Fragment implements Model.OnFinishedListener {

    private BookSearchViewModel viewModel;
    private BookAdapter adapter;
    private FragmentBooksearchBinding binding;
    private RecyclerView recyclerView;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new BookAdapter();
        viewModel = new ViewModelProvider(this).get(BookSearchViewModel.class);

        viewModel.init();
        viewModel.getVolumesResponseLiveData().observe(this, booksResponse -> {
            if (booksResponse != null || booksResponse.getItems() != null) {
                binding.booksImage.setVisibility(View.GONE);
                adapter.setmData(booksResponse.getItems());
            } else {
                binding.booksImage.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), R.string.no_such_book, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booksearch, container, false);
        recyclerView = binding.bookSearchRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_list, menu);
        MenuItem searchItem = menu.findItem(R.id.searchAction);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getString(R.string.query_hint));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 3) {
                        searchBook(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchBook(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.searchAction) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinished() {
        binding.progress.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Throwable t) {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(getContext(), getString(R.string.no_such_book), Toast.LENGTH_SHORT).show();
    }

    public void searchBook(String newText) {
        recyclerView.smoothScrollToPosition(0);
        binding.progress.setVisibility(View.VISIBLE);
        viewModel.searchBooks(newText, "10", this);
        Log.i("onQueryText", newText);
    }

}