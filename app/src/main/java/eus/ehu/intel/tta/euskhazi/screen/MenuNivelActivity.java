package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import eus.ehu.intel.tta.euskhazi.R;

public class MenuNivelActivity extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nivel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void chooseLevel(View view){

        //int level = Integer.parseInt(view.getTag().toString());
        String level = view.getTag().toString();
        //Toast.makeText(this, "Aukeratutako maila" + level, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TipoExamenActivity.class);
        intent.putExtra("level",level);
        startActivity(intent);
    }

}
