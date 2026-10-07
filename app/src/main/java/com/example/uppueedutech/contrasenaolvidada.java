package com.example.uppueedutech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class contrasenaolvidada extends AppCompatActivity {

    // Firebase Authentication
    private FirebaseAuth mAuth;

    // Elementos del XML
    private TextInputEditText edtCorreoRecuperar;

    private MaterialButton btnEnviarRecuperacion;
    private MaterialButton btnRegresarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contrasenaolvidada);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();

        // Vincular controles
        edtCorreoRecuperar = findViewById(R.id.edtCorreoRecuperar);
        btnEnviarRecuperacion = findViewById(R.id.btnEnviarRecuperacion);
        btnRegresarLogin = findViewById(R.id.btnRegresarLogin);

        // =====================================
        // BOTÓN ENVIAR RECUPERACIÓN
        // =====================================

        btnEnviarRecuperacion.setOnClickListener(v -> {

            String correo = edtCorreoRecuperar.getText().toString().trim();

            // Validar campo vacío
            if (correo.isEmpty()) {
                edtCorreoRecuperar.setError("Escribe tu correo electrónico");
                edtCorreoRecuperar.requestFocus();
                return;
            }

            // Validar formato del correo
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                edtCorreoRecuperar.setError("Correo electrónico no válido");
                edtCorreoRecuperar.requestFocus();
                return;
            }

            btnEnviarRecuperacion.setEnabled(false);

            DatabaseReference referencia =
                    FirebaseDatabase.getInstance()
                            .getReference("usuarios");

            referencia.orderByChild("correo")
                    .equalTo(correo)
                    .get()
                    .addOnCompleteListener(taskConsulta -> {

                        if (taskConsulta.isSuccessful()) {

                            DataSnapshot snapshot = taskConsulta.getResult();

                            if (!snapshot.exists()) {

                                btnEnviarRecuperacion.setEnabled(true);

                                edtCorreoRecuperar.setError("Este correo no está registrado");
                                edtCorreoRecuperar.requestFocus();

                                Toast.makeText(
                                        contrasenaolvidada.this,
                                        "No existe una cuenta con ese correo",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }

                            // El correo existe, enviar recuperación
                            mAuth.sendPasswordResetEmail(correo)
                                    .addOnCompleteListener(task -> {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(
                                                    contrasenaolvidada.this,
                                                    "Correo de recuperación enviado correctamente",
                                                    Toast.LENGTH_LONG
                                            ).show();

                                            edtCorreoRecuperar.setText("");

                                            new android.os.Handler().postDelayed(() -> {

                                                Intent intent = new Intent(
                                                        contrasenaolvidada.this,
                                                        loginActivity.class
                                                );

                                                startActivity(intent);
                                                finish();

                                            }, 2000);

                                        } else {

                                            btnEnviarRecuperacion.setEnabled(true);

                                            String mensajeError = "Error desconocido";

                                            if (task.getException() != null) {
                                                mensajeError = task.getException().getMessage();
                                            }

                                            Toast.makeText(
                                                    contrasenaolvidada.this,
                                                    mensajeError,
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }

                                    });

                        } else {

                            btnEnviarRecuperacion.setEnabled(true);

                            Toast.makeText(
                                    contrasenaolvidada.this,
                                    "Error al consultar la base de datos",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    });

        });

        // =====================================
        // BOTÓN REGRESAR AL LOGIN
        // =====================================

        btnRegresarLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    contrasenaolvidada.this,
                    loginActivity.class
            );

            startActivity(intent);
            finish();

        });

    }
    }