package repositories;

import config.DatabaseConfig;
import entities.Suit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SuitRepositoryImpl implements SuitRepository {
    @Override
    public List<Suit> getAllSuits() {
        List<Suit> suits = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM suits";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                Suit suit = new Suit();
                suit.setId(rs.getInt("id"));
                suit.setColor(rs.getString("color"));
                suit.setSize(rs.getString("size"));
                suit.setStock(rs.getInt("stock"));
                suit.setPricePerDay(rs.getDouble("price_per_day"));
                suits.add(suit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suits;
    }

    @Override
    public void updateSuit(Suit suit) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE suits SET color = ?, size = ?, stock = ?, price_per_day = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, suit.getColor());
            ps.setString(2, suit.getSize());
            ps.setInt(3, suit.getStock());
            ps.setDouble(4, suit.getPricePerDay());
            ps.setInt(5, suit.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Suit getSuitById(int id) {
        Suit suit = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM suits WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                suit = new Suit();
                suit.setId(rs.getInt("id"));
                suit.setColor(rs.getString("color"));
                suit.setSize(rs.getString("size"));
                suit.setStock(rs.getInt("stock"));
                suit.setPricePerDay(rs.getDouble("price_per_day"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suit;
    }
}
