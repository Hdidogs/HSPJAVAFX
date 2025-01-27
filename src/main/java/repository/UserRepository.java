package repository;

import database.Database;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static boolean addUser(User user) throws SQLException {
        return addUser(user.getNom(), user.getPrenom(), user.getMail(), user.getMotDePasse(), user.getRefRole());
    }

    public static boolean addUser(String nom, String prenom, String mail, String motDePasse, int refRole) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("INSERT INTO user (nom, prenom, mail, mdp, ref_role) VALUES (?, ?, ?, ?, ?)");
        req.setString(1, nom);
        req.setString(2, prenom);
        req.setString(3, mail);
        req.setString(4, motDePasse);
        req.setInt(5, refRole);

        return req.executeUpdate() == 1;
    }

    public static boolean deleteUser(int idUser) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("DELETE FROM user WHERE id_user = ?");
        req.setInt(1, idUser);

        return req.executeUpdate() == 1;
    }

    public static boolean updateUser(User user) throws SQLException {
        return updateUser(user.getId(), user.getNom(), user.getPrenom(), user.getMail(), user.getMotDePasse(), user.getRefRole());
    }

    public static boolean updateUser(int idUser, String nom, String prenom, String mail, String motDePasse, int refRole) throws SQLException {
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("UPDATE user SET nom = ?, prenom = ?, mail = ?, mdp = ?, ref_role = ? WHERE id_user = ?");
        req.setString(1, nom);
        req.setString(2, prenom);
        req.setString(3, mail);
        req.setString(4, motDePasse);
        req.setInt(5, refRole);
        req.setInt(6, idUser);

        return req.executeUpdate() == 1;
    }

    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM user");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            users.add(new User(
                    rs.getInt("id_user"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("mail"),
                    rs.getString("mdp"),
                    rs.getInt("ref_role"),
                    rs.getDate("date_creation")
            ));
        }

        return users;
    }
}
