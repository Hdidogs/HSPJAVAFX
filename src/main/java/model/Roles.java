package model;

public enum Roles {
    UTILISATEUR(1),
    MEDECIN(2),
    SECRETAIRE(3),
    GESTIONNAIREDESTOCK(4),
    ASMINISTRATEUR(5);

    private int i;

    Roles(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
