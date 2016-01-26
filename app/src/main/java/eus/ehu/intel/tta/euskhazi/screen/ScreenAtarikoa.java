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
                    System.out.println(1);
                    atarikoa.getStatements().get(statementNext).setIndex(0);
                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechFirst(true);
                }else if(data[1]==v.getId()){
                    System.out.println(2);
                    atarikoa.getStatements().get(statementNext).setIndex(1);
                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechSecond(true);

                }else if(data[2]==v.getId()){
                    System.out.println(3);
                    atarikoa.getStatements().get(statementNext).setIndex(2);
                    atarikoa.getStatements().get(statementNext).getAnswers().get(0).setChechThird(true);
                }else if(data[3]==v.getId()){
                    System.out.println(4);
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
                    double result=(okquestions/atarikoa.getStatements().size())*10;
                    Toast.makeText(getApplicationContext(),"Azken puntuazioa "+result+" da",Toast.LENGTH_SHORT).show();
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
        atarikoa_TextView_statement.setText((index+1)+"-."+atarikoa.getStatements().get(index).getStatement());
        atarikoa_RadioButton_solutions_1.setText(atarikoa.getStatements().get(index).getAnswers().get(0).getFirst());
        atarikoa_RadioButton_solutions_2.setText(atarikoa.getStatements().get(index).getAnswers().get(0).getSecond());
        atarikoa_RadioButton_solutions_3.setText(atarikoa.getStatements().get(index).getAnswers().get(0).getThird());
        atarikoa_RadioButton_solutions_4.setText(atarikoa.getStatements().get(index).getAnswers().get(0).getFourth());
        atarikoa_RadioButton_solutions_1.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechFirst());
        atarikoa_RadioButton_solutions_2.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechSecond());
        atarikoa_RadioButton_solutions_3.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechThird());
        atarikoa_RadioButton_solutions_4.setChecked(atarikoa.getStatements().get(index).getAnswers().get(0).isChechFourth());
    }


/*
    static class ViewHolder {
        protected TextView text;
        protected RadioGroup radioGroup;
        protected RadioButton radioButton1;
        protected RadioButton radioButton2;
        protected RadioButton radioButton3;
        protected RadioButton radioButton4;
    }
    public class InteractiveArrayAdapter extends ArrayAdapter<AtarikoaStatements> {
        private boolean control;
        private final List<AtarikoaStatements> list;
        private final Activity context;

        public InteractiveArrayAdapter(Activity context, ArrayList<AtarikoaStatements> list) {
            super(context, R.layout.content_screen_atarikoa, list);
            this.context = context;
            this.list = list;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                LayoutInflater inflator = context.getLayoutInflater();
                view = inflator.inflate(R.layout.item_atarikoa, null);
                final ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) view.findViewById(R.id.item_atarikoa_TextView_statement);
                viewHolder.radioGroup = (RadioGroup) view.findViewById(R.id.item_atarikoa_RadioGroup_solutions);

                viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        System.out.println(checkedId+" checkedId");
                        AtarikoaStatements element = (AtarikoaStatements) group
                                .getTag();
                        RadioButton radioButton= (RadioButton)group.getChildAt((group.getCheckedRadioButtonId()-(element.getIndex()*4)));
                        if(radioButton==null){
                            System.out.println("B "+element.getIndex()+" "+(group.getCheckedRadioButtonId()-(element.getIndex()*4)));
                            return;
                        }else{
                            System.out.println("A "+element.getIndex()+" "+(group.getCheckedRadioButtonId()-(element.getIndex()*4)));
                        }
                        if(radioButton.getText().equals(element.getAnswers().get(0).getFirst())){
                            element.getAnswers().get(0).setChechFirst(true);
                            element.getAnswers().get(0).setChechSecond(false);
                            element.getAnswers().get(0).setChechThird(false);
                            element.getAnswers().get(0).setChechFourth(false);
                        }else if(radioButton.getText().equals(element.getAnswers().get(0).getSecond())){
                            element.getAnswers().get(0).setChechFirst(false);
                            element.getAnswers().get(0).setChechSecond(true);
                            element.getAnswers().get(0).setChechThird(false);
                            element.getAnswers().get(0).setChechFourth(false);
                        }else if(radioButton.getText().equals(element.getAnswers().get(0).getThird())){

                            element.getAnswers().get(0).setChechFirst(false);
                            element.getAnswers().get(0).setChechSecond(false);
                            element.getAnswers().get(0).setChechThird(true);
                            element.getAnswers().get(0).setChechFourth(false);
                        }else if(radioButton.getText().equals(element.getAnswers().get(0).getFourth())){
                            element.getAnswers().get(0).setChechFirst(false);
                            element.getAnswers().get(0).setChechSecond(false);
                            element.getAnswers().get(0).setChechThird(false);
                            element.getAnswers().get(0).setChechFourth(true);
                        }

                    }
                });


                System.out.println("position: " + position);

                view.setTag(viewHolder);
                viewHolder.radioGroup.setTag(list.get(position));
                viewHolder.radioButton1=new RadioButton(context);
                viewHolder.radioGroup.addView(viewHolder.radioButton1);
                viewHolder.radioButton2=new RadioButton(context);
                viewHolder.radioGroup.addView(viewHolder.radioButton2);
                viewHolder.radioButton3=new RadioButton(context);
                viewHolder.radioGroup.addView(viewHolder.radioButton3);
                viewHolder.radioButton4=new RadioButton(context);
                viewHolder.radioGroup.addView(viewHolder.radioButton4);
            } else {
                view = convertView;
                ((ViewHolder) view.getTag()).radioGroup.setTag(list.get(position));
            }
            System.out.println("position1: " + position);

            ViewHolder holder = (ViewHolder) view.getTag();



            holder.text.setText(list.get(position).getStatement());
            holder.radioButton1.setText(list.get(position).getAnswers().get(0).getFirst());
            holder.radioButton2.setText(list.get(position).getAnswers().get(0).getSecond());
            holder.radioButton3.setText(list.get(position).getAnswers().get(0).getThird());
            holder.radioButton4.setText(list.get(position).getAnswers().get(0).getFourth());
            control=true;
            holder.radioButton1.setChecked(list.get(position).getAnswers().get(0).isChechFirst());
            control=false;
            control=true;
            holder.radioButton2.setChecked(list.get(position).getAnswers().get(0).isChechSecond());
            control=false;

            holder.radioButton3.setChecked(list.get(position).getAnswers().get(0).isChechThird());
            holder.radioButton4.setChecked(list.get(position).getAnswers().get(0).isChechFourth());

            return view;
        }
    }



*/




