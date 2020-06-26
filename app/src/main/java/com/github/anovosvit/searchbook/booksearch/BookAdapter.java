package com.github.anovosvit.searchbook.booksearch;

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
import com.github.anovosvit.searchbook.databinding.ItemBookBinding;
import com.github.anovosvit.searchbook.data.model.Item;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Item> mData = new ArrayList<>();

    public List<Item> getmData() {
        return mData;
    }

    public void setmData(List<Item> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding item = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_book, parent, false);

        return new BookViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Item item = getmData().get(position);
        VolumeInfo volumeInfo = item.getVolumeInfo();
        holder.itemBinding.setBookItem(volumeInfo);
        holder.itemBinding.executePendingBindings();

        if (volumeInfo.getImageLinks() != null) {
            String imageUrl = item.getVolumeInfo().getImageLink();

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .into(holder.itemBinding.coverImageView);
        }

        holder.itemBinding.setItemClickListener(v -> {
            BookInfoFragment.newInstance().onBookItemClick(volumeInfo);
            Navigation.findNavController(v).navigate(R.id.bookInfoFragment);
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (getmData() != null) {
            count = getmData().size();
        }

        return count;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private ItemBookBinding itemBinding;

        public BookViewHolder(ItemBookBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}