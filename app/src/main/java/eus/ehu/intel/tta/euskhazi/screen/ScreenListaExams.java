package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;

public class ScreenListaExams extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_exams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String levelString = intent.getStringExtra("level");
        final int typeLevel = intent.getExtras().getInt("typeLevel");
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
                            cantidadExamenes = levels.getAtarikoas().size();
                            break;
                        case 1:
                            tituloTextView.setText("Idatzizkoa " + levelString);
                            cantidadExamenes = levels.getIdatzizkoas().size();
                            break;
                        case 2:
                            tituloTextView.setText("Sinonimoak " + levelString);
                            cantidadExamenes = levels.getSinonimoaks().size();
                            break;
                        case 3:
                            tituloTextView.setText("Berridazketak " + levelString);
                            cantidadExamenes = levels.getBerridazketaks().size();
                            break;
                        case 4:
                            tituloTextView.setText("Entzunezkoa " + levelString);
                            cantidadExamenes = levels.getEntzunezkoas().size();
                            break;
                        case 5:
                            tituloTextView.setText("Ahozkoa " + levelString);
                            cantidadExamenes = levels.getAhozkoas().size();
                            break;
                    }

                    ArrayList<String> exams = new ArrayList<>();
                    for (int i = 0; i < cantidadExamenes; i++) {
                        String azterketa = "Azterketa " + (i+1);
                        //Button button = new Button(this);
                        //button.setText("Azterketa " + i);
                        exams.add(azterketa);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_layout, R.id.exams_textView, exams);
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

}
