package model;

import java.sql.Date;

public class Chambres {
    private int id;
    private String num;
    private boolean dispo;
    private Date dateDebut;
    private Date dateFin;
    private int refDossiers;

    public Chambres(int id, String num, boolean dispo, Date dateDebut, Date dateFin, int refDossiers) {
        this.id = id;
        this.num = num;
        this.dispo = dispo;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.refDossiers = refDossiers;
    }

    public Chambres(String num, boolean dispo, Date dateDebut, Date dateFin, int refDossiers) {
        this.id = id;
        this.num = num;
        this.dispo = dispo;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.refDossiers = refDossiers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getRefDossiers() {
        return refDossiers;
    }

    public void setRefDossiers(int refDossiers) {
        this.refDossiers = refDossiers;
    }
}
