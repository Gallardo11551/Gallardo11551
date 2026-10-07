package com.example.uppueedutech.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;

public class CaracteristicasFragment extends Fragment {

    private FrameLayout proyecto1, proyecto2, proyecto3, proyecto4;

    public CaracteristicasFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_caracteristicas, container, false);

        proyecto1 = view.findViewById(R.id.proyecto1);
        proyecto2 = view.findViewById(R.id.proyecto2);
        proyecto3 = view.findViewById(R.id.proyecto3);
        proyecto4 = view.findViewById(R.id.proyecto4);

        proyecto1.setOnClickListener(v ->
                mostrarProyecto(
                        getString(R.string.proyecto1_titulo),
                        getString(R.string.proyecto1_descripcion)));

        proyecto2.setOnClickListener(v ->
                mostrarProyecto(
                        getString(R.string.proyecto2_titulo),
                        getString(R.string.proyecto2_descripcion)));

        proyecto3.setOnClickListener(v ->
                mostrarProyecto(
                        getString(R.string.proyecto3_titulo),
                        getString(R.string.proyecto3_descripcion)));

        proyecto4.setOnClickListener(v ->
                mostrarProyecto(
                        getString(R.string.proyecto4_titulo),
                        getString(R.string.proyecto4_descripcion)));

        return view;
    }

    private void mostrarProyecto(String titulo, String descripcion) {

        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_proyecto);

        // Hacer el diálogo transparente y sin bordes
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        TextView txtTitulo = dialog.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = dialog.findViewById(R.id.txtDescripcion);
        MaterialButton btnCerrar = dialog.findViewById(R.id.btnCerrar);

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

        btnCerrar.setOnClickListener(v -> dialog.dismiss());

        // Permitir cerrar tocando fuera del diálogo
        dialog.setCancelable(true);

        dialog.show();
    }
}