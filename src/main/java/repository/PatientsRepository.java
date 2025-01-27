package repository;

import database.Database;
import model.Patients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientsRepository {
    public static boolean addPatient(Patients patients) throws SQLException {
        return addPatient(patients.getNom(), patients.getPrenom(), patients.getNumSecu(), patients.getMail(), patients.getTel(), patients.getRue(), patients.getVille(), patients.getCp());
    }

    public static boolean addPatient(String nom, String prenom, String numSecu, String mail, String tel, String rue, String ville, int cp) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO patients (nom, prenom, num_secu, mail, tel, rue, ville, cp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        req.setString(1, nom);
        req.setString(2, prenom);
        req.setString(3, numSecu);
        req.setString(4, mail);
        req.setString(5, tel);
        req.setString(6, rue);
        req.setString(7, ville);
        req.setInt(8, cp);

        return req.executeUpdate() == 1;
    }

    public static boolean deletePatient(int idPatient) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM patients WHERE id_patients = ?");
        req.setInt(1, idPatient);

        return req.executeUpdate() == 1;
    }

    public static boolean updatePatient(Patients patients) throws SQLException {
        return updatePatient(patients.getId(), patients.getNom(), patients.getPrenom(), patients.getNumSecu(), patients.getMail(), patients.getTel(), patients.getRue(), patients.getVille(), patients.getCp());
    }

    public static boolean updatePatient(int idPatient, String nom, String prenom, String numSecu, String mail, String tel, String rue, String ville, int cp) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE patients SET nom = ?, prenom = ?, num_secu = ?, mail = ?, tel = ?, rue = ?, ville = ?, cp = ? WHERE id_patients = ?");
        req.setString(1, nom);
        req.setString(2, prenom);
        req.setString(3, numSecu);
        req.setString(4, mail);
        req.setString(5, tel);
        req.setString(6, rue);
        req.setString(7, ville);
        req.setInt(8, cp);
        req.setInt(9, idPatient);

        return req.executeUpdate() == 1;
    }

    public static List<Patients> getAllPatients() throws SQLException {
        List<Patients> patients = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM patients");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            patients.add(new Patients(
                    rs.getInt("id_patients"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("num_secu"),
                    rs.getString("mail"),
                    rs.getString("tel"),
                    rs.getString("rue"),
                    rs.getString("ville"),
                    rs.getInt("cp")
            ));
        }

        return patients;
    }

    public static Patients getPatientById(int refPatients) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT * FROM patients WHERE id_patients = ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        req.setInt(1, refPatients);

        ResultSet rs = req.executeQuery();

        if (rs.next()) {
            return new Patients(
                    rs.getInt("id_patients"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("num_secu"),
                    rs.getString("mail"),
                    rs.getString("tel"),
                    rs.getString("rue"),
                    rs.getString("ville"),
                    rs.getInt("cp")
            );
        }

        return null;
    }

    public static List<Patients> searchPatients(String searchQuery) throws SQLException {
        List<Patients> patients = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        String sql = "SELECT * FROM patients " +
                "WHERE nom LIKE ? OR prenom LIKE ? OR num_secu LIKE ? OR mail LIKE ? OR tel LIKE ? OR rue LIKE ?";
        PreparedStatement req = cnx.prepareStatement(sql);
        String likeQuery = "%" + searchQuery + "%";

        req.setString(1, likeQuery);
        req.setString(2, likeQuery);
        req.setString(3, likeQuery);
        req.setString(4, likeQuery);
        req.setString(5, likeQuery);
        req.setString(6, likeQuery);
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            patients.add(new Patients(
                    rs.getInt("id_patients"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("num_secu"),
                    rs.getString("mail"),
                    rs.getString("tel"),
                    rs.getString("rue"),
                    rs.getString("ville"),
                    rs.getInt("cp")
            ));
        }

        return patients;
    }
}

