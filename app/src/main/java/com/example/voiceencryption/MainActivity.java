package com.example.voiceencryption;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    public static final File NAME_HELLO = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/hello-5.mp3");
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

        textToSpeech = new TextToSpeech(getApplicationContext(), this);

        requestPermissions();

    }

    private void requestPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlayAudio:
                Log.i("SuccesfullEncrypt","startSound();");
                startSound();
                break;
            case R.id.btnStopAudio:
                finishSong(player);
                break;
            case R.id.btnPlayAudio2:
                textToSpeech.speak(encrypt.substring(2, 30), TextToSpeech.QUEUE_FLUSH, null);
                break;
            default:
                // throw new IllegalStateException("Unexpected value: " + idView);
        }
    }

    private void startSound() {
        try (
                AssetFileDescriptor assetFileDescriptor = getResources().getAssets().openFd("mirage.mp3");
                FileOutputStream os = new FileOutputStream(NAME_HELLO, false);
        ) {
            file = Utity.createFileFromInputStream(assetFileDescriptor.createInputStream(), NAME_HELLO);

            byte[] bytes = FileUtils.readFileToByteArray(file);

            String encoded = Base64.encodeToString(bytes, 0);

            CipherEncrypt(encoded, 5);
            byte[] decoded = Base64.decode(encoded, 0);
            os.write(decoded);

            if (assetFileDescriptor != null) {
                player.setDataSource(file.toString());
                player.prepare();
                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        finishSong(mediaPlayer);
                    }
                });
            }
            Log.i("SuccesfullEncrypt4", String.valueOf(NAME_HELLO));


        } catch (IOException e) {
            Log.e("ExceptionError", IOException.class.getName() + " " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("ExceptionError", Exception.class.getName() + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void finishSong(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        player = new MediaPlayer();
    }

    public void CipherEncrypt(String text, int s) {
        StringBuilder result = new StringBuilder();

        for (int counter = 0; counter < text.length(); counter++) {

            result.append(Character.isUpperCase(text.charAt(counter)) ? (char) (((int) text.charAt(counter) + s - 65) % 26 + 65) : (char) (((int) text.charAt(counter) + s - 97) % 26 + 97));
        }
        encrypt = Utity.getOnlyString(result.toString());
        try (FileOutputStream os = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/jirage.mp3", true);) {
            byte[] decoded = Base64.decode(result.toString(), 0);
            os.write(decoded);
        } catch (Exception e) {
            Log.e("ExceptionError", Exception.class.getName() + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int i) {
        if (i != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(Locale.UK);
        }
    }
}