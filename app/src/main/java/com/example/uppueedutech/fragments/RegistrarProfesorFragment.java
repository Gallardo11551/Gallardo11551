package com.example.uppueedutech.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.Profesor;
import com.example.uppueedutech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistrarProfesorFragment extends Fragment {

    private EditText edtNumeroEmpleado;
    private EditText edtNombreProfesor;
    private EditText edtCorreoProfesor;
    private EditText edtTelefonoProfesor;

    private RadioGroup rgSexoProfesor;
    private RadioButton rbProfesorHombre;
    private RadioButton rbProfesorMujer;

    private Spinner spEspecialidad;
    private Spinner spGradoAcademico;

    private CheckBox chkPOO;
    private CheckBox chkBD;
    private CheckBox chkRedes;
    private CheckBox chkCiberseguridad;

    private Button btnRegistrarProfesor;
    private Button btnBuscarProfesor;
    private Button btnActualizarProfesor;
    private Button btnEliminarProfesor;

    private ListView listViewProfesores;

    private DatabaseReference referencia;

    private ArrayList<Profesor> listaProfesores;
    private ArrayAdapter<String> adapterLista;

    public RegistrarProfesorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_registrar_profesor,
                container,
                false);

        //==========================
        // Vincular controles
        //==========================

        edtNumeroEmpleado = view.findViewById(R.id.edtNumeroEmpleado);
        edtNombreProfesor = view.findViewById(R.id.edtNombreProfesor);
        edtCorreoProfesor = view.findViewById(R.id.edtCorreoProfesor);
        edtTelefonoProfesor = view.findViewById(R.id.edtTelefonoProfesor);

        rgSexoProfesor = view.findViewById(R.id.rgSexoProfesor);

        rbProfesorHombre = view.findViewById(R.id.rbProfesorHombre);
        rbProfesorMujer = view.findViewById(R.id.rbProfesorMujer);

        spEspecialidad = view.findViewById(R.id.spEspecialidad);
        spGradoAcademico = view.findViewById(R.id.spGradoAcademico);

        chkPOO = view.findViewById(R.id.chkPOO);
        chkBD = view.findViewById(R.id.chkBD);
        chkRedes = view.findViewById(R.id.chkRedes);
        chkCiberseguridad = view.findViewById(R.id.chkCiberseguridad);

        btnRegistrarProfesor = view.findViewById(R.id.btnRegistrarProfesor);
        btnBuscarProfesor = view.findViewById(R.id.btnBuscarProfesor);
        btnActualizarProfesor = view.findViewById(R.id.btnActualizarProfesor);
        btnEliminarProfesor = view.findViewById(R.id.btnEliminarProfesor);

        listViewProfesores = view.findViewById(R.id.listViewProfesores);

        //==========================
        // Firebase
        //==========================

        referencia = FirebaseDatabase
                .getInstance()
                .getReference("Profesores");

        cargarSpinners();

        //==========================
        // Eventos botones
        //==========================

        btnRegistrarProfesor.setOnClickListener(v -> registrarProfesor());

        btnBuscarProfesor.setOnClickListener(v -> buscarProfesor());

        btnActualizarProfesor.setOnClickListener(v -> actualizarProfesor());

        btnEliminarProfesor.setOnClickListener(v -> eliminarProfesor());

        mostrarProfesores();

        listViewProfesores.setOnItemClickListener((parent, view1, position, id) -> {

            Profesor profesor = listaProfesores.get(position);

            cargarDatosProfesor(profesor);

        });

        return view;
    }

    //==========================
    // Cargar Spinners
    //==========================

    private void cargarSpinners() {

        String[] especialidades = {

                "Programación",
                "Bases de Datos",
                "Redes",
                "Ciberseguridad",
                "Desarrollo Web",
                "Inteligencia Artificial"

        };

        ArrayAdapter<String> adapterEspecialidad =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        especialidades);

        adapterEspecialidad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spEspecialidad.setAdapter(adapterEspecialidad);

        String[] grados = {

                "Licenciatura",
                "Maestría",
                "Doctorado"

        };

        ArrayAdapter<String> adapterGrado =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        grados);

        adapterGrado.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spGradoAcademico.setAdapter(adapterGrado);

    }
    //==================================================
