package repository;

import database.Database;
import model.Chambres;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambresRepository {

    private static Connection getConnection() throws SQLException {
        Database db = new Database();
        return db.getConnection();
    }

    public static boolean addChambre(Chambres chambres) throws SQLException {
        return addChambre(chambres.getNum(), chambres.getType(), chambres.getCapacite(), chambres.getStatus(), chambres.getLitOccupe());
    }

    public static boolean addChambre(String num, String type, int capacite, String status, int litOccupe) throws SQLException {
        try (Connection cnx = getConnection()) {
            PreparedStatement req = cnx.prepareStatement("INSERT INTO chambres (num, type, capacite, status, litoccupe) VALUES (?, ?, ?, ?, ?)");
            req.setString(1, num);
            req.setString(2, type);
            req.setInt(3, capacite);
            req.setString(4, status);
            req.setInt(5, litOccupe);

            return req.executeUpdate() == 1;
        }
    }

    public static boolean deleteChambre(int idChambre) throws SQLException {
        try (Connection cnx = getConnection()) {
            PreparedStatement req = cnx.prepareStatement("DELETE FROM chambres WHERE id_chambres = ?");
            req.setInt(1, idChambre);

            return req.executeUpdate() == 1;
        }
    }

    public static boolean updateChambre(Chambres chambres) throws SQLException {
        return updateChambre(chambres.getId(), chambres.getNum(), chambres.getType(), chambres.getCapacite(), chambres.getStatus(), chambres.getLitOccupe());
    }

    public static boolean updateChambre(int idChambre, String num, String type, int capacite, String status, int litoccupe) throws SQLException {
        try (Connection cnx = getConnection()) {
            PreparedStatement req = cnx.prepareStatement("UPDATE chambres SET num = ?, type = ?, capacite = ?, status = ?, litoccupe = ? WHERE id_chambres = ?");
            req.setString(1, num);
            req.setString(2, type);
            req.setInt(3, capacite);
            req.setString(4, status);
            req.setInt(5, litoccupe);
            req.setInt(6, idChambre);

            return req.executeUpdate() == 1;
        }
    }

    public static List<Chambres> getAllChambres() throws SQLException {
        List<Chambres> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres";

        try (Connection cnx = getConnection();
             Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Chambres chambre = new Chambres(
                        rs.getInt("id_chambres"),
                        rs.getString("num"),
                        rs.getString("type"),
                        rs.getInt("capacite"),
                        rs.getString("status"),
                        rs.getInt("litoccupe")
                );
                chambres.add(chambre);
            }
        }
        return chambres;
    }

    public static List<Chambres> searchChambres(String searchQuery, String type, String statusFilter) throws SQLException {
        List<Chambres> chambres = new ArrayList<>();

        String sql = "SELECT * FROM chambres";

        try (Connection cnx = getConnection();
             PreparedStatement req = cnx.prepareStatement(sql);
             ResultSet rs = req.executeQuery()) {

            while (rs.next()) {
                Chambres chambre = new Chambres(
                        rs.getInt("id_chambres"),
                        rs.getString("num"),
                        rs.getString("type"),
                        rs.getInt("capacite"),
                        rs.getString("status"),
                        rs.getInt("litoccupe")
                );
                chambres.add(chambre);
            }
        }

        List<Chambres> filteredChambres = new ArrayList<>();

        for (Chambres chambre : chambres) {
            boolean matches = true;

            if (searchQuery != null && !searchQuery.isEmpty()) {
                if (!chambre.getNum().toLowerCase().contains(searchQuery.toLowerCase())) {
                    matches = false;
                }
            }

            if (type != null && !type.isEmpty()) {
                if (!chambre.getType().toLowerCase().contains(type.toLowerCase())) {
                    matches = false;
                }
            }

            if (statusFilter != null && !statusFilter.isEmpty()) {
                if (!chambre.getStatus().toLowerCase().contains(statusFilter.toLowerCase())) {
                    matches = false;
                }
            }

            if (matches) {
                filteredChambres.add(chambre);
            }
        }

        return filteredChambres;
    }
}
