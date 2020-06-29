package com.github.anovosvit.searchbook.bookinfo;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.bookcollection.BookCollectionAdapter;
import com.github.anovosvit.searchbook.databinding.BookInfoFragmentBinding;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;
import com.github.anovosvit.searchbook.recyclerview.BookItemClickListener;

import java.util.List;

public class BookInfoFragment extends Fragment implements BookItemClickListener {

    private BookInfoViewModel viewModel;
    private BookInfoFragmentBinding binding;
    private static VolumeInfo volumeInfo;
    private boolean isFavorite;

    public static BookInfoFragment newInstance() {
        BookInfoFragment bookInfoFragment = new BookInfoFragment();
        return bookInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(BookInfoViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.isFavorite(volumeInfo.getTitle()).observe(getViewLifecycleOwner(), aBoolean -> {
            isFavorite = aBoolean;
            if (isFavorite) {
                binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_active_24);
            } else {
                binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });

        viewModel.getAllFavBooks().observe(getViewLifecycleOwner(), new Observer<List<VolumeInfo>>() {
            @Override
            public void onChanged(List<VolumeInfo> volumeInfos) {
                if (volumeInfos != null || volumeInfos.size() != 0) {
                    BookCollectionAdapter.getInstance().setBooks(volumeInfos);
                    BookCollectionAdapter.getInstance().notifyDataSetChanged();
                }
            }
        });


        binding = DataBindingUtil.inflate(inflater, R.layout.book_info_fragment, container, false);
        binding.setBookInfo(volumeInfo);

        if (volumeInfo.getImageLink() != null) {
            String imageUrl = volumeInfo.getImageLink();

            Glide.with(this)
                    .load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(binding.coverBookImageView);
        }

//        binding.favoritesButton.setOnClickListener(v -> {
//            if (!isFavorite) {
//                addToFavorite(volumeInfo);
//            } else {
//                deleteBook(volumeInfo);
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onBookItemClick(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eventShare:
                share();
                return false;
            case R.id.addToCollection:
                addToFavorite(volumeInfo);
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBook(VolumeInfo volumeInfo) {
        viewModel.deleteSelectedBook(volumeInfo);
        Toast.makeText(getContext(), getString(R.string.delete_fav), Toast.LENGTH_SHORT).show();
        binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_24);
    }

    private void share() {
        Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
    }

    private void addToFavorite(VolumeInfo book) {
        viewModel.addToFavorite(book);
        Toast.makeText(getContext(), getString(R.string.add_to_fav), Toast.LENGTH_SHORT).show();
        binding.favoritesButton.setImageResource(R.drawable.ic_baseline_favorite_active_24);
    }
}