package com.example.uppueedutech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    EditText usuario, contrasenna;

    Button btnsig;
    Button btnOlvidePassword;
    Button btnNuevoUsuario;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Vincular elementos del XML
        usuario = findViewById(R.id.edtCorreo);
        contrasenna = findViewById(R.id.edtPassword);

        btnsig = findViewById(R.id.btnLogin);
        btnNuevoUsuario = findViewById(R.id.btnRegistro);
        btnOlvidePassword = findViewById(R.id.txtOlvidePassword);

        btnsig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = usuario.getText().toString().trim();
                String pass = contrasenna.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {

                    Toast.makeText(
                            loginActivity.this,
                            "Introduce ambos campos ya que no pueden estar vacíos",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(
                                            loginActivity.this,
                                            "Usuario iniciado correctamente",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    Intent intent = new Intent(
                                            loginActivity.this,
                                            MainActivity.class
                                    );

                                    startActivity(intent);

                                    finish();

                                } else {

                                    Toast.makeText(
                                            loginActivity.this,
                                            "No se logró iniciar sesión",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        });
            }
        });
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        loginActivity.this,
                        nuevousuarioActivity.class
                );

                startActivity(intent);
            }
        });
        btnOlvidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        loginActivity.this,
                        contrasenaolvidada.class
                );

                startActivity(intent);
            }
        });

    }
}