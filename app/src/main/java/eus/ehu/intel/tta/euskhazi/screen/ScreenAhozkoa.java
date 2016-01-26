package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.utils.UtilsServices;

public class ScreenAhozkoa extends ScreenBase {
    private TextView ahozkoa_title_textView;
    private int numeroExamen;
    private TextView ahozkoa_exam_textView;
    private TextView ahozkoa_explanation_textView;
    private TextView ahozkoa_questions_textView;
    private TextView ahozkoa_conditions_textView;
    private Button ahozkoa_record_voice_Button;

    private static final int AUDIO_REQUEST_CODE=147;
    private String levelString;
    private String titleRecordAudio=null;
    private Intent dataResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_ahozkoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        numeroExamen = intent.getExtras().getInt("numeroExamen");
        levelString = intent.getStringExtra("level");

        ahozkoa_title_textView = (TextView) findViewById(R.id.ahozkoa_title_textView);
        ahozkoa_title_textView.setText("Ahozkoa " + (numeroExamen + 1) + " - " + levelString);

        ahozkoa_exam_textView = (TextView) findViewById(R.id.ahozkoa_exam_textView);
        ahozkoa_explanation_textView = (TextView) findViewById(R.id.ahozkoa_explanation_textView);
        ahozkoa_questions_textView  = (TextView) findViewById(R.id.ahozkoa_questions_textView);
        ahozkoa_conditions_textView = (TextView) findViewById(R.id.ahozkoa_conditions_textView);
        ahozkoa_record_voice_Button = (Button) findViewById(R.id.ahozkoa_record_voice_Button);


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


        ahozkoa_record_voice_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent, AUDIO_REQUEST_CODE);
                }else{
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK)return;
        switch (requestCode){
            case AUDIO_REQUEST_CODE:
                if(data!=null){
                    dataResult=data;

                    mEngine.setOnGetUserNowListener(new UsersManager.OnGetUserNowListener() {
                        @Override
                        public void onGetUserNow(User user) {
                            mEngine.setOnUpdateFileListener(new LevelsManager.OnUpdateFileListener() {
                                @Override
                                public void onUpdateFile(Boolean result) {
                                    if(result){
                                        Exam exam=new Exam();
                                        exam.setLevel(levelString);
                                        exam.setNumExams(numeroExamen);
                                        exam.setTypeExam("ahozkoa");
                                        exam.setVoiceFileName(titleRecordAudio);
                                        saveUserExam(exam);
                                        Toast.makeText(getApplicationContext(),getString(R.string.No_se_ha_subido_correctamente_al_servidor),Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),getString(R.string.No_se_ha_subido_correctamente_al_servidor),Toast.LENGTH_LONG).show();
                                    }
                                    finish();
                                }
                            });
                            titleRecordAudio=UtilsServices.getMACaddress(getApplicationContext()) +"_"+user.getName()+"_"+levelString+"_"+numeroExamen+"_.3gpp";
                            mEngine.updateFile(dataResult.getData(), titleRecordAudio);
                        }
                    });
                    mEngine.getUserNow();


                }
                break;
            default:
                break;
        }
    }

}
