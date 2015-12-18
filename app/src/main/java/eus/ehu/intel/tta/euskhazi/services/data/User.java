package eus.ehu.intel.tta.euskhazi.services.data;

import java.util.List;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class User {
    private static String TAG = User.class.getCanonicalName();
    private String name;
    private String pass;
    private List<Exam> exams;

    public User() {
    }
    public User(String name,String pass,List<Exam> exams) {
        this.name=name;
        this.pass=pass;
        this.exams=exams;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Exam> getExams() {
        return this.exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public Exam addExam(Exam exam) {
        if (exam!=null) {
            exams.add(exam);
        }

        return exam;
    }





}
