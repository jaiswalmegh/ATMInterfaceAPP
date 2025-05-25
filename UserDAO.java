package dao;

import model.User;
import db.DBConnection;

import java.sql.*;

public class UserDAO {

    public User validateUser(String cardNumber, String pin) {
        String query = "SELECT * FROM users WHERE card_number=? AND pin=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cardNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("card_number"), rs.getString("pin"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(String cardNumber, double newBalance) {
        String query = "UPDATE users SET balance=? WHERE card_number=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
