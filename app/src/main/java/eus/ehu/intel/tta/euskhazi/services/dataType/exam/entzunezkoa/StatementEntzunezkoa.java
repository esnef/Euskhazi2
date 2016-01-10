package eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 10/01/16.
 */
public class StatementEntzunezkoa implements Serializable {
    private String statement;
    private String solution;
    private ArrayList<AnswerEntzunezkoa> answers;

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

    public ArrayList<AnswerEntzunezkoa> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerEntzunezkoa> answers) {
        this.answers = answers;
    }
}
