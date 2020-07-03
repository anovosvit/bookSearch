package com.github.anovosvit.searchbook.utils;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.github.anovosvit.searchbook.data.model.VolumeInfo;

public class Helper {

    public static void uploadImage(View view, String imageUrl, ImageView imageView) {
        Glide.with(view)
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imageView);
    }

    public static void uploadImage(Fragment fragment, String imageUrl, ImageView imageView) {
        Glide.with(fragment)
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imageView);
    }

    public static Intent shareBookInfo(VolumeInfo volumeInfo) {
        Intent myShareIntent = new Intent();
        myShareIntent.setAction(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, "I recommend you read book")
                .putExtra(Intent.EXTRA_TEXT, "I recommend you read this book: \n" + volumeInfo.getTitle() + " \n" + volumeInfo.getPreviewLink());
        return myShareIntent;
    }

    public static Intent goToRead(VolumeInfo volumeInfo) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(volumeInfo.getPreviewLink()));
    }

    public static Intent sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain")
                .putExtra(Intent.EXTRA_EMAIL, "novosvit_alyona@mail.ru")
                .putExtra(Intent.EXTRA_SUBJECT, "Book Search Application Feedback")
                .putExtra(Intent.EXTRA_TEXT, "Hello, Alyona! I used the book search app you wrote.");
        return intent;
    }
}
