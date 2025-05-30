package DAO;

import db.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public User validateCardLogin(String cardNumber, String pin) {
        String sql = "SELECT * FROM users WHERE card_number = ? AND pin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cardNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User validateCardlessLogin(String accountNumber, String ebankingPin) {
        String sql = "SELECT * FROM users WHERE account_number = ? AND ebanking_pin = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, ebankingPin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBalance(String cardNumber, double newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE card_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, cardNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
}
