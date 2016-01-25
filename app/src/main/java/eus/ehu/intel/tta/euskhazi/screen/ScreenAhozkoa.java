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
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;

public class ScreenAhozkoa extends ScreenBase {
    private TextView ahozkoa_title_textView;
    private int numeroExamen;
    private TextView ahozkoa_exam_textView;
    private TextView ahozkoa_explanation_textView;
    private TextView ahozkoa_questions_textView;
    private TextView ahozkoa_conditions_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_ahozkoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        numeroExamen = intent.getExtras().getInt("numeroExamen");
        String levelString = intent.getStringExtra("level");

        ahozkoa_title_textView = (TextView) findViewById(R.id.ahozkoa_title_textView);
        ahozkoa_title_textView.setText("Ahozkoa " + (numeroExamen + 1) + " - " + levelString);

        ahozkoa_exam_textView = (TextView) findViewById(R.id.ahozkoa_exam_textView);
        ahozkoa_explanation_textView = (TextView) findViewById(R.id.ahozkoa_explanation_textView);
        ahozkoa_questions_textView  = (TextView) findViewById(R.id.ahozkoa_questions_textView);
        ahozkoa_conditions_textView = (TextView) findViewById(R.id.ahozkoa_conditions_textView);



        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {
                if (levels != null) {
                    Ahozkoa ahozkoa = levels.getAhozkoas().get(numeroExamen);
                    ahozkoa_exam_textView.setText(ahozkoa.getTitle());
                    ahozkoa_explanation_textView.setText(ahozkoa.getExplanation());
                    if(ahozkoa.getQuestions()!=null){
                        for(int con=0;con<ahozkoa.getQuestions().size();con++){
                            ahozkoa_questions_textView.setText(ahozkoa_questions_textView.getText()+"-"+ahozkoa.getQuestions().get(con).getQuestion()+"\n");

                        }
                        ahozkoa_conditions_textView.setText(ahozkoa.getConditions());

                    }

                }
            }
        });
        mEngine.getLevel(levelString);
    }
}
