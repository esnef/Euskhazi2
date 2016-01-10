package eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 10/01/16.
 */
public class Sinonimoak implements Serializable {
    private ArrayList<StatementSinonimoak> statements;

    public ArrayList<StatementSinonimoak> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementSinonimoak> statements) {
        this.statements = statements;
    }
}
