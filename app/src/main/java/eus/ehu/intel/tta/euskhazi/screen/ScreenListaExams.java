package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;

public class ScreenListaExams extends ScreenBase {

    private String examType;
    private int typeLevel;
    private String levelString;

    private ExamsAdapter adapter;
    private ArrayList<ExamType> exams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_exams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        levelString = intent.getStringExtra("level");
        typeLevel = intent.getExtras().getInt("typeLevel");
        final TextView tituloTextView = (TextView) findViewById(R.id.content_lista_exams_title_textView);




        mEngine.setOnGetLevelListener(new LevelsManager.OnGetLevelListener() {
            @Override
            public void onGetLevel(Level levels) {
                if (levels == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_hay_examenes), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int cantidadExamenes = 0;
                    switch (typeLevel) {
                        case 0:
                            tituloTextView.setText("Atarikoa " + levelString);
                            examType="atarikoa";
                            cantidadExamenes = levels.getAtarikoas().size();
                            break;
                        case 1:
                            tituloTextView.setText("Idatzizkoa " + levelString);
                            examType="idatzizkoa";
                            cantidadExamenes = levels.getIdatzizkoas().size();
                            break;
                        case 2:
                            tituloTextView.setText("Sinonimoak " + levelString);
                            examType="sinonimoak";
                            cantidadExamenes = levels.getSinonimoaks().size();
                            break;
                        case 3:
                            tituloTextView.setText("Berridazketak " + levelString);
                            examType="berridazketak";
                            cantidadExamenes = levels.getBerridazketaks().size();
                            break;
                        case 4:
                            tituloTextView.setText("Entzunezkoa " + levelString);
                            examType="entzumena";
                            cantidadExamenes = levels.getEntzunezkoas().size();
                            break;
                        case 5:
                            tituloTextView.setText("Ahozkoa " + levelString);
                            examType="ahozkoa";
                            cantidadExamenes = levels.getAhozkoas().size();
                            break;
                    }
                    //aqui es donde genera los Strings
                    exams = new ArrayList<ExamType>();
                    for (int i = 0; i < cantidadExamenes; i++) {
                        ExamType examType=new ExamType("Azterketa",levelString,i+1);
                        String azterketa = "Azterketa " + (i+1);
                        //Button button = new Button(this);
                        //button.setText("Azterketa " + i);
                        exams.add(examType);
                    }
                    adapter = new ExamsAdapter(getApplicationContext(), exams);


                    //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_layout, R.id.exams_textView, exams);
                    ListView lstOpciones = (ListView) findViewById(R.id.atarikoa_exams_listView);
                    lstOpciones.setAdapter(adapter);

                    lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                            Context context = getApplicationContext();
                            Toast.makeText(context, "Aukeratutako azterketa " + (position+1), Toast.LENGTH_SHORT).show();

                            switch (typeLevel) {
                                case 0:
                                    Intent intent0 = new Intent(getApplicationContext(), ScreenAtarikoa.class);
                                    intent0.putExtra("level", levelString);
                                    intent0.putExtra("numeroExamen", position);
                                    startActivity(intent0);
                                    break;
                                case 1:
                                    Intent intent1 = new Intent(getApplicationContext(), ScreenIdatzizkoa.class);
                                    intent1.putExtra("level", levelString);
                                    intent1.putExtra("numeroExamen", position);
                                    startActivity(intent1);
                                    break;
                                case 2:
                                    Intent intent2 = new Intent(getApplicationContext(), ScreenSinonimoak.class);
                                    intent2.putExtra("level", levelString);
                                    intent2.putExtra("numeroExamen", position);
                                    startActivity(intent2);
                                    break;
                                case 3:
                                    Intent intent3 = new Intent(getApplicationContext(), ScreenBerridazketak.class);
                                    intent3.putExtra("level", levelString);
                                    intent3.putExtra("numeroExamen", position);
                                    startActivity(intent3);
                                    break;
                                case 4:
                                    Intent intent4 = new Intent(getApplicationContext(), ScreenEntzumena.class);
                                    intent4.putExtra("level", levelString);
                                    intent4.putExtra("numeroExamen", position);
                                    startActivity(intent4);
                                    break;
                                case 5:
                                    Intent intent5 = new Intent(getApplicationContext(), ScreenAhozkoa.class);
                                    intent5.putExtra("level", levelString);
                                    intent5.putExtra("numeroExamen", position);
                                    startActivity(intent5);
                                    break;
                            }
                        }
                    });
                }
                mEngine.setOnGetLevelListener(null);
            }
        });
        mEngine.getLevel(levelString);


    }

    public class ExamType {
        public String type;
        public int index;
        public double score;
        public String level;

        public ExamType(String type, String level, int index,double score) {
            this.type = type;
            this.level = level;
            this.index=index;
            this.score=score;
        }
        public ExamType(String type, String level, int index) {
            this.type = type;
            this.level = level;
            this.index=index;
            this.score=-1;
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        mEngine.setOnGetUserNowListener(new UsersManager.OnGetUserNowListener() {
            @Override
            public void onGetUserNow(User user) {
                if (user != null) {
                    if (user.getExams() == null) {
                        user.setExams(new ArrayList<Exam>());
                    }
                    for (int con = 0; con < user.getExams().size(); con++) {
                        if (user.getExams().get(con).getTypeExam().equals(examType) && user.getExams().get(con).getLevel().equals(levelString)) {
                            //Entonces esta aqui con lo que se puede printar
                            int numExam = user.getExams().get(con).getNumExams();
                            exams.get(numExam).score = user.getExams().get(con).getResult();
                        }
                    }
                    adapter.examsType=exams;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        mEngine.getUserNow();
    }


    public class ExamsAdapter extends ArrayAdapter<ExamType> {
        public ArrayList<ExamType> examsType;
        public ExamsAdapter(Context context, ArrayList<ExamType> examsType) {
            super(context, 0, examsType);
            this.examsType=examsType;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ExamType examType = examsType.get(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_examstype, parent, false);
            }
            // Lookup view for data population
            TextView item_examsType_TextView_index = (TextView) convertView.findViewById(R.id.item_examsType_TextView_index);
            TextView item_examsType_TextView_level = (TextView) convertView.findViewById(R.id.item_examsType_TextView_level);
            TextView item_examsType_TextView_type = (TextView) convertView.findViewById(R.id.item_examsType_TextView_type);

            // Populate the data into the template view using the data object
            item_examsType_TextView_index.setText(Integer.toString(examType.index));
            item_examsType_TextView_level.setText(getString(R.string.Nivel)+":"+examType.level);
            item_examsType_TextView_type.setText(examType.type);
            // Return the completed view to render on screen
            if(examType.score>0){
                if(examType.score>=5){
                    //aprobado verde
                    item_examsType_TextView_index.setBackgroundColor(Color.argb(255, 16, 178, 35));
                }else{
                    item_examsType_TextView_index.setBackgroundColor(Color.argb(255, 206, 29, 29));
                    //suspendido rojo
                }
                DecimalFormat df = new DecimalFormat("##.##");
                df.setRoundingMode(RoundingMode.DOWN);
                String string=df.format(examType.score);
                item_examsType_TextView_type.setText(item_examsType_TextView_type.getText() + "  " + getString(R.string.Puntuacion) + ": " + string);
            }



            return convertView;
        }
    }

}
