package eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;

/**
 * Created by irazu on 24/01/16.
 */
public class SinonimoakAdapter extends ArrayAdapter<StatementSinonimoak> {

    public SinonimoakAdapter(Context context, ArrayList<StatementSinonimoak> sinonimoak){
        super(context, R.layout.sinonimoak_layout, sinonimoak);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View item = convertView;
        ViewHolder viewHolder;

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.sinonimoak_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) item.findViewById(R.id.sinonimoak_layout_textView);
            viewHolder.editText = (EditText) item.findViewById(R.id.sinonimoak_layout_editText);
            item.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) item.getTag();
        }

        StatementSinonimoak statementSinonimoak = getItem(position);

        //TextView sinonimoakLayoutTextView = (TextView)item.findViewById(R.id.sinonimoak_layout_textView);
        //sinonimoakLayoutTextView.setText(statementSinonimoak.getStatement() + " (" + statementSinonimoak.getPlaceholder() + ")");

        viewHolder.textView.setText(statementSinonimoak.getStatement());
        viewHolder.editText.setText(statementSinonimoak.getSolution());

        return  item;
    }

    static class ViewHolder{
        TextView textView;
        EditText editText;
    }
}
