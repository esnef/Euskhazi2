package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
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
        final String levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView)findViewById(R.id.sinonimoak_title_textView);
        textLogin.setText("Sinonimoak " + (numeroExamen + 1) + " - " + levelString);

        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {

                if (levels == null || levels.getSinonimoaks() == null || levels.getSinonimoaks().get(numeroExamen).getStatements() == null) {
                    Toast.makeText(getApplicationContext(), R.string.load_exam_incorrectly, Toast.LENGTH_SHORT).show();
                    return;
                }
                final ArrayList<StatementSinonimoak> statements = levels.getSinonimoaks().get(numeroExamen).getStatements();

                TextView statementTextView0 = (TextView) findViewById(R.id.sinonimoak_layout_textView_0);
                statementTextView0.setText(statements.get(0).getStatement() + " (" + statements.get(0).getPlaceholder() + ")");
                TextView statementTextView1 = (TextView) findViewById(R.id.sinonimoak_layout_textView_1);
                statementTextView1.setText(statements.get(1).getStatement() + " (" + statements.get(1).getPlaceholder() + ")");
                TextView statementTextView2 = (TextView) findViewById(R.id.sinonimoak_layout_textView_2);
                statementTextView2.setText(statements.get(2).getStatement() + " (" + statements.get(2).getPlaceholder() + ")");
                TextView statementTextView3 = (TextView) findViewById(R.id.sinonimoak_layout_textView_3);
                statementTextView3.setText(statements.get(3).getStatement() + " (" + statements.get(3).getPlaceholder() + ")");
                TextView statementTextView4 = (TextView) findViewById(R.id.sinonimoak_layout_textView_4);
                statementTextView4.setText(statements.get(4).getStatement() + " (" + statements.get(4).getPlaceholder() + ")");
                TextView statementTextView5 = (TextView) findViewById(R.id.sinonimoak_layout_textView_5);
                statementTextView5.setText(statements.get(5).getStatement() + " (" + statements.get(5).getPlaceholder() + ")");
                TextView statementTextView6 = (TextView) findViewById(R.id.sinonimoak_layout_textView_6);
                statementTextView6.setText(statements.get(6).getStatement() + " (" + statements.get(6).getPlaceholder() + ")");
                TextView statementTextView7 = (TextView) findViewById(R.id.sinonimoak_layout_textView_7);
                statementTextView7.setText(statements.get(7).getStatement() + " (" + statements.get(7).getPlaceholder() + ")");
                TextView statementTextView8 = (TextView) findViewById(R.id.sinonimoak_layout_textView_8);
                statementTextView8.setText(statements.get(8).getStatement() + " (" + statements.get(8).getPlaceholder() + ")");
                TextView statementTextView9 = (TextView) findViewById(R.id.sinonimoak_layout_textView_9);
                statementTextView9.setText(statements.get(9).getStatement() + " (" + statements.get(9).getPlaceholder() + ")");

                Button zuzenduButton = (Button) findViewById(R.id.sinonimoak_correct_button);
                zuzenduButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double zuzenak = 0;

                        EditText answerEditText0 = (EditText) findViewById(R.id.sinonimoak_layout_editText_0);
                        String answer0 = answerEditText0.getText().toString().trim().toLowerCase();
                        if (statements.get(0).getSolution().equals(answer0)) zuzenak++;
                        EditText answerEditText1 = (EditText) findViewById(R.id.sinonimoak_layout_editText_1);
                        String answer1 = answerEditText1.getText().toString().trim().toLowerCase();
                        if (statements.get(1).getSolution().equals(answer1)) zuzenak++;
                        EditText answerEditText2 = (EditText) findViewById(R.id.sinonimoak_layout_editText_2);
                        String answer2 = answerEditText2.getText().toString().trim().toLowerCase();
                        if (statements.get(2).getSolution().equals(answer2)) zuzenak++;
                        EditText answerEditText3 = (EditText) findViewById(R.id.sinonimoak_layout_editText_3);
                        String answer3 = answerEditText3.getText().toString().trim().toLowerCase();
                        if (statements.get(3).getSolution().equals(answer3)) zuzenak++;
                        EditText answerEditText4 = (EditText) findViewById(R.id.sinonimoak_layout_editText_4);
                        String answer4 = answerEditText4.getText().toString().trim().toLowerCase();
                        if (statements.get(4).getSolution().equals(answer4)) zuzenak++;
                        EditText answerEditText5 = (EditText) findViewById(R.id.sinonimoak_layout_editText_5);
                        String answer5 = answerEditText5.getText().toString().trim().toLowerCase();
                        if (statements.get(5).getSolution().equals(answer5)) zuzenak++;
                        EditText answerEditText6 = (EditText) findViewById(R.id.sinonimoak_layout_editText_6);
                        String answer6 = answerEditText6.getText().toString().trim().toLowerCase();
                        if (statements.get(6).getSolution().equals(answer6)) zuzenak++;
                        EditText answerEditText7 = (EditText) findViewById(R.id.sinonimoak_layout_editText_7);
                        String answer7 = answerEditText7.getText().toString().trim().toLowerCase();
                        if (statements.get(7).getSolution().equals(answer7)) zuzenak++;
                        EditText answerEditText8 = (EditText) findViewById(R.id.sinonimoak_layout_editText_8);
                        String answer8 = answerEditText8.getText().toString().trim().toLowerCase();
                        if (statements.get(8).getSolution().equals(answer8)) zuzenak++;
                        EditText answerEditText9 = (EditText) findViewById(R.id.sinonimoak_layout_editText_9);
                        String answer9 = answerEditText9.getText().toString().trim().toLowerCase();
                        if (statements.get(9).getSolution().equals(answer9)) zuzenak++;

                        if (zuzenak >= 6) {
                            Toast.makeText(getApplicationContext(), R.string.examen_aprobado, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.examen_suspendido, Toast.LENGTH_SHORT).show();
                        }
                        Exam exam = new Exam();
                        exam.setTypeExam("sinonimoak");
                        exam.setNumExams(numeroExamen);
                        exam.setLevel(levelString);
                        exam.setResult(zuzenak);

                        saveUserExam(exam);
                    }
                });

                mEngine.setOnGetLevelListener(null);
            }
        });
        mEngine.getLevel(levelString);
    }

}
