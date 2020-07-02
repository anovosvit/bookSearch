package com.github.anovosvit.searchbook.bookinfo;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.databinding.BookInfoFragmentBinding;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

public class BookInfoFragment extends Fragment implements BookItemClickListener {

    private BookItemViewModel viewModel;
    private BookInfoFragmentBinding binding;
    private static VolumeInfo volumeInfo;
    private boolean isFavorite;

    public static BookInfoFragment newInstance() {
        return new BookInfoFragment();
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
        inflater.inflate(R.menu.menu_book_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eventShare:
                share();
                return true;
            case R.id.addToCollection:
                addToFavorite(volumeInfo);
                return true;
            case R.id.readBook:
                goToRead();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToRead() {
        Intent readBookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(volumeInfo.getPreviewLink()));
        startActivity(readBookIntent);
    }

    private void share() {
        Intent myShareIntent = new Intent();
        myShareIntent.setAction(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_SUBJECT, "I recommend you read book");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, "I recommend you read this book: \n" + volumeInfo.getTitle() + " \n" + volumeInfo.getPreviewLink());
        startActivity(Intent.createChooser(myShareIntent, null));
    }

    private void addToFavorite(VolumeInfo book) {
        if (!isFavorite) {
            viewModel.addToFavorite(book);
            Toast.makeText(getContext(), getString(R.string.add_to_fav), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.add_error_message), Toast.LENGTH_SHORT).show();
        }

    }
}