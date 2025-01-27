package model;

public class Fournisseurs {
    private int id;
    private String nom;
    private String contact;
    private float prixUnitaire;

    public Fournisseurs(int id, String nom, String contact, float prixUnitaire) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.prixUnitaire = prixUnitaire;
    }

    public Fournisseurs(String nom, String contact, float prixUnitaire) {
        this.nom = nom;
        this.contact = contact;
        this.prixUnitaire = prixUnitaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
