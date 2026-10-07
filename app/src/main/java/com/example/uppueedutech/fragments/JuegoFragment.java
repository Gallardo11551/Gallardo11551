package com.example.uppueedutech.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uppueedutech.R;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class JuegoFragment extends Fragment {

    private FrameLayout areaJuego;
    private TextView txtTiempo, txtPuntos, txtSubtitulo;
    private MaterialButton btnIniciar;
    private MediaPlayer sonido;

    // Palabras CORRECTAS (relacionadas con educación, UPPUE, modelo educativo)
    private final String[] correctas = {
            "Innovación", "Trabajo en equipo", "Aprender", "Creatividad",
            "Tecnología", "Investigación", "Comunicación", "Problemas",
            "Competencias", "Valores", "Ética", "Responsabilidad",
            "Inclusión", "Respeto", "Colaboración", "Liderazgo",
            "Conocimiento", "Habilidades", "Actitudes", "Excelencia",
            "Calidad", "Compromiso", "Disciplina", "Perseverancia",
            "Pensamiento crítico", "Solución", "Proyectos", "Digital",
            "Transformación", "Sostenibilidad", "Humanista", "Integral"
    };

    // Palabras FALSAS (distractores)
    private final String[] falsas = {
            "Netflix", "TikTok", "Pizza", "Minecraft",
            "Xbox", "Spotify", "Instagram", "YouTube",
            "Fortnite", "WhatsApp", "Facebook", "Twitter",
            "Tinder", "Snapchat", "Telegram", "Discord",
            "Roblox", "Among Us", "Candy Crush", "Clash Royale"
    };

    private final Random random = new Random();
    private final Handler handler = new Handler();

    private int puntos = 0;
    private int palabrasCorrectas = 0;
    private int palabrasIncorrectas = 0;
    private CountDownTimer timer;
    private boolean jugando = false;
    private int palabrasGeneradas = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_juego, container, false);

        areaJuego = view.findViewById(R.id.areaJuego);
        txtTiempo = view.findViewById(R.id.txtTiempo);
        txtPuntos = view.findViewById(R.id.txtPuntos);
        txtSubtitulo = view.findViewById(R.id.txtSubtitulo);
        btnIniciar = view.findViewById(R.id.btnIniciarJuego);

        // Cambiar subtítulo aleatoriamente
        cambiarSubtitulo();

        sonido = MediaPlayer.create(requireContext(), R.raw.juego);
        sonido.setLooping(true);

        btnIniciar.setOnClickListener(v -> iniciarJuego());

        return view;
    }

    private void cambiarSubtitulo() {
        String[] subtitulos = {
                "Atrapa las palabras relacionadas con el Modelo Educativo",
                "Atrapa las palabras relacionadas con la UPPUE",
                "Atrapa las palabras relacionadas con Competencias",
                "Atrapa las palabras relacionadas con Innovación",
                "Atrapa las palabras relacionadas con Valores",
                "Atrapa las palabras relacionadas con Educación"
        };
        txtSubtitulo.setText(subtitulos[random.nextInt(subtitulos.length)]);
    }

    private void iniciarJuego() {

        if (sonido != null) {
            if (!sonido.isPlaying()) {
                sonido.start();
            }
        }

        jugando = true;
        puntos = 0;
        palabrasCorrectas = 0;
        palabrasIncorrectas = 0;
        palabrasGeneradas = 0;
        txtPuntos.setText("⭐ " + puntos);
        areaJuego.removeAllViews();
        btnIniciar.setEnabled(false);
        cambiarSubtitulo();

        if (timer != null) timer.cancel();

        timer = new CountDownTimer(30000, 800) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTiempo.setText("⏱ " + (millisUntilFinished / 1000) + " s");
                crearPalabra();
            }

            @Override
            public void onFinish() {
                jugando = false;
                btnIniciar.setEnabled(true);
                handler.removeCallbacksAndMessages(null);
                mostrarResultado();
            }
        }.start();
    }

    private void crearPalabra() {

        if (!jugando) return;

        palabrasGeneradas++;
        final TextView tv = new TextView(requireContext());

        // 60% de probabilidad de que sea correcta, 40% falsa
        boolean buena = random.nextFloat() < 0.6;

        String texto = buena ?
                correctas[random.nextInt(correctas.length)] :
                falsas[random.nextInt(falsas.length)];

        tv.setText(texto);
        tv.setTextSize(18 + random.nextInt(8)); // Tamaño aleatorio entre 18-26
        tv.setTextColor(Color.WHITE);
        tv.setShadowLayer(8, 2, 2, Color.BLACK);
        tv.setPadding(24, 14, 24, 14);

        // Colores de fondo diferentes para palabras correctas e incorrectas
        if (buena) {
            tv.setBackgroundColor(Color.parseColor("#CC4CAF50")); // Verde
        } else {
            tv.setBackgroundColor(Color.parseColor("#CCF44336")); // Rojo
        }

        int ancho = areaJuego.getWidth();
        if (ancho == 0) ancho = 900;

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        lp.gravity = Gravity.TOP | Gravity.START;
        lp.leftMargin = random.nextInt(Math.max(1, ancho - 250));
        lp.topMargin = random.nextInt(100);
        tv.setLayoutParams(lp);

        areaJuego.addView(tv);

        tv.setOnClickListener(v -> {
            if (!jugando) return;

            if (buena) {
                puntos += 15;
                palabrasCorrectas++;
                tv.setBackgroundColor(Color.parseColor("#66FFD700"));
            } else {
                puntos -= 5;
                palabrasIncorrectas++;
                tv.setBackgroundColor(Color.parseColor("#66FF0000"));
            }

            txtPuntos.setText("⭐ " + puntos);
            areaJuego.removeView(tv);
        });

        tv.setTranslationY(-80);

        tv.animate()
                .translationY(areaJuego.getHeight() + 200)
                .setDuration(4000 + random.nextInt(1500))
                .withEndAction(() -> {
                    if (tv.getParent() != null) {
                        areaJuego.removeView(tv);
                    }
                })
                .start();
    }

    private void mostrarResultado() {

        if (sonido != null && sonido.isPlaying()) {
            sonido.pause();
            sonido.seekTo(0);
        }

        // Calcular porcentaje de aciertos
        double porcentaje = 0;
        if (palabrasCorrectas + palabrasIncorrectas > 0) {
            porcentaje = (double) palabrasCorrectas / (palabrasCorrectas + palabrasIncorrectas) * 100;
        }

        String estrellas;
        String mensajeExtra = "";

        if (puntos >= 120) {
            estrellas = "⭐⭐⭐⭐⭐";
            mensajeExtra = "¡Excelente! Eres un experto en el Modelo Educativo 🏆";
        } else if (puntos >= 90) {
            estrellas = "⭐⭐⭐⭐";
            mensajeExtra = "¡Muy bien! Conoces mucho sobre la UPPUE 👏";
        } else if (puntos >= 60) {
            estrellas = "⭐⭐⭐";
            mensajeExtra = "¡Buen trabajo! Sigue aprendiendo sobre el modelo educativo 💪";
        } else if (puntos >= 30) {
            estrellas = "⭐⭐";
            mensajeExtra = "¡Sigue practicando! Revisa la sección del Modelo Educativo 📚";
        } else {
            estrellas = "⭐";
            mensajeExtra = "¡No te rindas! Explora las tarjetas del Modelo Educativo 🎯";
        }

        String mensaje = "Puntuación: " + puntos + " puntos\n" +
                "Palabras correctas: " + palabrasCorrectas + "\n" +
                "Palabras incorrectas: " + palabrasIncorrectas + "\n" +
                "Aciertos: " + String.format("%.0f", porcentaje) + "%\n\n" +
                estrellas + "\n\n" +
                mensajeExtra;

        new AlertDialog.Builder(requireContext())
                .setTitle("🏆 Juego terminado")
                .setMessage(mensaje)
                .setPositiveButton("Jugar otra vez", (d, w) -> iniciarJuego())
                .setNegativeButton("Cerrar", null)
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sonido != null && sonido.isPlaying()) {
            sonido.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (jugando && sonido != null && !sonido.isPlaying()) {
            sonido.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sonido != null) {
            sonido.release();
            sonido = null;
        }
    }
}