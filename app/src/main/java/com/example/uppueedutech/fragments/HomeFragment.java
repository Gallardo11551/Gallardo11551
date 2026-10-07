package com.example.uppueedutech.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    MaterialButton btnComenzar;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        btnComenzar = vista.findViewById(R.id.btnComenzar);

        btnComenzar.setOnClickListener(v -> {

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, new ModeloFragment())
                    .commit();

        });

        return vista;
    }
}