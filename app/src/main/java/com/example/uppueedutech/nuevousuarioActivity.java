package com.example.uppueedutech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class nuevousuarioActivity extends AppCompatActivity {

    // Firebase Authentication
    private FirebaseAuth mAuth;

    // Campos del formulario
    private TextInputEditText edtNuevoUsuario;
    private TextInputEditText edtCorreo;
    private TextInputEditText edtNuevaPassword;
    private TextInputEditText edtConfirmarPassword;

    // Botones
    private MaterialButton btnRegistrar;
    private MaterialButton btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Conectar la Activity con el XML
        setContentView(R.layout.activity_nuevousuario);

        // Inicializar Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Vincular campos del XML
        edtNuevoUsuario = findViewById(R.id.edtNuevoUsuario);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtNuevaPassword = findViewById(R.id.edtNuevaPassword);
        edtConfirmarPassword = findViewById(R.id.edtConfirmarPassword);

        // Vincular botones
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        // =====================================
        // BOTÓN REGISTRAR
        // =====================================

        btnRegistrar.setOnClickListener(v -> {

            // Obtener los datos
            String usuario = edtNuevoUsuario.getText()
                    .toString()
                    .trim();

            String correo = edtCorreo.getText()
                    .toString()
                    .trim();

            String password = edtNuevaPassword.getText()
                    .toString()
                    .trim();

            String confirmarPassword = edtConfirmarPassword.getText()
                    .toString()
                    .trim();

            // =====================================
            // VALIDAR NOMBRE DE USUARIO
            // =====================================
            if (usuario.isEmpty()) {

                edtNuevoUsuario.setError(
                        "Escribe un nombre de usuario"
                );

                return;
            }
            if (usuario.length() < 4) {
                edtNuevoUsuario.setError("El usuario debe tener al menos 4 caracteres");
                edtNuevoUsuario.requestFocus();
                return;
            }

            if (usuario.length() > 20) {
                edtNuevoUsuario.setError("El usuario no puede tener más de 20 caracteres");
                edtNuevoUsuario.requestFocus();
                return;
            }
            if (!usuario.equals(usuario.trim())) {
                edtNuevoUsuario.setError("No escribas espacios al inicio o final");
                edtNuevoUsuario.requestFocus();
                return;
            }
            if (!usuario.matches("^[a-zA-Z0-9_]+$")) {
                edtNuevoUsuario.setError("Solo letras, números y _");
                edtNuevoUsuario.requestFocus();
                return;
            }
            if (usuario.equalsIgnoreCase("admin") ||
                    usuario.equalsIgnoreCase("usuario") ||
                    usuario.equalsIgnoreCase("test")) {

                edtNuevoUsuario.setError("Ese nombre de usuario no está permitido");
                edtNuevoUsuario.requestFocus();
                return;
            }
            if (usuario.equalsIgnoreCase(correo)) {
                edtNuevoUsuario.setError("El usuario no puede ser igual al correo");
                edtNuevoUsuario.requestFocus();
                return;
            }
            // =====================================
            // VALIDAR CORREO
            // =====================================

            if (correo.isEmpty()) {

                edtCorreo.setError(
                        "Escribe un correo electrónico"
                );

                return;
            }
            if (!correo.endsWith("@uppuebla.edu.mx")) {
                edtCorreo.setError("Debe utilizar un correo institucional");
                edtCorreo.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(correo)
                    .matches()) {

                edtCorreo.setError(
                        "Correo electrónico no válido"
                );

                return;
            }

            // =====================================
            // VALIDAR CONTRASEÑA
            // =====================================

            if (password.isEmpty()) {

                edtNuevaPassword.setError(
                        "Escribe una contraseña"
                );

                return;
            }

            if (password.length() < 6) {

                edtNuevaPassword.setError(
                        "La contraseña debe tener mínimo 6 caracteres"
                );

                return;
            }

            // =====================================
            // VALIDAR CONFIRMACIÓN
            // =====================================

            if (confirmarPassword.isEmpty()) {

                edtConfirmarPassword.setError(
                        "Confirma tu contraseña"
                );

                return;
            }

            if (!password.equals(confirmarPassword)) {

                edtConfirmarPassword.setError(
                        "Las contraseñas no coinciden"
                );

                return;
            }

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("usuarios");

            ref.orderByChild("nombre")
                    .equalTo(usuario)
                    .get()
                    .addOnSuccessListener(snapshot -> {

                        if (snapshot.exists()) {
                            edtNuevoUsuario.setError("Ese usuario ya existe");
                            btnRegistrar.setEnabled(true);
                            return;
                        }

                        // Aquí crear el usuario con FirebaseAuth
                    });

            // Deshabilitar botón para evitar doble registro
            btnRegistrar.setEnabled(false);

            // =====================================
            // CREAR USUARIO EN FIREBASE AUTH
            // =====================================

            mAuth.createUserWithEmailAndPassword(
                            correo,
                            password
                    )
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            // Obtener el usuario creado
                            FirebaseUser user =
                                    mAuth.getCurrentUser();

                            if (user != null) {

                                // Obtener UID
                                String uid =
                                        user.getUid();

                                // Referencia a Realtime Database
                                DatabaseReference database =
                                        FirebaseDatabase
                                                .getInstance()
                                                .getReference("usuarios")
                                                .child(uid);

                                // Crear datos del usuario
                                HashMap<String, Object> datos =
                                        new HashMap<>();

                                datos.put(
                                        "nombre",
                                        usuario
                                );

                                datos.put(
                                        "correo",
                                        correo
                                );

                                datos.put(
                                        "rol",
                                        "alumno"
                                );

                                // =====================================
                                // GUARDAR EN REALTIME DATABASE
                                // =====================================

                                database.setValue(datos)
                                        .addOnCompleteListener(
                                                databaseTask -> {

                                                    if (databaseTask
                                                            .isSuccessful()) {

                                                        // Mostrar mensaje
                                                        Toast.makeText(
                                                                nuevousuarioActivity.this,
                                                                "¡Registro exitoso!",
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                        // Esperar 2 segundos
                                                        // antes de ir al login
                                                        new Handler()
                                                                .postDelayed(
                                                                        () -> {

                                                                            Intent intent =
                                                                                    new Intent(
                                                                                            nuevousuarioActivity.this,
                                                                                            loginActivity.class
                                                                                    );

                                                                            startActivity(
                                                                                    intent
                                                                            );

                                                                            finish();

                                                                        },
                                                                        2000
                                                                );

                                                    } else {

                                                        // Reactivar botón
                                                        btnRegistrar
                                                                .setEnabled(
                                                                        true
                                                                );

                                                        Toast.makeText(
                                                                nuevousuarioActivity.this,
                                                                "Error al guardar los datos en la base de datos",
                                                                Toast.LENGTH_LONG
                                                        ).show();
                                                    }
                                                }
                                        );
                            }

                        } else {

                            // Reactivar botón
                            btnRegistrar.setEnabled(true);

                            String mensajeError;

                            if (task.getException() != null) {

                                mensajeError =
                                        task.getException()
                                                .getMessage();

                            } else {

                                mensajeError =
                                        "Error desconocido";
                            }

                            Toast.makeText(
                                    nuevousuarioActivity.this,
                                    "Error al registrar: "
                                            + mensajeError,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    });
        });

        // =====================================
        // BOTÓN INICIAR SESIÓN
        // =====================================

        btnIniciarSesion.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            nuevousuarioActivity.this,
                            loginActivity.class
                    );

            startActivity(intent);

            finish();
        });
    }
}