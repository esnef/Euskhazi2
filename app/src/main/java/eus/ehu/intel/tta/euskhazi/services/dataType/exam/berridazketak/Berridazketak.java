package eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 10/01/16.
 */
public class Berridazketak implements Serializable {
    private ArrayList<StatementBerridazketak> statements;

    public ArrayList<StatementBerridazketak> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementBerridazketak> statements) {
        this.statements = statements;
    }
}
