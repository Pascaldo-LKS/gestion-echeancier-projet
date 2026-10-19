package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurDAO {

    // Méthode pour lister tous les utilisateurs
    public static List<String[]> listerUtilisateurs() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT id, nom_complet, email, role, actif FROM utilisateur ORDER BY id ASC";
        
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
        }
        return liste;
    }

    // Méthode pour ajouter un nouvel utilisateur
    public static boolean ajouterUtilisateur(String nomComplet, String email, String password, String role) {
        String sql = "INSERT INTO utilisateur (nom_complet, email, mot_de_passe_hash, role, actif) VALUES (?, ?, ?, ?, true)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nomComplet);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // En production, il faudrait hacher le mot de passe (ex: BCrypt)
            pstmt.setString(4, role);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour supprimer un utilisateur
    public static boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return false;
        }
    }
}