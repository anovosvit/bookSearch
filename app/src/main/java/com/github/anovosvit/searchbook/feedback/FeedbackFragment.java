package com.github.anovosvit.searchbook.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.github.anovosvit.searchbook.R;
import com.github.anovosvit.searchbook.databinding.FragmentFeedbackBinding;
import com.github.anovosvit.searchbook.utils.Helper;

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

        binding.button.setOnClickListener(v ->
                startActivity(Intent.createChooser(Helper.sendFeedback(), "Send Email"))
        );
        return binding.getRoot();
    }
}