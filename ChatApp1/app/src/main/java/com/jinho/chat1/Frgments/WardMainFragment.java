package com.jinho.chat1.Frgments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinho.chat1.R;


public class WardMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ward_main, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}