/*
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


    // The Holder
    static class StatementAtarikoaHolder {
        private TextView statement = null;
        private RadioButton first;
        private RadioButton second;
        private RadioButton third;
        private RadioButton fourth;
        RadioGroup item_atarikoa_RadioGroup_solutions;
        private int position;

        StatementAtarikoaHolder(View row) {
            statement= (TextView) row.findViewById(R.id.item_atarikoa_TextView_statement);
            first = (RadioButton) row.findViewById(R.id.item_atarikoa_RadioButton_solutions_1);
            second = (RadioButton) row.findViewById(R.id.item_atarikoa_RadioButton_solutions_2);
            third = (RadioButton) row.findViewById(R.id.item_atarikoa_RadioButton_solutions_3);
            fourth = (RadioButton) row.findViewById(R.id.item_atarikoa_RadioButton_solutions_4);
            item_atarikoa_RadioGroup_solutions=(RadioGroup) row.findViewById(R.id.item_atarikoa_RadioGroup_solutions);

        }
        public void populateFrom(AtarikoaStatements atarikoaStatements, int position, ScreenAtarikoa activity){
            statement.setText((position+1)+"-."+atarikoaStatements.getStatement());
            first.setText(atarikoaStatements.getAnswers().get(0).getFirst());
            second.setText(atarikoaStatements.getAnswers().get(0).getSecond());
            third.setText(atarikoaStatements.getAnswers().get(0).getThird());
            fourth.setText(atarikoaStatements.getAnswers().get(0).getFourth());
            item_atarikoa_RadioGroup_solutions.setOnCheckedChangeListener(activity);
        }


    }

    // The Adapter
    class statementAtarikoaAdapter extends ArrayAdapter<myObject> {
        statementAtarikoaAdapter() {
            super(get, R.layout.my_row_layout,
                    listItems);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            myObjectHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_row_layout,
                        parent, false);
                holder = new myObjectHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (myObjectHolder) convertView.getTag();
            }
            holder.populateFrom(listItems.get(position));
            return (convertView);
        }
    }
    public class StatementAtarikoaAdapter extends ArrayAdapter<AtarikoaStatements> {
        public ArrayList<AtarikoaStatements> atarikoaStatements;
        public ScreenAtarikoa mAtarikoa;
        public StatementAtarikoaAdapter(Context context, ArrayList<AtarikoaStatements> atarikoaStatements,ScreenAtarikoa mAtarikoa) {
            super(context,R.layout.item_atarikoa, atarikoaStatements);
            this.atarikoaStatements=atarikoaStatements;
            this.mAtarikoa=mAtarikoa;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StatementAtarikoaHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_atarikoa, parent, false);
                holder = new StatementAtarikoaHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (StatementAtarikoaHolder) convertView.getTag();
            }
            holder.populateFrom(atarikoaStatements.get(position),position,mAtarikoa);
            return (convertView);

            // Get the data item for this position
            StatementAtarikoaHolder examType = statementAtarikoaHolders.get(position);
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
                    item_examsType_TextView_index.setBackgroundColor(Color.argb(255, 206,29,29));
                    //suspendido rojo
                }
            }
            return convertView;


        }
    }
    */



}
