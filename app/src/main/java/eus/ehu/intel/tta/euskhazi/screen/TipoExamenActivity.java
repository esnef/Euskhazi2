package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import eus.ehu.intel.tta.euskhazi.R;

public class TipoExamenActivity extends ScreenBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_examen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        TextView textLogin = (TextView)findViewById(R.id.tipo_examen_textView_level);
        textLogin.setText(intent.getStringExtra("level") + " MAILA");

    }

    public void chooseExamType(View view){

        int examType = Integer.parseInt(view.getTag().toString());
        String level = getIntent().getStringExtra("level");
        Intent intent = new Intent(this, ScreenListaExams.class);
        intent.putExtra("level", level);
        intent.putExtra("typeLevel", examType);
        startActivity(intent);

    }

}
