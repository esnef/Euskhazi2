package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.StatementEntzunezkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.StatementSinonimoak;

public class ScreenEntzumena extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_entzumena);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final int numeroExamen = intent.getExtras().getInt("numeroExamen");
        String levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView)findViewById(R.id.entzumena_title_textView);
        textLogin.setText("Entzumena " + (numeroExamen + 1) + " - " + levelString);

        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {
                if (levels == null || levels.getEntzunezkoas() == null) {
                    Toast.makeText(getApplicationContext(), R.string.load_exam_incorrectly, Toast.LENGTH_SHORT).show();
                    return;
                }
                String audioUrl = levels.getEntzunezkoas().get(numeroExamen).getAudioUrl();
                ArrayList<StatementEntzunezkoa> statements = levels.getEntzunezkoas().get(numeroExamen).getStatements();

                TextView statementTextView0 = (TextView) findViewById(R.id.entzumena_statement_textView_0);
                statementTextView0.setText(statements.get(0).getStatement());
                TextView statementTextView1 = (TextView) findViewById(R.id.entzumena_statement_textView_1);
                statementTextView1.setText(statements.get(1).getStatement());
                TextView statementTextView2 = (TextView) findViewById(R.id.entzumena_statement_textView_2);
                statementTextView2.setText(statements.get(2).getStatement());
                TextView statementTextView3 = (TextView) findViewById(R.id.entzumena_statement_textView_3);
                statementTextView3.setText(statements.get(3).getStatement());
                TextView statementTextView4 = (TextView) findViewById(R.id.entzumena_statement_textView_4);
                statementTextView4.setText(statements.get(4).getStatement());

                ArrayList<String> posibleAnswers0 = getPosibleAnswers(statements, 0);
                RadioGroup radioGroup0 = (RadioGroup) findViewById(R.id.entzumena_radioGroup_0);
                populateRadioGroup(radioGroup0, posibleAnswers0);
                ArrayList<String> posibleAnswers1 = getPosibleAnswers(statements, 1);
                RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.entzumena_radioGroup_1);
                populateRadioGroup(radioGroup1, posibleAnswers1);
                ArrayList<String> posibleAnswers2 = getPosibleAnswers(statements, 2);
                RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.entzumena_radioGroup_2);
                populateRadioGroup(radioGroup2, posibleAnswers2);
                ArrayList<String> posibleAnswers3 = getPosibleAnswers(statements, 3);
                RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.entzumena_radioGroup_3);
                populateRadioGroup(radioGroup3, posibleAnswers3);
                ArrayList<String> posibleAnswers4 = getPosibleAnswers(statements, 4);
                RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.entzumena_radioGroup_4);
                populateRadioGroup(radioGroup4, posibleAnswers4);
            }
        });
        mEngine.getLevel(levelString);
    }

    private ArrayList<String> getPosibleAnswers(ArrayList<StatementEntzunezkoa> statements, int numeroPregunta){
        ArrayList<String> posibleAnswers = new ArrayList<>();
        posibleAnswers.add(statements.get(numeroPregunta).getAnswers().get(0).getFirst());
        posibleAnswers.add(statements.get(numeroPregunta).getAnswers().get(0).getSecond());
        posibleAnswers.add(statements.get(numeroPregunta).getAnswers().get(0).getThird());

        return posibleAnswers;
    }

    private void populateRadioGroup(RadioGroup radioGroup, ArrayList<String> posibleAnswers){
        for (String posibleAnswer : posibleAnswers) {
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(posibleAnswer);
            //radioButton.setOnClickListener(this);
            radioGroup.addView(radioButton);

            System.out.println(posibleAnswer);
        }
    }
}
