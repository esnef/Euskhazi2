package eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 10/01/16.
 */
public class Idatzizkoa implements Serializable {
    private String title;
    private String explanation;
    private ArrayList<ItemIdatzizkoa> items;
    private ArrayList<ImageURLIdatzizkoa> imagesURL;
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

    public ArrayList<ItemIdatzizkoa> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemIdatzizkoa> items) {
        this.items = items;
    }

    public ArrayList<ImageURLIdatzizkoa> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(ArrayList<ImageURLIdatzizkoa> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
