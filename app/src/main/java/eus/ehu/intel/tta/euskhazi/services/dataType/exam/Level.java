package eus.ehu.intel.tta.euskhazi.services.dataType.exam;

import java.io.Serializable;
import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak.Berridazketak;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.Entzunezkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.Idatzizkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.Sinonimoak;

/**
 * Created by eduardo on 8/01/16.
 */
public class Level implements Serializable {
    private ArrayList<Atarikoa> atarikoas;
    private ArrayList<Ahozkoa> ahozkoas;
    private ArrayList<Berridazketak> berridazketaks;
    private ArrayList<Entzunezkoa> entzunezkoas;
    private ArrayList<Idatzizkoa> idatzizkoas;
    private ArrayList<Sinonimoak> sinonimoaks;
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

    public ArrayList<Berridazketak> getBerridazketaks() {
        return berridazketaks;
    }

    public void setBerridazketaks(ArrayList<Berridazketak> berridazketaks) {
        this.berridazketaks = berridazketaks;
    }

    public String getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(String typeLevel) {
        this.typeLevel = typeLevel;
    }

    public ArrayList<Entzunezkoa> getEntzunezkoas() {
        return entzunezkoas;
    }

    public void setEntzunezkoas(ArrayList<Entzunezkoa> entzunezkoas) {
        this.entzunezkoas = entzunezkoas;
    }

    public ArrayList<Idatzizkoa> getIdatzizkoas() {
        return idatzizkoas;
    }

    public void setIdatzizkoas(ArrayList<Idatzizkoa> idatzizkoas) {
        this.idatzizkoas = idatzizkoas;
    }

    public ArrayList<Sinonimoak> getSinonimoaks() {
        return sinonimoaks;
    }

    public void setSinonimoaks(ArrayList<Sinonimoak> sinonimoaks) {
        this.sinonimoaks = sinonimoaks;
    }
}
