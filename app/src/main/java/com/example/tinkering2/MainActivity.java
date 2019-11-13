package com.example.tinkering2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    long time_millis = 0;
    int film_factor = 1;
    int cont = 13;
    int material_thick = 0;
    int material_attenuation = 1;
    int sfd = 0;
    int curies = 1;
    CountDownTimer timer = null;
    TextView main_time = null;
    ToneGenerator tonegen = null;


    public void time_calc() {
        time_millis = (cont * film_factor * sfd * sfd * (long) Math.pow(2.718, (material_thick / material_attenuation))) / curies;
    }

    public String stringter(long millis) {
        String sec_s;
        String min_s;
        String hr_s;
        millis = millis / 1000;
        int hr = (int) (millis / (long) 3600);
        int min = (int) ((millis / 60) - hr * 60);
        int sec = (int) millis % 60;
        hr_s = String.valueOf(hr);
        if (min < 10) min_s = "0" + String.valueOf(min);
        else min_s = String.valueOf(min);
        if (sec < 10) sec_s = "0" + String.valueOf(sec);
        else sec_s = String.valueOf(sec);
        if (hr > 0) return hr_s + ":" + min_s + ":" + sec_s;
        else return min_s + ":" + sec_s;
    }

    public void setTimer() {
        timer = new CountDownTimer(time_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                main_time.setText(stringter(millisUntilFinished));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public void setTonegen (int some) {
        long dur;
        long inter;
        final long dur_t;
        if (some == 0) {
            dur = 10000;
            inter = 500;
            dur_t = 300;
        }
        else {
            dur = 30000;
            inter = 2000;
            dur_t = 1000;
        }

        CountDownTimer temp = new CountDownTimer(dur,inter) {
            @Override
            public void onTick(long millisUntilFinished) {
                tonegen.startTone(ToneGenerator.TONE_DTMF_0);
                CountDownTimer temp2 = new CountDownTimer(dur_t,dur_t) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        tonegen.stopTone();
                    }
                }.start();
            }

            @Override
            public void onFinish() {
                tonegen.release();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner film = findViewById(R.id.spinner);
        String[] items = {"D4", "D5", "D7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_spinner, items);
        film.setAdapter(adapter);
        String[] meas = {"inch", "mm"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.my_spinner, meas);
        Spinner sfd_spinner = findViewById(R.id.spinner2);
        Spinner mt_spinner = findViewById(R.id.spinner3);
        sfd_spinner.setAdapter(adapter2);
        mt_spinner.setAdapter(adapter2);
        String[] sources = {"Ir-192", "Se-75", "Co-60", "Yb-169"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.my_spinner, sources);
        Spinner s_source = findViewById(R.id.spinner4);
        s_source.setAdapter(adapter3);
        String[] materials = {"Steel", "Inconel", "Ti", "Al"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.my_spinner, materials);
        Spinner s_material = findViewById(R.id.spinner5);
        s_material.setAdapter(adapter4);
        main_time = findViewById(R.id.textView4);
        tonegen = new ToneGenerator(10, 100);
    }
}
