package eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa;

import java.io.Serializable;

/**
 * Created by eduardo on 8/01/16.
 */
public class AnswerAtarikoa implements Serializable {
    private String first;
    private String second;
    private String third;
    private String fourth;

    private boolean chechFirst;
    private boolean chechSecond;
    private boolean chechThird;
    private boolean chechFourth;



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

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public boolean isChechFirst() {
        return chechFirst;
    }

    public void setChechFirst(boolean chechFirst) {
        this.chechFirst = chechFirst;
    }

    public boolean isChechFourth() {
        return chechFourth;
    }

    public void setChechFourth(boolean chechFourth) {
        this.chechFourth = chechFourth;
    }

    public boolean isChechThird() {
        return chechThird;
    }

    public void setChechThird(boolean chechThird) {
        this.chechThird = chechThird;
    }

    public boolean isChechSecond() {
        return chechSecond;
    }

    public void setChechSecond(boolean chechSecond) {
        this.chechSecond = chechSecond;
    }
}
