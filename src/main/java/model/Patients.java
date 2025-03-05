package model;

public class Patients {
    private int id;
    private String nom;
    private String prenom;
    private String numSecu;
    private String mail;
    private String tel;
    private String rue;
    private String ville;
    private int cp;

    public Patients(int id, String nom, String prenom, String numSecu, String mail, String tel, String rue, String ville, int cp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numSecu = numSecu;
        this.mail = mail;
        this.tel = tel;
        this.rue = rue;
        this.ville = ville;
        this.cp = cp;
    }

    public Patients(String nom, String prenom, String numSecu, String mail, String tel, String rue, String ville, int cp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numSecu = numSecu;
        this.mail = mail;
        this.tel = tel;
        this.rue = rue;
        this.ville = ville;
        this.cp = cp;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumSecu() {
        return numSecu;
    }

    public void setNumSecu(String numSecu) {
        this.numSecu = numSecu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getAdresseComplete() {
        return rue + ", " + ville;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
