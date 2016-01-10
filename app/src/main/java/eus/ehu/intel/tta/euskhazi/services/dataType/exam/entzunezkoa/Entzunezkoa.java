package eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eduardo on 10/01/16.
 */
public class Entzunezkoa implements Serializable {
    private String audioUrl;
    private ArrayList<StatementEntzunezkoa> statements;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public ArrayList<StatementEntzunezkoa> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementEntzunezkoa> statements) {
        this.statements = statements;
    }
}
