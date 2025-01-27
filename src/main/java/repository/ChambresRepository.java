package repository;

import database.Database;
import model.Chambres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ChambresRepository {
    public static boolean addChambre(String num, boolean dispo, Date dateDebut, Date dateFin, int refDossiers) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO chambres (num, dispo, date_debut, date_fin, ref_dossiers) VALUES (?, ?, ?, ?, ?)");
        req.setString(1, num);
        req.setBoolean(2, dispo);
        req.setDate(3, new Date(dateDebut.getTime()));
        req.setDate(4, new Date(dateFin.getTime()));
        req.setInt(5, refDossiers);

        return req.executeUpdate() == 1;
    }

    public static boolean deleteChambre(int idChambre) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM chambres WHERE id_chambres = ?");
        req.setInt(1, idChambre);

        return req.executeUpdate() == 1;
    }

    public static boolean updateChambre(int idChambre, String num, boolean dispo, Date dateDebut, Date dateFin, int refDossiers) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE chambres SET num = ?, dispo = ?, date_debut = ?, date_fin = ?, ref_dossiers = ? WHERE id_chambres = ?");
        req.setString(1, num);
        req.setBoolean(2, dispo);
        req.setDate(3, new Date(dateDebut.getTime()));
        req.setDate(4, new Date(dateFin.getTime()));
        req.setInt(5, refDossiers);
        req.setInt(6, idChambre);

        return req.executeUpdate() == 1;
    }

    public static List<Chambres> getAllChambres() throws SQLException {
        List<Chambres> chambres = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM chambres");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            chambres.add(new Chambres(
                    rs.getInt("id_chambres"),
                    rs.getString("num"),
                    rs.getBoolean("dispo"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getInt("ref_dossiers")
            ));
        }

        return chambres;
    }
}