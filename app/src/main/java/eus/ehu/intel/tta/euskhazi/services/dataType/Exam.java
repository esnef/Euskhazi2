package eus.ehu.intel.tta.euskhazi.services.dataType;

import java.io.Serializable;

/**
 * Created by alumno on 8/01/16.
 */
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idexams;

    /**
     * en los examenes que hay que escribir, se escribe aqui
     */
    private String drafting;

    /**
     * el nivel del examen
     */
    private String level;

    /**
     * número de examen
     */
    private int numExam;
    private double result;

    /**
     *el nombre del tipo de examen
     */
    private String typeExam;

    /**
     * el path al archivo de audio de ahozkoa
     */
    private String voiceFileName;



    public Exam() {
    }

    public Exam(String level,int numExam,double result,String typeExam,String voiceFileName,String drafting) {
        this.drafting=drafting;
        this.level=level;
        this.numExam=numExam;
        this.result=result;
        this.typeExam=typeExam;
        this.voiceFileName=voiceFileName;
    }


    public int getIdexams() {
        return this.idexams;
    }

    public void setIdexams(int idexams) {
        this.idexams = idexams;
    }

    public String getDrafting() {
        return this.drafting;
    }

    public void setDrafting(String drafting) {
        this.drafting = drafting;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getNumExams() {
        return this.numExam;
    }

    public void setNumExams(int numExam) {
        this.numExam = numExam;
    }

    public double getResult() {
        return this.result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getTypeExam() {
        return this.typeExam;
    }

    public void setTypeExam(String typeExam) {
        this.typeExam = typeExam;
    }

    public String getVoiceFileName() {
        return this.voiceFileName;
    }

    public void setVoiceFileName(String voiceFileName) {
        this.voiceFileName = voiceFileName;
    }



}