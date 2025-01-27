package model;

import java.sql.Date;

public class Dossiers {
    private int id;
    private int refPatients;
    private int refUser;
    private Date dateArrivee;
    private String symptomes;
    private int niveauGravite;
    private int refEtat;
    private Date dateCloture;

    public Dossiers(int id, int refPatients, int refUser, Date dateArrivee, String symptomes, int niveauGravite, int refEtat, Date dateCloture) {
        this.id = id;
        this.refPatients = refPatients;
        this.refUser = refUser;
        this.dateArrivee = dateArrivee;
        this.symptomes = symptomes;
        this.niveauGravite = niveauGravite;
        this.refEtat = refEtat;
        this.dateCloture = dateCloture;
    }

    public Dossiers(int refPatients, int refUser, Date dateArrivee, String symptomes, int niveauGravite, int refEtat, Date dateCloture) {
        this.refPatients = refPatients;
        this.refUser = refUser;
        this.dateArrivee = dateArrivee;
        this.symptomes = symptomes;
        this.niveauGravite = niveauGravite;
        this.refEtat = refEtat;
        this.dateCloture = dateCloture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefPatients() {
        return refPatients;
    }

    public void setRefPatients(int refPatients) {
        this.refPatients = refPatients;
    }

    public int getRefUser() {
        return refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getSymptomes() {
        return symptomes;
    }

    public void setSymptomes(String symptomes) {
        this.symptomes = symptomes;
    }

    public int getNiveauGravite() {
        return niveauGravite;
    }

    public void setNiveauGravite(int niveauGravite) {
        this.niveauGravite = niveauGravite;
    }

    public int getRefEtat() {
        return refEtat;
    }

    public void setRefEtat(int refEtat) {
        this.refEtat = refEtat;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }
}
