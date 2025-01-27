package model;

public class Produits {
    private int id;
    private String libelle;
    private String description;
    private int niveauDangerosite;
    private int quantite;
    private int refFournisseurs;

    public Produits(int id, String libelle, String description, int niveauDangerosite, int quantite, int refFournisseurs) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.niveauDangerosite = niveauDangerosite;
        this.quantite = quantite;
        this.refFournisseurs = refFournisseurs;
    }

    public Produits(String libelle, String description, int niveauDangerosite, int quantite, int refFournisseurs) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.niveauDangerosite = niveauDangerosite;
        this.quantite = quantite;
        this.refFournisseurs = refFournisseurs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNiveauDangerosite() {
        return niveauDangerosite;
    }

    public void setNiveauDangerosite(int niveauDangerosite) {
        this.niveauDangerosite = niveauDangerosite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getRefFournisseurs() {
        return refFournisseurs;
    }

    public void setRefFournisseurs(int refFournisseurs) {
        this.refFournisseurs = refFournisseurs;
    }
}
