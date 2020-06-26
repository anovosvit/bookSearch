package com.github.anovosvit.searchbook.bookcollection;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.bookinfo.BookInfoFragment;
import com.github.anovosvit.searchbook.databinding.ItemCollectionBinding;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.ArrayList;
import java.util.List;

public class BookCollectionAdapter extends RecyclerView.Adapter<BookCollectionAdapter.BookCollectionViewHolder> {

    private List<VolumeInfo> books = new ArrayList<>();

    public List<VolumeInfo> getBooks() {
        return books;
    }

    public void setBooks(List<VolumeInfo> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCollectionBinding itemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_collection, parent, false);
        return new BookCollectionViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCollectionViewHolder holder, int position) {
        VolumeInfo book = getBooks().get(position);
        holder.itemBinding.setBook(book);
        holder.itemBinding.executePendingBindings();

        if (book.getImageLink() != null) {
            String imageUrl = book.getImageLink();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(holder.itemBinding.coverImageCollection);
        }

        holder.itemBinding.setItemClickListener(v -> {
            BookInfoFragment.newInstance().onBookItemClick(book);
            Navigation.findNavController(v).navigate(R.id.bookInfoFragment);
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (getBooks() != null) {
            count = getBooks().size();
        }
        return count;
    }

    public class BookCollectionViewHolder extends RecyclerView.ViewHolder {
        private ItemCollectionBinding itemBinding;

        public BookCollectionViewHolder(ItemCollectionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
