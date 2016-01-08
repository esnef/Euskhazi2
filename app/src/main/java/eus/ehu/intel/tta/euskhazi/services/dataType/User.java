package eus.ehu.intel.tta.euskhazi.services.dataType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alumno on 8/01/16.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    private int idusers;
    private String name;
    private String pass;
    //@XmlElement
    //private ExamsJSON examsJSON;

    private List<Exam> exams;

    public User() {
    }
    /*
    public UserJSON(String name,String pass,ExamsJSON examsJSON) {
        this.name=name;
        this.pass=pass;
        this.examsJSON=examsJSON;
    }
    */
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
	/*
	public ExamJSON addExam(ExamJSON examJSONList) {
		getExams().add(examJSONList);
		examJSONList.setUser(this);
		return examJSONList;
	}
	public ExamJSON removeExam(ExamJSON examJSONList) {
		getExams().remove(examJSONList);
		examJSONList.setUser(null);
		return examJSONList;
	}
	*/


}