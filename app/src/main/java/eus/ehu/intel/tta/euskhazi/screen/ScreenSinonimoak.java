package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
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

                ArrayList<SinonimoakType> sinonimoakTypes=new ArrayList<SinonimoakType>();
                for(int con=0;con<statements.size();con++){
                    sinonimoakTypes.add(new SinonimoakType(statements.get(con),new String(""),con));
                }

                SinonimoakAdapter sinonimoakAdapter = new SinonimoakAdapter(getApplicationContext(), sinonimoakTypes);
                ListView lstOpciones = (ListView) findViewById(R.id.sinonimoak_listView);
                lstOpciones.setAdapter(sinonimoakAdapter);

                mEngine.setOnGetLevelListener(null);
            }
        });
        mEngine.getLevel(levelString);
    }

    private class SinonimoakType {
        public StatementSinonimoak statementSinonimoak;
        public String response;
        public int index;


        public SinonimoakType(StatementSinonimoak statementSinonimoak, String response, int index) {
            this.statementSinonimoak = statementSinonimoak;
            this.response = response;
            this.index=index;
        }
    }
    public class SinonimoakAdapter extends ArrayAdapter<SinonimoakType> {
        private ArrayList<SinonimoakType> sinonimoakTypes;

        public SinonimoakAdapter(Context context, ArrayList<SinonimoakType> sinonimoakTypes){
            super(context, R.layout.sinonimoak_layout, sinonimoakTypes);
            this.sinonimoakTypes=sinonimoakTypes;
        }

        public View getView(int position, View convertView, ViewGroup parent){


            // Get the data item for this position
            SinonimoakType sinonimoakType = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.sinonimoak_layout, parent, false);
            }
            // Lookup view for data population
            TextView sinonimoak_layout_textView = (TextView) convertView.findViewById(R.id.sinonimoak_layout_textView);
            EditText sinonimoak_layout_editText = (EditText) convertView.findViewById(R.id.sinonimoak_layout_editText);

            // Populate the data into the template view using the data object
            sinonimoak_layout_textView.setText(sinonimoakType.statementSinonimoak.getStatement());
            sinonimoak_layout_editText.setHint(sinonimoakType.statementSinonimoak.getPlaceholder());
            // Return the completed view to render on screen

            return convertView;
        }

        /*
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            SinonimoakType sinonimoakType = sinonimoakTypes.get(position);


            if (v == null) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.sinonimoak_layout, parent, false);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) v.findViewById(R.id.sinonimoak_layout_textView);
                viewHolder.editText = (EditText) v.findViewById(R.id.sinonimoak_layout_editText);


                viewHolder.textView.addTextChangedListener(new TextWatcher(viewHolder,));
                viewHolder.editText.addTextChangedListener(new TextWatcher(viewHolder, viewHolder.editText));

                v.setTag(viewHolder);
                viewHolder.textView.setTag(sinonimoakType);
                viewHolder.editText.setTag(sinonimoakType);

            }

            else
            {
                ViewHolder holder = (ViewHolder) v.getTag();
                holder.text1.setTag(sinonimoakType);
                holder.text2.setTag(sinonimoakType);
            }

            ViewHolder holder = (ViewHolder) v.getTag();

            // set values

            if(wed.getWeight() != -1)
            {
                holder.text1.setText(wed.getWeight()+"");
            }

            else
            {
                holder.weight.setText("");
            }

            if(wed.getRepetitions() != -1)
            {
                holder.text2.setText(wed.getRepetitions()+"");
            }

            else
            {
                holder.reps.setText("");
            }

            return v;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable == mEditText1.getEditableText()) {
                // DO STH
            } else if (editable == mEditText2.getEditableText()) {
                // DO STH
            }
        }
        */

    }
    static class ViewHolder{
        TextView textView;
        EditText editText;
    }

}
