package com.github.anovosvit.searchbook.bookcollection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;
import com.github.anovosvit.searchbook.databinding.FragmentBookCollectionBinding;

import java.util.List;

public class BookCollectionFragment extends Fragment {

    private BookCollectionViewModel bookCollectionViewModel;
    private BookCollectionAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentBookCollectionBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        adapter = new BookCollectionAdapter();
        setHasOptionsMenu(true);
        bookCollectionViewModel = new ViewModelProvider(this).get(BookCollectionViewModel.class);
        bookCollectionViewModel.init();

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_collection, container, false);
        recyclerView = binding.collectionRecyclerView;

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        bookCollectionViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<VolumeInfo>>() {
            @Override
            public void onChanged(List<VolumeInfo> volumeInfos) {
                adapter.setBooks(volumeInfos);
                Log.i("BookCollectionFragment", "Получили книги:");
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_collection, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteAllBooks();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllBooks() {
        bookCollectionViewModel.deleteAllBooks();
        binding.textBookcollection.setVisibility(View.VISIBLE);
        binding.textBookcollection.setText("BOOK COLLECTION");
    }

}