package com.github.anovosvit.searchbook.bookinfo;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.booksearch.BookSearchViewModel;
import com.github.anovosvit.searchbook.databinding.BookInfoFragmentBinding;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;
import com.github.anovosvit.searchbook.recyclerview.BookItemClickListener;

public class BookInfoFragment extends Fragment implements BookItemClickListener {

    private BookInfoViewModel viewModel;
    private BookInfoFragmentBinding binding;
    private static VolumeInfo volumeInfo;
    private boolean isFavorite = false;
    private ImageButton favButton, shareButton = null;

    public static BookInfoFragment newInstance() {
        BookInfoFragment bookInfoFragment = new BookInfoFragment();
        volumeInfo = new VolumeInfo();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.book_info_fragment, container, false);
        binding.setBookInfo(volumeInfo);

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
        Log.i("Fragment", "проброс в фрагмент" + this.volumeInfo.getTitle());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_info, menu);
        MenuItem favItem = menu.findItem(R.id.favoriteEvent);
        MenuItem shareItem = menu.findItem(R.id.eventShare);

        if (favItem != null) {
            favButton = (ImageButton) favItem.getActionView();
        }

        if (shareItem != null) {
            shareButton = (ImageButton) shareItem.getActionView();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favoriteEvent:
                if (!isFavorite) {
                    addToFavorite(volumeInfo);
                    item.setIcon(R.drawable.ic_baseline_favorite_active_24);
                } else {
                    deleteBook(volumeInfo);
                    item.setIcon(R.drawable.ic_baseline_favorite_24);
                }
                return true;
            case R.id.eventShare:
                share();
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBook(VolumeInfo volumeInfo) {
        viewModel.deleteBook(volumeInfo);
        isFavorite = false;
        Toast.makeText(getContext(), getString(R.string.delete_fav), Toast.LENGTH_SHORT).show();
    }

    private void share() {
        Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
    }

    private void addToFavorite(VolumeInfo book) {
        viewModel.addToFavorite(book);
        isFavorite = true;
        Toast.makeText(getContext(), getString(R.string.add_to_fav), Toast.LENGTH_SHORT).show();
    }

}