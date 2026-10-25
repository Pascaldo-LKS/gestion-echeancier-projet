package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlerteDAO {

    public static boolean insererAlerte(String type, String message, String statut) {
        String sql = "INSERT INTO alerte (type, message, date_creation, statut) VALUES (?, ?, NOW(), ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, type);
            pstmt.setString(2, message);
            pstmt.setString(3, statut);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'alerte : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerAlertes() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT id, type, message, date_creation, statut FROM alerte ORDER BY id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] alt = new String[5];
                alt[0] = rs.getString("type");
                alt[1] = rs.getString("message");
                alt[2] = rs.getTimestamp("date_creation") != null ? rs.getTimestamp("date_creation").toString() : "";
                alt[3] = rs.getString("statut");
                
                liste.add(alt);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des alertes : " + e.getMessage());
            e.printStackTrace();
        }
        
        return liste;
    }
}