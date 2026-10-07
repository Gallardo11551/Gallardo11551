package com.example.uppueedutech.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.MainActivity;
import com.example.uppueedutech.R;

public class HistorietaFragment extends Fragment {

    private ImageButton btnCapitulo1;
    private ImageButton btnCapitulo2;
    private ImageButton btnCapitulo3;

    public HistorietaFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historieta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCapitulo1 = view.findViewById(R.id.btnCapitulo1);
        btnCapitulo2 = view.findViewById(R.id.btnCapitulo2);
        btnCapitulo3 = view.findViewById(R.id.btnCapitulo3);

        btnCapitulo1.setOnClickListener(v -> abrirCapitulo(R.drawable.capitulo1, "Capítulo 1"));
        btnCapitulo2.setOnClickListener(v -> abrirCapitulo(R.drawable.capitulo2, "Capítulo 2"));
        btnCapitulo3.setOnClickListener(v -> abrirCapitulo(R.drawable.capitulo3, "Capítulo 3"));
    }

    private void abrirCapitulo(int imagenResId, String titulo) {
        ComicViewerFragment fragment = ComicViewerFragment.newInstance(imagenResId, titulo);
        ((MainActivity) requireActivity()).cambiarFragment(fragment);
    }
}