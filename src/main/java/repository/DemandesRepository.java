package repository;

import database.Database;
import model.Demandes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandesRepository {
    public static boolean addDemande(Demandes demandes) throws SQLException {
        return addDemande(demandes.getRefMedecin(), demandes.getRefProduits(), demandes.getQuantite(), demandes.getRefEtat(), demandes.getDate(), demandes.getRefGestionnaire());
    }

    public static boolean addDemande(int refMedecin, int refProduits, int quantite, int refEtat, Date date, Integer refGestionnaire) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO demandes (ref_medecin, ref_produits, qtn, ref_etat, date, ref_gestionnaire) VALUES (?, ?, ?, ?, ?, ?)");
        req.setInt(1, refMedecin);
        req.setInt(2, refProduits);
        req.setInt(3, quantite);
        req.setInt(4, refEtat);
        req.setDate(5, new Date(date.getTime()));
        if (refGestionnaire != null) {
            req.setInt(6, refGestionnaire);
        } else {
            req.setNull(6, Types.INTEGER);
        }

        return req.executeUpdate() == 1;
    }

    public static boolean deleteDemande(int idDemande) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM demandes WHERE id_demandes = ?");
        req.setInt(1, idDemande);

        return req.executeUpdate() == 1;
    }

    public static boolean updateDemande(Demandes demandes) throws SQLException {
        return updateDemande(demandes.getId() ,demandes.getRefMedecin(), demandes.getRefProduits(), demandes.getQuantite(), demandes.getRefEtat(), demandes.getDate(), demandes.getRefGestionnaire());
    }

    public static boolean updateDemande(int idDemande, int refMedecin, int refProduits, int quantite, int refEtat, Date date, Integer refGestionnaire) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE demandes SET ref_medecin = ?, ref_produits = ?, qtn = ?, ref_etat = ?, date = ?, ref_gestionnaire = ? WHERE id_demandes = ?");
        req.setInt(1, refMedecin);
        req.setInt(2, refProduits);
        req.setInt(3, quantite);
        req.setInt(4, refEtat);
        req.setDate(5, new java.sql.Date(date.getTime()));
        if (refGestionnaire != null) {
            req.setInt(6, refGestionnaire);
        } else {
            req.setNull(6, Types.INTEGER);
        }
        req.setInt(7, idDemande);

        return req.executeUpdate() == 1;
    }

    public static List<Demandes> getAllDemandes() throws SQLException {
        List<Demandes> demandes = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM demandes");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            demandes.add(new Demandes(
                    rs.getInt("id_demandes"),
                    rs.getInt("ref_medecin"),
                    rs.getInt("ref_produits"),
                    rs.getInt("qtn"),
                    rs.getInt("ref_etat"),
                    rs.getDate("date"),
                    rs.getInt("ref_gestionnaire")
            ));
        }

        return demandes;
    }
}
