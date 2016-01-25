package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.Sinonimoak;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.SinonimoakAdapter;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.StatementSinonimoak;

public class ScreenSinonimoak extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_sinonimoak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final int numeroExamen = intent.getExtras().getInt("numeroExamen");
        String levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView)findViewById(R.id.sinonimoak_title_textView);
        textLogin.setText("Sinonimoak " + (numeroExamen + 1) + " - " + levelString);

        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {

                if (levels == null || levels.getSinonimoaks() == null || levels.getSinonimoaks().get(numeroExamen).getStatements() == null) {
                    Toast.makeText(getApplicationContext(), R.string.load_exam_incorrectly, Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<StatementSinonimoak> statements = levels.getSinonimoaks().get(numeroExamen).getStatements();

                SinonimoakAdapter sinonimoakAdapter = new SinonimoakAdapter(getApplicationContext(), statements);
                ListView lstOpciones = (ListView) findViewById(R.id.sinonimoak_listView);
                lstOpciones.setAdapter(sinonimoakAdapter);

                mEngine.setOnGetLevelListener(null);
            }
        });
        mEngine.getLevel(levelString);
    }

}
