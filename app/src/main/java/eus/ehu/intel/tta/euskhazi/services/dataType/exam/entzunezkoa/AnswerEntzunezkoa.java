package eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa;

import java.io.Serializable;

/**
 * Created by eduardo on 10/01/16.
 */
public class AnswerEntzunezkoa implements Serializable {
    private String first;
    private String second;
    private String third;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }
}
