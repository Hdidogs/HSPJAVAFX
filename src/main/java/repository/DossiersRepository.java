package repository;

import database.Database;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Repository for Dossiers
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
}