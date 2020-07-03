package com.github.anovosvit.searchbook.bookinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;
import com.github.anovosvit.searchbook.databinding.BookInfoFragmentBinding;
import com.github.anovosvit.searchbook.listeners.BookItemClickListener;
import com.github.anovosvit.searchbook.utils.Helper;

public class BookInfoCollectionFragment extends Fragment implements BookItemClickListener {

    private BookItemViewModel viewModel;
    private BookInfoFragmentBinding binding;
    private static VolumeInfo volumeInfo;
    private boolean isFavorite;

    public static BookInfoCollectionFragment newInstance() {
        return new BookInfoCollectionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(BookItemViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.book_info_fragment, container, false);
        binding.setBookInfo(volumeInfo);

        viewModel.isFavorite(volumeInfo.getTitle()).observe(getViewLifecycleOwner(), aBoolean -> {
            isFavorite = aBoolean;
            if (isFavorite) {
                binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_active_24);
            } else {
                binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });

        if (volumeInfo.getImageLink() != null) {
            Helper.uploadImage(this, volumeInfo.getImageLink(), binding.coverBookImageView);
        }

        return binding.getRoot();
    }

    @Override
    public void onBookItemClick(VolumeInfo volumeInfo) {
        BookInfoCollectionFragment.volumeInfo = volumeInfo;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_info_collection, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeFromCollection:
                deleteBook();
                return true;
            case R.id.eventShare:
                startActivity(Helper.shareBookInfo(volumeInfo));
                return true;
            case R.id.readBook:
                startActivity(Helper.goToRead(volumeInfo));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBook() {
        if (isFavorite) {
            viewModel.deleteSelectedBook(volumeInfo);
            Toast.makeText(getContext(), getString(R.string.delete_fav), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.delete_error_message), Toast.LENGTH_SHORT).show();
        }
    }
}
