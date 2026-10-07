package com.example.uppueedutech.fragments;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;

public class TutoresFragment extends Fragment {

    private MaterialButton btnRebeca, btnMaria, btnYessenia, btnNora, btnCarlos;

    public TutoresFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutores, container, false);

        btnRebeca = view.findViewById(R.id.btnRebeca);
        btnMaria = view.findViewById(R.id.btnMaria);
        btnYessenia = view.findViewById(R.id.btnYessenia);
        btnNora = view.findViewById(R.id.btnNora);
        btnCarlos = view.findViewById(R.id.btnCarlos);

        btnRebeca.setOnClickListener(v ->
                mostrarTutor(
                        R.drawable.rebe1,
                        "Rebeca",
                        "rebeca@uppuebla.edu.mx",
                        "Lunes y Miércoles",
                        "10:00 - 12:00",
                        "Cubiculo"
                ));

        btnMaria.setOnClickListener(v ->
                mostrarTutor(
                        R.drawable.mar,
                        "Maria Auxilio",
                        "maria@uppuebla.edu.mx",
                        "Martes y Jueves",
                        "09:00 - 11:00",
                        "Cubiculo"
                ));

        btnYessenia.setOnClickListener(v ->
                mostrarTutor(
                        R.drawable.yes,
                        "Yessenia",
                        "yessenia@uppuebla.edu.mx",
                        "Miércoles y Viernes",
                        "08:00 - 10:00",
                        "D1-201"
                ));

        btnNora.setOnClickListener(v ->
                mostrarTutor(
                        R.drawable.nora,
                        "Nora",
                        "nora@uppuebla.edu.mx",
                        "Lunes y Jueves",
                        "11:00 - 13:00",
                        "LC-11"
                ));

        btnCarlos.setOnClickListener(v ->
                mostrarTutor(
                        R.drawable.gude,
                        "Gudelia Pilar",
                        "gudelia@uppuebla.edu.mx",
                        "Martes y Viernes",
                        "10:00 - 12:00",
                        "Cubiculo-302"
                ));

        return view;
    }

    private void mostrarTutor(int imagen,
                              String nombre,
                              String correo,
                              String dias,
                              String horario,
                              String salon) {

        Dialog dialog = new Dialog(requireContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tutor);

        ImageView imgTutor = dialog.findViewById(R.id.imgTutor);
        TextView txtNombre = dialog.findViewById(R.id.txtNombre);
        TextView txtCorreo = dialog.findViewById(R.id.txtCorreo);
        TextView txtDias = dialog.findViewById(R.id.txtDias);
        TextView txtHorario = dialog.findViewById(R.id.txtHorario);
        TextView txtSalon = dialog.findViewById(R.id.txtSalon);

        MaterialButton btnCopiar = dialog.findViewById(R.id.btnCopiar);
        MaterialButton btnCerrar = dialog.findViewById(R.id.btnCerrar);

        imgTutor.setImageResource(imagen);
        txtNombre.setText(nombre);
        txtCorreo.setText("Correo: " + correo);
        txtDias.setText("Días: " + dias);
        txtHorario.setText("Horario: " + horario);
        txtSalon.setText("Salón: " + salon);

        btnCopiar.setOnClickListener(v -> {

            ClipboardManager clipboard =
                    (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clip = ClipData.newPlainText("Correo", correo);

            clipboard.setPrimaryClip(clip);

            Toast.makeText(getContext(),
                    "Correo copiado al portapapeles",
                    Toast.LENGTH_SHORT).show();

        });

        btnCerrar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

    }

}