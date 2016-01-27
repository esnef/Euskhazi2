package eus.ehu.intel.tta.euskhazi.screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.AtarikoaStatements;

public class ScreenAtarikoa extends ScreenBase{

    private int numeroExamen;
    private String levelString;
    private Atarikoa atarikoa;
    private RadioGroup atarikoa_RadioGroup_solutions;
    private TextView atarikoa_TextView_statement;
    private RadioButton atarikoa_RadioButton_solutions_1;
    private RadioButton atarikoa_RadioButton_solutions_2;
    private RadioButton atarikoa_RadioButton_solutions_3;
    private RadioButton atarikoa_RadioButton_solutions_4;
    private Button atarikoa_Button_previous;
    private Button atarikoa_Button_next;
    private int statementNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_atarikoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        numeroExamen = intent.getExtras().getInt("numeroExamen");
        levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView)findViewById(R.id.atarikoa_title_textView);
        textLogin.setText("Atarikoa " + (numeroExamen+1) + " - " + levelString);

        atarikoa_TextView_statement = (TextView)findViewById(R.id.atarikoa_TextView_statement);
        atarikoa_RadioGroup_solutions = (RadioGroup)findViewById(R.id.atarikoa_RadioGroup_solutions);
        atarikoa_RadioButton_solutions_1 = (RadioButton)findViewById(R.id.atarikoa_RadioButton_solutions_1);
        atarikoa_RadioButton_solutions_2 = (RadioButton)findViewById(R.id.atarikoa_RadioButton_solutions_2);
        atarikoa_RadioButton_solutions_3 = (RadioButton)findViewById(R.id.atarikoa_RadioButton_solutions_3);
        atarikoa_RadioButton_solutions_4 = (RadioButton)findViewById(R.id.atarikoa_RadioButton_solutions_4);
        atarikoa_Button_previous = (Button)findViewById(R.id.atarikoa_Button_previous);
        atarikoa_Button_next = (Button)findViewById(R.id.atarikoa_Button_next);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] data=new int[4];
                data[0]=atarikoa_RadioButton_solutions_1.getId();
                data[1]=atarikoa_RadioButton_solutions_2.getId();
                data[2]=atarikoa_RadioButton_solutions_3.getId();
                data[3]=atarikoa_RadioButton_solutions_4.getId();

                if(data[0]==v.getId()){
                    atarikoa.getStatements().get(statementNext).setIndex(0);
                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechFirst(true);
                }else if(data[1]==v.getId()){
                    atarikoa.getStatements().get(statementNext).setIndex(1);

                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechSecond(true);

                }else if(data[2]==v.getId()){
                    atarikoa.getStatements().get(statementNext).setIndex(2);

                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechThird(true);
                }else if(data[3]==v.getId()){
                    atarikoa.getStatements().get(statementNext).setIndex(3);
                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechFourth(true);

                }
            }
        };
        atarikoa_RadioButton_solutions_1.setOnClickListener(onClickListener);
        atarikoa_RadioButton_solutions_2.setOnClickListener(onClickListener);
        atarikoa_RadioButton_solutions_3.setOnClickListener(onClickListener);
        atarikoa_RadioButton_solutions_4.setOnClickListener(onClickListener);



        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {


            @Override
            public void onGetLevel(Level levels) {
                if (levels != null) {
                    atarikoa = levels.getAtarikoas().get(numeroExamen);
                    statementNext = 0;
                    loadStatement(0);

                }
            }
        });
        mEngine.getLevel(levelString);
        atarikoa_Button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statementNext != atarikoa.getStatements().size()-1) {
                    statementNext++;
                    loadStatement(statementNext);
                    if(statementNext == atarikoa.getStatements().size()-1){
                        atarikoa_Button_next.setText(getString(R.string.corregir));
                    }
                }else{
                    //Correguir
                    int okquestions=0;
                    for(int con=0;con<atarikoa.getStatements().size();con++) {

                        if (atarikoa.getStatements().get(con).getIndex()==new Integer(atarikoa.getStatements().get(con).getSolution())) {
                            okquestions++;
                        }

                    }

                    double result=((double)okquestions/(double)atarikoa.getStatements().size())*10;
                    DecimalFormat df = new DecimalFormat("##.##");
                    df.setRoundingMode(RoundingMode.DOWN);
                    String string=df.format(result);
                    Toast.makeText(getApplicationContext(),"Azken puntuazioa "+string+" da",Toast.LENGTH_SHORT).show();
                    if(result>=5){
                        //aprobado
                        Exam exam=new Exam();
                        exam.setNumExams(numeroExamen);
                        exam.setTypeExam("atarikoa");
                        exam.setLevel(levelString);
                        exam.setResult(result);
                        saveUserExam(exam);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),getString(R.string.No_se_ha_aprobado_la_prueba),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        atarikoa_Button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statementNext != 0) {
                    statementNext--;
                    loadStatement(statementNext);
                    atarikoa_Button_next.setText(getString(R.string.next));

                }
            }
        });

    }

    public void loadStatement(int index){
        atarikoa_RadioGroup_solutions.clearCheck();
        atarikoa_TextView_statement.setText((index + 1) + "-." + atarikoa.getStatements().get(index).getStatement());
        atarikoa_RadioButton_solutions_1.setText("1-."+atarikoa.getStatements().get(index).getAnswers().get(0).getFirst());
        atarikoa_RadioButton_solutions_2.setText("2-."+atarikoa.getStatements().get(index).getAnswers().get(0).getSecond());
        atarikoa_RadioButton_solutions_3.setText("3-."+atarikoa.getStatements().get(index).getAnswers().get(0).getThird());
        atarikoa_RadioButton_solutions_4.setText("4-."+atarikoa.getStatements().get(index).getAnswers().get(0).getFourth());
        atarikoa_RadioButton_solutions_1.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechFirst());
        atarikoa_RadioButton_solutions_2.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechSecond());
        atarikoa_RadioButton_solutions_3.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechThird());
        atarikoa_RadioButton_solutions_4.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechFourth());
    }





}
