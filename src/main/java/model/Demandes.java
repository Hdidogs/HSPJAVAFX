package model;

import java.sql.Date;

public class Demandes {
    private int id;
    private int refMedecin;
    private int refProduits;
    private int quantite;
    private int refEtat;
    private Date date;
    private Integer refGestionnaire;

    public Demandes(int id, int refMedecin, int refProduits, int quantite, int refEtat, Date date, Integer refGestionnaire) {
        this.id = id;
        this.refMedecin = refMedecin;
        this.refProduits = refProduits;
        this.quantite = quantite;
        this.refEtat = refEtat;
        this.date = date;
        this.refGestionnaire = refGestionnaire;
    }

    public Demandes(int refMedecin, int refProduits, int quantite, int refEtat, Date date, Integer refGestionnaire) {
        this.refMedecin = refMedecin;
        this.refProduits = refProduits;
        this.quantite = quantite;
        this.refEtat = refEtat;
        this.date = date;
        this.refGestionnaire = refGestionnaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefMedecin() {
        return refMedecin;
    }

    public void setRefMedecin(int refMedecin) {
        this.refMedecin = refMedecin;
    }

    public int getRefProduits() {
        return refProduits;
    }

    public void setRefProduits(int refProduits) {
        this.refProduits = refProduits;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getRefEtat() {
        return refEtat;
    }

    public void setRefEtat(int refEtat) {
        this.refEtat = refEtat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRefGestionnaire() {
        return refGestionnaire;
    }

    public void setRefGestionnaire(Integer refGestionnaire) {
        this.refGestionnaire = refGestionnaire;
    }
}
