package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import eus.ehu.intel.tta.euskhazi.R;

public class ScreenAhozkoa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_ahozkoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int numeroExamen = intent.getExtras().getInt("numeroExamen");
        String levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView) findViewById(R.id.ahozkoa_title_textView);
        textLogin.setText("Ahozkoa " + (numeroExamen + 1) + " - " + levelString);
    }
}
