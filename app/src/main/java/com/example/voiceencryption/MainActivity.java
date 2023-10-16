package com.example.voiceencryption;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlayAudio, btnStopAudio, btnPlayAudio2;
    private MediaPlayer player;
    private File file;
    private static final int REQUEST_WRITE_PERMISSION = 1;
    private TextToSpeech textToSpeech;
    private String encrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

    }

    private void setUI() {
        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        btnStopAudio = findViewById(R.id.btnStopAudio);
        btnPlayAudio2 = findViewById(R.id.btnPlayAudio2);

        encrypt = null;

        player = new MediaPlayer();

        btnPlayAudio.setOnClickListener(this);
        btnStopAudio.setOnClickListener(this);
        btnPlayAudio2.setOnClickListener(this);

        requestPermissions();

    }

    private void requestPermissions() {
        if(SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int idView = view.getId();
        switch (idView){
            case R.id.btnPlayAudio: break;
            case R.id.btnStopAudio: break;
            case R.id.btnPlayAudio2: break;
            default:
                throw new IllegalStateException("Unexpected value: " + idView);
        }
    }
}