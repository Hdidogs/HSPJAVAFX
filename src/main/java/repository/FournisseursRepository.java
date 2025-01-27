package repository;

import database.Database;
import model.Fournisseurs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseursRepository {
    public static boolean addFournisseur(Fournisseurs fournisseur) throws SQLException {
        return addFournisseur(fournisseur.getNom(), fournisseur.getContact(), fournisseur.getPrixUnitaire());
    }

    public static boolean addFournisseur(String nom, String contact, float prixUnitaire) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO fournisseurs (nom, contact, prix_uni) VALUES (?, ?, ?)");
        req.setString(1, nom);
        req.setString(2, contact);
        req.setFloat(3, prixUnitaire);

        return req.executeUpdate() == 1;
    }

    public static boolean deleteFournisseur(int idFournisseur) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM fournisseurs WHERE id_fournisseurs = ?");
        req.setInt(1, idFournisseur);

        return req.executeUpdate() == 1;
    }

    public static boolean updateFournisseur(Fournisseurs fournisseur) throws SQLException {
        return updateFournisseur(fournisseur.getId(), fournisseur.getNom(), fournisseur.getContact(), fournisseur.getPrixUnitaire());
    }

    public static boolean updateFournisseur(int idFournisseur, String nom, String contact, float prixUnitaire) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE fournisseurs SET nom = ?, contact = ?, prix_uni = ? WHERE id_fournisseurs = ?");
        req.setString(1, nom);
        req.setString(2, contact);
        req.setFloat(3, prixUnitaire);
        req.setInt(4, idFournisseur);

        return req.executeUpdate() == 1;
    }

    public static List<Fournisseurs> getAllFournisseurs() throws SQLException {
        List<Fournisseurs> fournisseurs = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM fournisseurs");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            fournisseurs.add(new Fournisseurs(
                    rs.getInt("id_fournisseurs"),
                    rs.getString("nom"),
                    rs.getString("contact"),
                    rs.getFloat("prix_uni")
            ));
        }

        return fournisseurs;
    }

    public static Fournisseurs getFournisseurById(int refFournisseurs) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT * FROM fournisseurs WHERE id_fournisseurs = ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        req.setInt(1, refFournisseurs);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return new Fournisseurs(
                    rs.getInt("id_fournisseurs"),
                    rs.getString("nom"),
                    rs.getString("contact"),
                    rs.getFloat("prix_uni")
            );
        }

        return null;
    }
}
