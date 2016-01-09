package eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa;

import java.io.Serializable;

/**
 * Created by eduardo on 8/01/16.
 */
public class Question implements Serializable {
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
