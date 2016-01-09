package eus.ehu.intel.tta.euskhazi.services.dataType.exam;

import java.io.Serializable;
import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;

/**
 * Created by eduardo on 8/01/16.
 */
public class Level implements Serializable {
    private ArrayList<Atarikoa> atarikoas;
    private ArrayList<Ahozkoa> ahozkoas;
    private String typeLevel;

    public ArrayList<Atarikoa> getAtarikoas() {
        return atarikoas;
    }

    public void setAtarikoas(ArrayList<Atarikoa> atarikoas) {
        this.atarikoas = atarikoas;
    }

    public ArrayList<Ahozkoa> getAhozkoas() {
        return ahozkoas;
    }

    public void setAhozkoas(ArrayList<Ahozkoa> ahozkoas) {
        this.ahozkoas = ahozkoas;
    }

    public String getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(String typeLevel) {
        this.typeLevel = typeLevel;
    }
}
