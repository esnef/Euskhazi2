package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;

public class ScreenListaExamsAtarikoa extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_exams_atarikoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<String> exams = new ArrayList<String>();
        for (int i = 1; i < 6; i++){
            String azterketa = "Azterketa " + i;
            exams.add(azterketa);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textview_layout, R.id.exams_textView, exams);
        ListView lstOpciones = (ListView) findViewById(R.id.atarikoa_exams_listView);
        lstOpciones.setAdapter(adapter);

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Context context = getApplicationContext();
                Toast.makeText(context, "Aukeratutako azterketa " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
