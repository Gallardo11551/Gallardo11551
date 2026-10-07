package com.example.uppueedutech.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ModeloFragment extends Fragment {

    private MaterialCardView cardQueEs, cardObjetivo, cardMision;
    private MaterialCardView cardCompetencias, cardValores, cardPerfilEgreso;

    private MaterialButton btnCaracteristicas;

    public ModeloFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_modelo, container, false);

        cardQueEs = vista.findViewById(R.id.cardQueEs);
        cardObjetivo = vista.findViewById(R.id.cardObjetivo);
        cardMision = vista.findViewById(R.id.cardMision);
        cardCompetencias = vista.findViewById(R.id.cardCompetencias);
        cardValores = vista.findViewById(R.id.cardValores);
        cardPerfilEgreso = vista.findViewById(R.id.cardBeneficios);

        btnCaracteristicas = vista.findViewById(R.id.btnCaracteristicas);

        cardQueEs.setOnClickListener(v ->
                mostrarDialogo("¿Qué es?",
                        getString(R.string.que_es_texto)));

        cardObjetivo.setOnClickListener(v ->
                mostrarDialogo("Objetivo",
                        getString(R.string.objetivo_texto)));

        cardMision.setOnClickListener(v ->
                mostrarDialogo("Misión",
                        getString(R.string.mision_texto)));

        cardCompetencias.setOnClickListener(v ->
                mostrarDialogo("Competencias",
                        getString(R.string.competencias_texto)));

        cardValores.setOnClickListener(v ->
                mostrarDialogo("Valores",
                        getString(R.string.valores_texto)));

        cardPerfilEgreso.setOnClickListener(v ->
                mostrarDialogo(getString(R.string.perfil_egreso),
                        getString(R.string.perfil_egreso_texto)));

        btnCaracteristicas.setOnClickListener(v -> {

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container,
                            new CaracteristicasFragment())
                    .addToBackStack(null)
                    .commit();

        });

        return vista;
    }

    private void mostrarDialogo(String titulo, String mensaje){

        new AlertDialog.Builder(requireContext())
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();

    }

}