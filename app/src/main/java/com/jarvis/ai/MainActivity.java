package com.jarvis.ai;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.RecognizerIntent;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
                speak("Jarvis online, señor. ¿Qué desea?");
            }
        });

        listen();
    }

    private void listen() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        startActivityForResult(intent, 100);
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == 100 && res == RESULT_OK) {

            String text = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS).get(0);

            if (text.toLowerCase().contains("hola")) {
                speak("Hola señor, ¿qué necesita?");
            } else if (text.toLowerCase().contains("hora")) {
                speak("La hora es: " + java.time.LocalTime.now().toString());
            } else {
                speak("No entendí eso, señor.");
            }
        }
        listen();
    }
}
