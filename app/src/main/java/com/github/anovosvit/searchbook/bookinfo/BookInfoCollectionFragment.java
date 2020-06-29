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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;
import com.github.anovosvit.searchbook.databinding.BookInfoFragmentBinding;

public class BookInfoCollectionFragment extends Fragment implements BookItemClickListener {

    private BookItemViewModel viewModel;
    private BookInfoFragmentBinding binding;
    private static VolumeInfo volumeInfo;
    private boolean isFavorite;

    public static BookInfoCollectionFragment newInstance() {
        BookInfoCollectionFragment bookInfoCollectionFragment = new BookInfoCollectionFragment();
        return bookInfoCollectionFragment;
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
            String imageUrl = volumeInfo.getImageLink();

            Glide.with(this)
                    .load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(binding.coverBookImageView);
        }

        return binding.getRoot();
    }

    @Override
    public void onBookItemClick(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
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
                share();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
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
