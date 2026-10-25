package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public static String authentifier(String nomComplet, String password) {
        String sql = "SELECT role FROM utilisateur WHERE nom_complet = ? AND mot_de_passe_hash = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nomComplet);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification : " + e.getMessage());
        }
        return null;
    }

    public static List<String[]> listerUtilisateurs() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT id, nom_complet, email, role, actif FROM utilisateur ORDER BY id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] user = new String[5];
                user[0] = String.valueOf(rs.getInt("id"));
                user[1] = rs.getString("nom_complet");
                user[2] = rs.getString("email");
                user[3] = rs.getString("role");
                user[4] = rs.getBoolean("actif") ? "Actif" : "Inactif";
                
                liste.add(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            e.printStackTrace();
        }
        
        return liste;
    }
}