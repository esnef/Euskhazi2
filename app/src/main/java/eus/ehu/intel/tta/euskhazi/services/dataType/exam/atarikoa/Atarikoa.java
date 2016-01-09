package eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 8/01/16.
 */
public class Atarikoa implements Serializable {
    private String statement;
    private String solution;
    private ArrayList<Answer> answers;

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

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


}
