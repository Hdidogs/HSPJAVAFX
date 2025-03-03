package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String serveur = "localhost";
    private String nomDeLaBase = "hspjava";
    private String utilisateur = "root";
    private String motDePasse = "";

    private String getUrl() {
        return "jdbc:mysql://" + serveur + "/" + nomDeLaBase +  "?serverTimezone=UTC";
    }

    public Connection getConnection() {
        try {
            Connection cnx = DriverManager.getConnection(getUrl(), utilisateur, motDePasse);
            return cnx;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la tentative de connexion à la base de données.");
            e.printStackTrace();
            return null;
        }
    }
}
