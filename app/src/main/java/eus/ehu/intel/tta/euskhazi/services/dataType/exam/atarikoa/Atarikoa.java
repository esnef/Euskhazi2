package eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 8/01/16.
 */
public class Atarikoa implements Serializable {

    private ArrayList<AtarikoaStatements> statements;


    public ArrayList<AtarikoaStatements> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<AtarikoaStatements> statements) {
        this.statements = statements;
    }
}
