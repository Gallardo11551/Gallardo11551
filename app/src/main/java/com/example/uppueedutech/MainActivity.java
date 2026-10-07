package com.example.uppueedutech;

import android.os.Bundle;
import android.view.Menu;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.fragments.AdministrarUsuariosFragment;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.material.navigation.NavigationView;
import com.example.uppueedutech.fragments.RegistrarProfesorFragment;
import com.example.uppueedutech.fragments.AcercaFragment;
import com.example.uppueedutech.fragments.CaracteristicasFragment;
import com.example.uppueedutech.fragments.CompetenciasFragment;
import com.example.uppueedutech.fragments.EvaluacionFragment;
import com.example.uppueedutech.fragments.HistorietaFragment;
import com.example.uppueedutech.fragments.HomeFragment;
import com.example.uppueedutech.fragments.JuegoFragment;
import com.example.uppueedutech.fragments.ModeloFragment;
import com.example.uppueedutech.fragments.TutoresFragment;
import com.example.uppueedutech.fragments.CarreraVideoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        // Inicializar Firebase -----------------------------------------
        mAuth = FirebaseAuth.getInstance();
        usuarioActual = mAuth.getCurrentUser();

// Ocultar opciones de administrador por defecto
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_registrar_profesor).setVisible(false);
        menu.findItem(R.id.nav_administrar_usuarios).setVisible(false);

// Si hay un usuario autenticado, consultar su rol
        if (usuarioActual != null) {

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("usuarios")
                    .child(usuarioActual.getUid());

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        String rol = snapshot.child("rol").getValue(String.class);

                        if ("administrador".equals(rol)) {

                            menu.findItem(R.id.nav_registrar_profesor).setVisible(true);
                            menu.findItem(R.id.nav_administrar_usuarios).setVisible(true);
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }

            });

        }
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            cambiarFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_inicio);
        }

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id == R.id.nav_inicio){
                getSupportActionBar().setTitle("Inicio");
                cambiarFragment(new HomeFragment());
            }else if(id == R.id.nav_registrar_profesor){
                getSupportActionBar().setTitle("Registrar Profesor");
                cambiarFragment(new RegistrarProfesorFragment());
            }else if(id == R.id.nav_administrar_usuarios){
                getSupportActionBar().setTitle("Administrar Usuarios");
                cambiarFragment(new AdministrarUsuariosFragment());
            }else if(id == R.id.nav_modelo){
                getSupportActionBar().setTitle("Modelo Educativo");
                cambiarFragment(new ModeloFragment());
            }else if(id == R.id.nav_competencias){
                getSupportActionBar().setTitle("Competencias");
                cambiarFragment(new CompetenciasFragment());
            }else if(id == R.id.nav_caracteristicas){
                getSupportActionBar().setTitle("Proyectos TI");
                cambiarFragment(new CaracteristicasFragment());
            }else if(id == R.id.nav_historieta){
                getSupportActionBar().setTitle("Historieta");
                cambiarFragment(new HistorietaFragment());
            }else if(id == R.id.nav_juego){
                getSupportActionBar().setTitle("Juego");
                cambiarFragment(new JuegoFragment());
            }else if(id == R.id.nav_carrera_video){
                getSupportActionBar().setTitle("Más de la Carrera");
                cambiarFragment(new CarreraVideoFragment());
            }else if(id == R.id.nav_tutores){
                getSupportActionBar().setTitle("Tutores");
                cambiarFragment(new TutoresFragment());
            }else if(id == R.id.nav_evaluacion){
                getSupportActionBar().setTitle("Evaluación");
                cambiarFragment(new EvaluacionFragment());
            }else if(id == R.id.nav_acerca){
                getSupportActionBar().setTitle("Acerca de");
                cambiarFragment(new AcercaFragment());
            }

            drawerLayout.closeDrawers();
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    } else {
                        setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }
            });

            return true;

        });
    }

    public void cambiarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}