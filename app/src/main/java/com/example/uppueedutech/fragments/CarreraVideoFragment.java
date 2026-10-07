package com.example.uppueedutech.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;

public class CarreraVideoFragment extends Fragment {

    private VideoView videoView;

    public CarreraVideoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carrera_video, container, false);

        videoView = view.findViewById(R.id.videoCarrera);

        String path = "android.resource://" +
                requireContext().getPackageName() +
                "/" + R.raw.video_carrera;

        Uri uri = Uri.parse(path);

        videoView.setVideoURI(uri);

        MediaController mediaController =
                new MediaController(requireContext());

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);

        videoView.start();

        return view;
    }

}