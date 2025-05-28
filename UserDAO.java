package DAO;

import db.DBConnection;
import model.User;
import java.sql.*;

public class UserDAO {
    public User validateCardLogin(String cardNumber, String pin) {
        String sql = "SELECT * FROM users WHERE card_number=? AND pin=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cardNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User validateCardlessLogin(String accNo, String ePin) {
        String sql = "SELECT * FROM users WHERE account_number=? AND ebanking_pin=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNo);
            stmt.setString(2, ePin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("card_number"),
                rs.getString("pin"),
                rs.getString("account_number"),
                rs.getString("ebanking_pin"),
                rs.getString("name"),
                rs.getDouble("balance")
        );
    }
    public boolean updateBalance(String cardNumber, double newBalance) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance=? WHERE card_number=?")) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
