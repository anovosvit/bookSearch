package com.github.anovosvit.searchbook.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.databinding.FragmentFeedbackBinding;

public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false);

        binding.button.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, "novosvit_alyona@mail.ru");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Book Search Application Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, "Hello, Alyona! I used the book search app you wrote.");

            startActivity(Intent.createChooser(intent, "Send Email"));
        });
        return binding.getRoot();
    }
}