package com.example.uppueedutech.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;

public class AcercaFragment extends Fragment {

    private ImageView imgLogo;
    private TextView txtDesarrollador;

    public AcercaFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acerca, container, false);

        imgLogo = view.findViewById(R.id.imgLogo);
        txtDesarrollador = view.findViewById(R.id.txtDesarrollador);

        try{
            Animation anim = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in);
            anim.setDuration(1200);
            imgLogo.startAnimation(anim);
        }catch(Exception ignored){}

        txtDesarrollador.setOnClickListener(v -> mostrarDialogo());

        return view;
    }

    private void mostrarDialogo(){

        new AlertDialog.Builder(requireContext())
                .setIcon(R.drawable.ti_logo)
                .setTitle(getString(R.string.acerca_desarrollador))
                .setMessage(
                        "Aplicación: "+getString(R.string.acerca_app)+
                                "\n"+getString(R.string.acerca_version)+
                                "\n\nCarrera:\n"+
                                getString(R.string.acerca_carrera)+
                                "\n\nUniversidad:\n"+
                                getString(R.string.acerca_universidad)+
                                "\n\nGracias por utilizar esta aplicación."
                )
                .setPositiveButton("Aceptar",null)
                .show();

    }
}
