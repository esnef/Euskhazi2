package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.Idatzizkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.ItemIdatzizkoa;

public class ScreenIdatzizkoa extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_idatzizkoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final int numeroExamen = intent.getExtras().getInt("numeroExamen");
        final String levelString = intent.getStringExtra("level");

        TextView textLogin = (TextView)findViewById(R.id.idatzizkoa_title_textView);
        textLogin.setText("Idatzizkoa " + (numeroExamen + 1) + " - " + levelString);

        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {
                if (levels == null || levels.getIdatzizkoas() == null) {
                    Toast.makeText(getApplicationContext(), R.string.load_exam_incorrectly, Toast.LENGTH_SHORT).show();
                    return;
                }

                Idatzizkoa idatzizkoa = levels.getIdatzizkoas().get(numeroExamen);

                TextView titleTextView = (TextView) findViewById(R.id.idatzizkoa_exercise_title_textView);
                titleTextView.setText(idatzizkoa.getTitle());

                TextView explanationTextView = (TextView) findViewById(R.id.idatzizkoa_exercise_explanation_textView);
                explanationTextView.setText(idatzizkoa.getExplanation());

                TextView conditionTextView = (TextView) findViewById(R.id.idatzizkoa_exercise_condition_textView);
                conditionTextView.setText(idatzizkoa.getConditions());

                ArrayList<String> lista = new ArrayList<String>();
                for (ItemIdatzizkoa item : idatzizkoa.getItems()) {
                    String itemIdatzizkoa = "\u2022" + item.getItem();
                    lista.add(itemIdatzizkoa);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_layout, R.id.exams_textView, lista);
                ListView lstOpciones = (ListView) findViewById(R.id.idatzizkoa_listView);
                lstOpciones.setAdapter(adapter);

                mEngine.setOnGetLevelListener(null);
            }
        });
        mEngine.getLevel(levelString);

        Button sendButton = (Button)findViewById(R.id.idatzizkoa_correct_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idatzizkoa_editText = (EditText)findViewById(R.id.idatzizkoa_exercise_editText);
                final String emaitza = idatzizkoa_editText.getText().toString();

                mEngine.setOnGetUserNowListener(new UsersManager.OnGetUserNowListener() {
                    @Override
                    public void onGetUserNow(User user) {
                        if (user == null){
                            Toast.makeText(getApplicationContext(), R.string.usuario_incorrecto, Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            Toast.makeText(getApplicationContext(), "ERABILTZAILEA ONDO DAGO", Toast.LENGTH_SHORT).show();
                        }

                        Exam exam = new Exam();
                        exam.setDrafting(emaitza);
                        exam.setLevel(levelString);
                        exam.setNumExams(numeroExamen);
                        exam.setTypeExam("idatzizkoa");

                        List<Exam> examList = user.getExams();
                        boolean nuevoExamen = true;
                        for (int n=0; n < examList.size(); n++){
                            Exam exam1 = examList.get(n);
                            if (exam1.getLevel().equals(exam.getLevel()) && exam1.getNumExams() == exam.getNumExams() && exam1.getTypeExam().equals(exam.getTypeExam())) {
                                nuevoExamen = false;
                                examList.set(n, exam);
                            }
                        }
                        if (nuevoExamen){
                            examList.add(exam);
                        }

                        user.setExams(examList);

                        mEngine.setOnSaveUserListener(new UsersManager.OnSaveUserListener() {
                            @Override
                            public void onSaveUser(boolean isSaveUser) {
                                if (isSaveUser) {
                                    System.out.println("Se ha guardado correctamente el usuario");
                                } else {
                                    System.out.println("ERRORRRR!!!!");
                                }
                                mEngine.setOnSaveUserListener(null);
                                mEngine.setOnGetUserNowListener(null);
                            }
                        });
                        mEngine.saveUser();
                    }
                });
                mEngine.getUserNow();
            }
        });
    }

}
