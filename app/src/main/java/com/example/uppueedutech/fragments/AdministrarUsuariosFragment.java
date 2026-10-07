package com.example.uppueedutech.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uppueedutech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdministrarUsuariosFragment extends Fragment {

    // Componentes gráficos
    private ListView listViewUsuarios;
    private Button btnEliminarUsuario;
    private TextView txtTotalUsuarios;
    private TextView txtUsuarioSeleccionado;

    // Firebase
    private DatabaseReference referenciaUsuarios;
    private DatabaseReference referenciaProfesores;

    // Lista que se mostrará en el ListView
    private ArrayList<UsuarioItem> listaUsuarios;
    private ArrayList<String> listaMostrar;
    private ArrayAdapter<String> adapter;

    // Usuario seleccionado
    private UsuarioItem usuarioSeleccionado;

    public AdministrarUsuariosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_administrar_usuarios,
                container,
                false);

        //===========================
        // Vincular controles
        //===========================

        listViewUsuarios = view.findViewById(R.id.listViewUsuarios);
        btnEliminarUsuario = view.findViewById(R.id.btnEliminarUsuario);
        txtTotalUsuarios = view.findViewById(R.id.txtTotalUsuarios);
        txtUsuarioSeleccionado = view.findViewById(R.id.txtUsuarioSeleccionado);

        //===========================
        // Firebase
        //===========================

        referenciaUsuarios = FirebaseDatabase
                .getInstance()
                .getReference("usuarios");

        referenciaProfesores = FirebaseDatabase
                .getInstance()
                .getReference("Profesores");

        //===========================
        // Inicializar listas
        //===========================

        listaUsuarios = new ArrayList<>();
        listaMostrar = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                listaMostrar
        );

        listViewUsuarios.setAdapter(adapter);

        //===========================
        // Cargar información
        //===========================

        cargarAlumnos();

        //===========================
        // Seleccionar usuario
        //===========================

        listViewUsuarios.setOnItemClickListener((parent, view1, position, id) -> {

            usuarioSeleccionado = listaUsuarios.get(position);

            txtUsuarioSeleccionado.setText(
                    "Usuario seleccionado: "
                            + usuarioSeleccionado.nombre
            );

        });

        //===========================
        // Eliminar usuario
        //===========================

        btnEliminarUsuario.setOnClickListener(v -> {

            if (usuarioSeleccionado == null) {

                Toast.makeText(
                        requireContext(),
                        "Seleccione un usuario",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            eliminarUsuario();

        });

        return view;

    }
    //=====================================================
// CARGAR ALUMNOS
//=====================================================

    private void cargarAlumnos() {

        referenciaUsuarios.get().addOnSuccessListener(snapshot -> {

            listaUsuarios.clear();
            listaMostrar.clear();

            for (DataSnapshot dato : snapshot.getChildren()) {

                String id = dato.getKey();

                String nombre = "";
                String correo = "";

                if (dato.child("nombre").getValue() != null)
                    nombre = dato.child("nombre").getValue().toString();

                if (dato.child("correo").getValue() != null)
                    correo = dato.child("correo").getValue().toString();

                UsuarioItem usuario = new UsuarioItem();

                usuario.id = id;
                usuario.nombre = nombre;
                usuario.correo = correo;
                usuario.tipo = "Alumno";

                listaUsuarios.add(usuario);

            }

            cargarProfesores();

        });

    }

//=====================================================
// CARGAR PROFESORES
//=====================================================

    private void cargarProfesores() {

        referenciaProfesores.get().addOnSuccessListener(snapshot -> {

            for (DataSnapshot dato : snapshot.getChildren()) {

                String id = dato.getKey();

                String nombre = "";
                String correo = "";

                if (dato.child("nombre").getValue() != null)
                    nombre = dato.child("nombre").getValue().toString();

                if (dato.child("correo").getValue() != null)
                    correo = dato.child("correo").getValue().toString();

                UsuarioItem profesor = new UsuarioItem();

                profesor.id = id;
                profesor.nombre = nombre;
                profesor.correo = correo;
                profesor.tipo = "Profesor";

                listaUsuarios.add(profesor);

            }

            mostrarUsuarios();

        });

    }

//=====================================================
// MOSTRAR USUARIOS
//=====================================================

    private void mostrarUsuarios() {

        listaMostrar.clear();

        for (UsuarioItem usuario : listaUsuarios) {

            String texto = "";

            texto += "Tipo: " + usuario.tipo + "\n";
            texto += "Nombre: " + usuario.nombre + "\n";
            texto += "Correo: " + usuario.correo;

            listaMostrar.add(texto);

        }

        adapter.notifyDataSetChanged();

        actualizarContador();

    }

//=====================================================
// ACTUALIZAR CONTADOR
//=====================================================

    private void actualizarContador() {

        txtTotalUsuarios.setText(
                "Total de usuarios: " + listaUsuarios.size()
        );

    }
    //=====================================================
// ELIMINAR USUARIO
//=====================================================

    private void eliminarUsuario() {

        if (usuarioSeleccionado == null) {
            return;
        }

        DatabaseReference referencia;

        if (usuarioSeleccionado.tipo.equals("Alumno")) {

            referencia = referenciaUsuarios.child(usuarioSeleccionado.id);

        } else {

            referencia = referenciaProfesores.child(usuarioSeleccionado.id);

        }

        referencia.removeValue()
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            requireContext(),
                            "Usuario eliminado correctamente",
                            Toast.LENGTH_SHORT
                    ).show();

                    usuarioSeleccionado = null;

                    txtUsuarioSeleccionado.setText(
                            "Usuario seleccionado: Ninguno"
                    );

                    cargarAlumnos();

                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            requireContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();

                });

    }

//=====================================================
// CLASE PARA GUARDAR LOS DATOS
//=====================================================

    private static class UsuarioItem {

        String id;
        String nombre;
        String correo;
        String tipo;

    }
}