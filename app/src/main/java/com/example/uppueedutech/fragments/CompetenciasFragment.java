package com.example.uppueedutech.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.card.MaterialCardView;

public class CompetenciasFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    public CompetenciasFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_competencias, container, false);

        MaterialCardView card1 = view.findViewById(R.id.cardCompetencia1);
        MaterialCardView card2 = view.findViewById(R.id.cardCompetencia2);
        MaterialCardView card3 = view.findViewById(R.id.cardCompetencia3);
        MaterialCardView card4 = view.findViewById(R.id.cardCompetencia4);

        card1.setOnClickListener(v -> reproducirAudio(R.raw.aprender));

        card2.setOnClickListener(v -> reproducirAudio(R.raw.equipo));

        card3.setOnClickListener(v -> reproducirAudio(R.raw.innovacion));

        card4.setOnClickListener(v -> reproducirAudio(R.raw.problemas));

        return view;
    }

    private void reproducirAudio(int audio) {

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(getContext(), audio);

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}