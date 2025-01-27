package repository;

import database.Database;
import model.Etats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtatsRepository {

    public static List<Etats> getAlletats() throws SQLException {
        List<Etats> etats = new ArrayList<>();
        Database db = new Database();
        Connection cnx = db.getConnection();

        PreparedStatement req = cnx.prepareStatement("SELECT * FROM etats");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            etats.add(new Etats(
                    rs.getInt("id_etats"),
                    rs.getString("libelle")
            ));
        }

        return etats;
    }
}
