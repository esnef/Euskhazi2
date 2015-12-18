package eus.ehu.intel.tta.euskhazi.services.data;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class Exam {
    private static String TAG = Exam.class.getCanonicalName();

    private String drafting;
    private String level;
    private int numExams;
    private double resultExams;
    private String typeExam;
    private String voiceFileName;

    public Exam() {
    }

    public Exam(String level,int numExams,double resultExams,String typeExam,String voiceFileName,String drafting) {
        this.drafting=drafting;
        this.level=level;
        this.numExams=numExams;
        this.resultExams=resultExams;
        this.typeExam=typeExam;
        this.voiceFileName=voiceFileName;
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
        return this.numExams;
    }

    public void setNumExams(int numExams) {
        this.numExams = numExams;
    }

    public double getResultExams() {
        return this.resultExams;
    }

    public void setResultExams(double resultExams) {
        this.resultExams = resultExams;
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
