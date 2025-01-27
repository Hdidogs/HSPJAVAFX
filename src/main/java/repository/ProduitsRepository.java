package repository;

import database.Database;
import model.Produits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitsRepository {
    public static boolean addProduit(String libelle, String description, int niveauDangerosite, int quantite, int refFournisseurs) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO produits (libelle, description, niveau_dangerosite, qtn, ref_fournisseurs) VALUES (?, ?, ?, ?, ?)");
        req.setString(1, libelle);
        req.setString(2, description);
        req.setInt(3, niveauDangerosite);
        req.setInt(4, quantite);
        req.setInt(5, refFournisseurs);

        return req.executeUpdate() == 1;
    }

    public static boolean deleteProduit(int idProduit) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM produits WHERE id_produits = ?");
        req.setInt(1, idProduit);

        return req.executeUpdate() == 1;
    }

    public static boolean updateProduit(int idProduit, String libelle, String description, int niveauDangerosite, int quantite, int refFournisseurs) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE produits SET libelle = ?, description = ?, niveau_dangerosite = ?, qtn = ?, ref_fournisseurs = ? WHERE id_produits = ?");
        req.setString(1, libelle);
        req.setString(2, description);
        req.setInt(3, niveauDangerosite);
        req.setInt(4, quantite);
        req.setInt(5, refFournisseurs);
        req.setInt(6, idProduit);

        return req.executeUpdate() == 1;
    }

    public static List<Produits> getAllProduits() throws SQLException {
        List<Produits> produits = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM produits");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            produits.add(new Produits(
                rs.getInt("id_produits"),
                rs.getString("libelle"),
                rs.getString("description"),
                rs.getInt("niveau_dangerosite"),
                rs.getInt("qtn"),
                rs.getInt("ref_fournisseurs")
            ));
        }

        return produits;
    }
}
