package com.example.uppueedutech.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class EvaluacionFragment extends Fragment {

    private ImageView imgEstado;
    private ImageButton btnInfo;
    private Slider sliderProducto, sliderDesempeno, sliderConocimiento, sliderAsistencia;
    private TextView txtProducto, txtDesempeno, txtConocimiento, txtAsistencia, txtResultado;
    private MaterialButton btnCalcular;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evaluacion, container, false);

        imgEstado = view.findViewById(R.id.imgEstado);
        btnInfo = view.findViewById(R.id.btnInfo);

        sliderProducto = view.findViewById(R.id.sliderProducto);
        sliderDesempeno = view.findViewById(R.id.sliderDesempeno);
        sliderConocimiento = view.findViewById(R.id.sliderConocimiento);
        sliderAsistencia = view.findViewById(R.id.sliderAsistencia);

        txtProducto = view.findViewById(R.id.txtProducto);
        txtDesempeno = view.findViewById(R.id.txtDesempeno);
        txtConocimiento = view.findViewById(R.id.txtConocimiento);
        txtAsistencia = view.findViewById(R.id.txtAsistencia);
        txtResultado = view.findViewById(R.id.txtResultado);

        btnCalcular = view.findViewById(R.id.btnCalcular);

        Glide.with(this).asGif().load(R.drawable.estud1).into(imgEstado);

        sliderProducto.addOnChangeListener((s,v,f)->txtProducto.setText("Producto: "+(int)v+"%"));
        sliderDesempeno.addOnChangeListener((s,v,f)->txtDesempeno.setText("Desempeño: "+(int)v+"%"));
        sliderConocimiento.addOnChangeListener((s,v,f)->txtConocimiento.setText("Conocimiento: "+(int)v+"%"));
        sliderAsistencia.addOnChangeListener((s,v,f)->txtAsistencia.setText("Asistencia: "+(int)v+"%"));

        btnInfo.setOnClickListener(v -> mostrarInformacion());
        btnCalcular.setOnClickListener(v -> calcular());

        return view;
    }

    private void mostrarInformacion() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Sistema de Evaluación")
                .setMessage("• Producto\n• Desempeño\n• Conocimiento\n\n" +
                        "Para aprobar debes obtener mínimo 70% de promedio y 70% de asistencia.")
                .setPositiveButton("Entendido", null)
                .show();
    }

    private void calcular() {

        txtResultado.setTextColor(Color.WHITE);
        txtResultado.setText("⏳ Calculando...");

        Glide.with(this).asGif().load(R.drawable.estud1).into(imgEstado);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            float p = sliderProducto.getValue();
            float d = sliderDesempeno.getValue();
            float c = sliderConocimiento.getValue();
            float a = sliderAsistencia.getValue();

            float promedio = (p + d + c) / 3f;

            if (promedio >= 70 && a >= 70) {

                Glide.with(this).asGif().load(R.drawable.aprob).into(imgEstado);

                txtResultado.setTextColor(Color.parseColor("#2E7D32"));
                txtResultado.setText(
                        "🎉 APROBADO\n\n" +
                                "Promedio: " + (int) promedio + "%\n" +
                                "Asistencia: " + (int) a + "%\n\n" +
                                "¡Felicidades! Acreditaste la unidad.");

            } else {

                Glide.with(this).asGif().load(R.drawable.reprob).into(imgEstado);

                txtResultado.setTextColor(Color.parseColor("#C62828"));

                String motivo;
                if (promedio < 70 && a < 70)
                    motivo = "No alcanzaste el promedio ni la asistencia.";
                else if (promedio < 70)
                    motivo = "No alcanzaste el promedio mínimo.";
                else
                    motivo = "No alcanzaste el 70% de asistencia.";

                txtResultado.setText(
                        "REPROBADO\n\n" +
                                "Promedio: " + (int) promedio + "%\n" +
                                "Asistencia: " + (int) a + "%\n\n" +
                                motivo);
            }

        },1000);
    }
}