// REGISTRAR PROFESOR
//==================================================

    private void registrarProfesor() {

        String numeroEmpleado = edtNumeroEmpleado.getText().toString().trim();
        String nombre = edtNombreProfesor.getText().toString().trim();
        String correo = edtCorreoProfesor.getText().toString().trim();
        String telefono = edtTelefonoProfesor.getText().toString().trim();

        String sexo = "";
        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {

            edtNombreProfesor.setError("Solo se permiten letras");
            edtNombreProfesor.requestFocus();
            return;
        }

        if (!telefono.matches("\\d+")) {

            edtTelefonoProfesor.setError("Solo números");
            edtTelefonoProfesor.requestFocus();
            return;
        }
        if (telefono.length() != 10) {

            edtTelefonoProfesor.setError("Debe tener 10 dígitos");
            edtTelefonoProfesor.requestFocus();
            return;
        }
        if (rbProfesorHombre.isChecked()) {
            sexo = "Hombre";
        } else if (rbProfesorMujer.isChecked()) {
            sexo = "Mujer";
        }

        String especialidad = spEspecialidad.getSelectedItem().toString();
        String grado = spGradoAcademico.getSelectedItem().toString();

        String materias = "";

        if (chkPOO.isChecked())
            materias += "POO ";

        if (chkBD.isChecked())
            materias += "Bases de Datos ";

        if (chkRedes.isChecked())
            materias += "Redes ";

        if (chkCiberseguridad.isChecked())
            materias += "Ciberseguridad";

        if (numeroEmpleado.isEmpty() ||
                nombre.isEmpty() ||
                correo.isEmpty() ||
                telefono.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Profesor profesor = new Profesor(
                numeroEmpleado,
                nombre,
                correo,
                telefono,
                sexo,
                especialidad,
                grado,
                materias
        );

        referencia.child(numeroEmpleado).setValue(profesor);

        Toast.makeText(
                requireContext(),
                "Profesor registrado correctamente",
                Toast.LENGTH_SHORT
        ).show();

        limpiarCampos();
    }
    //==================================================
// LIMPIAR CAMPOS
//==================================================

    private void limpiarCampos() {

        edtNumeroEmpleado.setText("");
        edtNombreProfesor.setText("");
        edtCorreoProfesor.setText("");
        edtTelefonoProfesor.setText("");

        rgSexoProfesor.clearCheck();

        spEspecialidad.setSelection(0);
        spGradoAcademico.setSelection(0);

        chkPOO.setChecked(false);
        chkBD.setChecked(false);
        chkRedes.setChecked(false);
        chkCiberseguridad.setChecked(false);

        edtNumeroEmpleado.requestFocus();
    }
    //==================================================
// BUSCAR PROFESOR
//==================================================

    private void buscarProfesor() {

        String numeroEmpleado = edtNumeroEmpleado.getText().toString().trim();

        if (numeroEmpleado.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Ingrese el número de empleado",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        referencia.child(numeroEmpleado)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            Profesor profesor = snapshot.getValue(Profesor.class);

                            if (profesor != null) {

                                cargarDatosProfesor(profesor);

                                Toast.makeText(
                                        requireContext(),
                                        "Profesor encontrado",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                        } else {

                            Toast.makeText(
                                    requireContext(),
                                    "Profesor no encontrado",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                        Toast.makeText(
                                requireContext(),
                                error.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }
    //==================================================
// MOSTRAR PROFESORES
//==================================================

    private void mostrarProfesores() {

        listaProfesores = new ArrayList<>();

        referencia.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                listaProfesores.clear();

                for (DataSnapshot dato : snapshot.getChildren()) {

                    Profesor profesor = dato.getValue(Profesor.class);

                    if (profesor != null) {

                        listaProfesores.add(profesor);

                    }

                }

                ArrayList<String> datosMostrar = new ArrayList<>();

                for (Profesor profesor : listaProfesores) {

                    datosMostrar.add(
                            "Empleado: " + profesor.getNumeroEmpleado()
                                    + "\nNombre: " + profesor.getNombre()
                                    + "\nEspecialidad: " + profesor.getEspecialidad()
                    );

                }

                adapterLista = new ArrayAdapter<>(

                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        datosMostrar

                );

                listViewProfesores.setAdapter(adapterLista);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Toast.makeText(
                        requireContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }
    //==================================================
// CARGAR DATOS DEL PROFESOR
//==================================================

    private void cargarDatosProfesor(Profesor profesor) {

        edtNumeroEmpleado.setText(profesor.getNumeroEmpleado());
        edtNombreProfesor.setText(profesor.getNombre());
        edtCorreoProfesor.setText(profesor.getCorreo());
        edtTelefonoProfesor.setText(profesor.getTelefono());

        if (profesor.getSexo().equals("Hombre")) {

            rbProfesorHombre.setChecked(true);

        } else {

            rbProfesorMujer.setChecked(true);

        }

        int posicionEspecialidad =
                ((ArrayAdapter) spEspecialidad.getAdapter())
                        .getPosition(profesor.getEspecialidad());

        spEspecialidad.setSelection(posicionEspecialidad);

        int posicionGrado =
                ((ArrayAdapter) spGradoAcademico.getAdapter())
                        .getPosition(profesor.getGradoAcademico());

        spGradoAcademico.setSelection(posicionGrado);

        String materias = profesor.getMaterias();

        chkPOO.setChecked(
                materias.contains("POO"));

        chkBD.setChecked(
                materias.contains("Bases de Datos"));

        chkRedes.setChecked(
                materias.contains("Redes"));

        chkCiberseguridad.setChecked(
                materias.contains("Ciberseguridad"));

    }
//==================================================
// ACTUALIZAR PROFESOR
//==================================================

    private void actualizarProfesor() {

        String numeroEmpleado = edtNumeroEmpleado.getText().toString().trim();
        String nombre = edtNombreProfesor.getText().toString().trim();
        String correo = edtCorreoProfesor.getText().toString().trim();
        String telefono = edtTelefonoProfesor.getText().toString().trim();

        String sexo = "";
        if (sexo.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Seleccione el sexo",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }
        if (rbProfesorHombre.isChecked()) {
            sexo = "Hombre";
        } else if (rbProfesorMujer.isChecked()) {
            sexo = "Mujer";
        }

        String especialidad = spEspecialidad.getSelectedItem().toString();
        String grado = spGradoAcademico.getSelectedItem().toString();

        String materias = "";

        if (chkPOO.isChecked()) {
            materias += "POO ";
        }

        if (chkBD.isChecked()) {
            materias += "Bases de Datos ";
        }

        if (chkRedes.isChecked()) {
            materias += "Redes ";
        }

        if (chkCiberseguridad.isChecked()) {
            materias += "Ciberseguridad";
        }


        if (numeroEmpleado.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Seleccione un profesor primero",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        Profesor profesor = new Profesor(
                numeroEmpleado,
                nombre,
                correo,
                telefono,
                sexo,
                especialidad,
                grado,
                materias
        );


        referencia.child(numeroEmpleado)
                .setValue(profesor);


        Toast.makeText(
                requireContext(),
                "Profesor actualizado correctamente",
                Toast.LENGTH_SHORT
        ).show();


        limpiarCampos();

    }


//==================================================
// ELIMINAR PROFESOR
//==================================================

    private void eliminarProfesor() {

        String numeroEmpleado =
                edtNumeroEmpleado.getText().toString().trim();


        if (numeroEmpleado.isEmpty()) {

            Toast.makeText(
                    requireContext(),
                    "Seleccione un profesor primero",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        referencia.child(numeroEmpleado)
                .removeValue();


        Toast.makeText(
                requireContext(),
                "Profesor eliminado correctamente",
                Toast.LENGTH_SHORT
        ).show();


        limpiarCampos();

    }
}