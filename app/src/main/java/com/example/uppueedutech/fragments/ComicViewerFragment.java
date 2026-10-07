package com.example.uppueedutech.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ComicViewerFragment extends Fragment {

    private static final String ARG_IMAGEN = "arg_imagen";
    private static final String ARG_TITULO = "arg_titulo";

    private int imagenResId;
    private String titulo;

    public ComicViewerFragment() {
        // Constructor vacío requerido
    }

    // Factory method: así se crea el fragment pasándole el capítulo a mostrar
    public static ComicViewerFragment newInstance(int imagenResId, String titulo) {
        ComicViewerFragment fragment = new ComicViewerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGEN, imagenResId);
        args.putString(ARG_TITULO, titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagenResId = getArguments().getInt(ARG_IMAGEN);
            titulo = getArguments().getString(ARG_TITULO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comic_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhotoView photoView = view.findViewById(R.id.photoViewComic);
        photoView.setImageResource(imagenResId);

        if (getActivity() != null && titulo != null) {
            requireActivity().setTitle(titulo);
        }
    }
}