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

public class TipoExamenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_examen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void chooseExamType(View view){

        int examType = Integer.parseInt(view.getTag().toString());
        switch (examType){
            case 1:
                Intent intent = new Intent(this, AtarikoaActivity.class);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(this, "Idatzizkoa sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Sinonimoak sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Berridazketak sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Entzumena sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(this, "Ahozkoa sin implementar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
