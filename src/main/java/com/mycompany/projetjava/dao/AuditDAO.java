package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditDAO {

    // Méthode pour enregistrer une action dans le journal d'audit
    public static boolean enregistrerAction(String utilisateur, String action, String description) {
        String sql = "INSERT INTO journal_audit (utilisateur, action, description, date_action) VALUES (?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, utilisateur);
            pstmt.setString(2, action);
            pstmt.setString(3, description);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'audit : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour récupérer la liste des actions enregistrées
    public static List<String[]> listerAudit() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT id, utilisateur, action, description, date_action FROM journal_audit ORDER BY id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] log = new String[5];
                log[0] = String.valueOf(rs.getInt("id"));
                log[1] = rs.getString("utilisateur");
                log[2] = rs.getString("action");
                log[3] = rs.getString("description");
                log[4] = rs.getTimestamp("date_action").toString();
                
                liste.add(log);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du journal d'audit : " + e.getMessage());
        }
        
        return liste;
    }
}