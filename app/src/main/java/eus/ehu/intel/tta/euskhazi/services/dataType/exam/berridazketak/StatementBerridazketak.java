package eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak;

import java.io.Serializable;

/**
 * Created by eduardo on 10/01/16.
 */
public class StatementBerridazketak implements Serializable {
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
