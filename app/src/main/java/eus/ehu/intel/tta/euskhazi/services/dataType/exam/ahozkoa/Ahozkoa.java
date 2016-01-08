package eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 8/01/16.
 */
public class Ahozkoa implements Serializable{
    private String title;
    private String explanation;
    private ArrayList<Question> questions;
    private ArrayList<ImageURL> imagesURL;
    private String conditions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<ImageURL> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(ArrayList<ImageURL> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
