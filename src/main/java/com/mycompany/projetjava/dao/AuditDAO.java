package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditDAO {

    public static boolean enregistrerAction(String action, String typeObjet, int idObjet, int idActeur) {
        String sql = "INSERT INTO journal_audit (action, type_objet, id_objet, date_action, id_acteur) VALUES (?, ?, ?, NOW(), ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, action);
            pstmt.setString(2, typeObjet);
            pstmt.setInt(3, idObjet);
            pstmt.setInt(4, idActeur);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'audit : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerAudit() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT j.id, j.action, j.type_objet, j.date_action, u.nom_complet FROM journal_audit j LEFT JOIN utilisateur u ON j.id_acteur = u.id ORDER BY j.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] log = new String[5];
                log[0] = String.valueOf(rs.getInt("id"));
                log[1] = rs.getString("action");
                log[2] = rs.getString("type_objet");
                log[3] = rs.getTimestamp("date_action").toString();
                log[4] = rs.getString("nom_complet") != null ? rs.getString("nom_complet") : "Administrateur";
                
                liste.add(log);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du journal d'audit : " + e.getMessage());
        }
        
        return liste;
    }
}