package eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 8/01/16.
 */
public class AtarikoaStatements implements Serializable {
    private String statement;
    private String solution;
    private int index=100;
    private ArrayList<AnswerAtarikoa> answers;

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

    public ArrayList<AnswerAtarikoa> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerAtarikoa> answers) {
        this.answers = answers;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
