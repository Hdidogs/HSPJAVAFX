package repository;

import database.Database;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DossiersRepository {
    public static boolean addDossiers(Dossiers dossier) throws SQLException {
        return addDossiers(dossier.getRefPatients(), dossier.getRefUser(), dossier.getDateArrivee(), dossier.getSymptomes(), dossier.getNiveauGravite(), dossier.getRefEtat(), dossier.getDateCloture());
    }

    public static boolean addDossiers(int refPatients, int refUser, Date dateArrivee, String symptomes, int niveauGravite, int refEtat, Date dateCloture) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO dossiers (ref_patients, ref_user, date_arrivee, symptomes, niveau_gravite, ref_etat, date_cloture) VALUES (?, ?, ?, ?, ?, ?, ?)");
        req.setInt(1, refPatients);
        req.setInt(2, refUser);
        req.setDate(3, new java.sql.Date(dateArrivee.getTime()));
        req.setString(4, symptomes);
        req.setInt(5, niveauGravite);
        req.setInt(6, refEtat);
        if (dateCloture != null) {
            req.setDate(7, new java.sql.Date(dateCloture.getTime()));
        } else {
            req.setNull(7, Types.DATE);
        }

        return req.executeUpdate() == 1;
    }

    public static boolean deleteDossiers(int idDossiers) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM dossiers WHERE id_dossiers = ?");
        req.setInt(1, idDossiers);

        return req.executeUpdate() == 1;
    }

    public static boolean updateDossiers(Dossiers dossier) throws SQLException {
        return updateDossiers(dossier.getId(), dossier.getRefPatients(), dossier.getRefUser(), dossier.getDateArrivee(), dossier.getSymptomes(), dossier.getNiveauGravite(), dossier.getRefEtat(), dossier.getDateCloture());
    }

    public static int getDossiersClose() throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT COUNT(id_dossiers) AS total FROM `dossiers` WHERE date_cloture IS NOT NULL";
        PreparedStatement req = cnx.prepareStatement(sql);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public static int getDossiersActive() throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT COUNT(id_dossiers) AS total FROM `dossiers` WHERE date_cloture IS NULL";
        PreparedStatement req = cnx.prepareStatement(sql);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public static int getDossiersByEtat(int id) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT COUNT(id_dossiers) AS total FROM `dossiers` WHERE ref_etat = ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        req.setInt(1, id);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public static int getDossiersByMonth(int month) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT COUNT(id_dossiers) AS total FROM `dossiers` WHERE MONTH(date_arrivee) = ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        req.setInt(1, month);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public static boolean updateDossiers(int idDossiers, int refPatients, int refUser, Date dateArrivee, String symptomes, int niveauGravite, int refEtat, Date dateCloture) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE dossiers SET ref_patients = ?, ref_user = ?, date_arrivee = ?, symptomes = ?, niveau_gravite = ?, ref_etat = ?, date_cloture = ? WHERE id_dossiers = ?");
        req.setInt(1, refPatients);
        req.setInt(2, refUser);
        req.setDate(3, new java.sql.Date(dateArrivee.getTime()));
        req.setString(4, symptomes);
        req.setInt(5, niveauGravite);
        req.setInt(6, refEtat);
        if (dateCloture != null) {
            req.setDate(7, new java.sql.Date(dateCloture.getTime()));
        } else {
            req.setNull(7, Types.DATE);
        }
        req.setInt(8, idDossiers);

        return req.executeUpdate() == 1;
    }

    public static List<Dossiers> getAllDossiers() throws SQLException {
        List<Dossiers> dossiers = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM dossiers");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            dossiers.add(new Dossiers(
                    rs.getInt("id_dossiers"),
                    rs.getInt("ref_patients"),
                    rs.getInt("ref_user"),
                    rs.getDate("date_arrivee"),
                    rs.getString("symptomes"),
                    rs.getInt("niveau_gravite"),
                    rs.getInt("ref_etat"),
                    rs.getDate("date_cloture")
            ));
        }

        return dossiers;
    }

    public static List<Dossiers> searchDossiers(String searchQuery) throws SQLException {
        List<Dossiers> dossiers = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        // Requête SQL pour rechercher des dossiers
        String sql = "SELECT * FROM dossiers " +
                "WHERE ref_patients IN (SELECT id_patients FROM patients WHERE nom LIKE ? OR prenom LIKE ?) " +
                "OR symptomes LIKE ? " +
                "OR niveau_gravite LIKE ? " +
                "OR ref_etat IN (SELECT id_etat FROM etat WHERE libelle LIKE ?)";

        PreparedStatement req = cnx.prepareStatement(sql);
        String likeQuery = "%" + searchQuery + "%";

        // Remplissage des paramètres pour la requête
        req.setString(1, likeQuery); // Recherche par nom du patient
        req.setString(2, likeQuery); // Recherche par prénom du patient
        req.setString(3, likeQuery); // Recherche par symptômes
        req.setString(4, likeQuery); // Recherche par niveau de gravité
        req.setString(5, likeQuery); // Recherche par état (libellé)

        ResultSet rs = req.executeQuery();

        // Parcours des résultats et ajout à la liste
        while (rs.next()) {
            dossiers.add(new Dossiers(
                    rs.getInt("id_dossiers"),
                    rs.getInt("ref_patients"),
                    rs.getInt("ref_user"),
                    rs.getDate("date_arrivee"),
                    rs.getString("symptomes"),
                    rs.getInt("niveau_gravite"),
                    rs.getInt("ref_etat"),
                    rs.getDate("date_cloture")
            ));
        }
        return dossiers;
    }

    public static Etats getEtatById(int refEtat) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        // Requête SQL pour récupérer l'état par ID
        String sql = "SELECT * FROM etat WHERE id_etat = ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        req.setInt(1, refEtat);

        ResultSet rs = req.executeQuery();

        // Si un état est trouvé, le retourner
        if (rs.next()) {
            return new Etats(
                    rs.getInt("id_etat"),
                    rs.getString("libelle")
            );
        }

        // Retourner null si aucun état n'est trouvé
        return null;
    }
}