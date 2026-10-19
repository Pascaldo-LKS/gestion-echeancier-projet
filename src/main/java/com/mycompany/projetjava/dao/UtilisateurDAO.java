package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {

    // Méthode pour authentifier un utilisateur et retourner son rôle
    public static String authentifier(String nomComplet, String password) {
        // Utilisation des exacts noms de colonnes de la base de données
        String sql = "SELECT role FROM utilisateur WHERE nom_complet = ? AND mot_de_passe_hash = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nomComplet);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // Retourne le rôle (ex: admin)
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification : " + e.getMessage());
        }
        return null; // Identifiants incorrects ou utilisateur non trouvé
    }
}