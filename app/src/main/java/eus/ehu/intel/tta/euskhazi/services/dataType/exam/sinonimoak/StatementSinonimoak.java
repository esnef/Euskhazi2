package eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak;

import java.io.Serializable;
import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.AnswerEntzunezkoa;

/**
 * Created by eduardo on 10/01/16.
 */
public class StatementSinonimoak implements Serializable {
    private String statement;
    private String solution;
    private String placeholder;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
