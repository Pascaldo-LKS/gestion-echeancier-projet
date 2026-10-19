package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiviteDAO {

   public static boolean insererActivite(String code, String libelle, double budgetPrevu, int projetId) {
        // On renseigne id_projet, projet_id et poids_avancement pour respecter toutes les contraintes NOT NULL
        String sql = "INSERT INTO activite (code, libelle, budget_prevu, id_projet, projet_id, poids_avancement, statut) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            pstmt.setString(2, libelle);
            pstmt.setDouble(3, budgetPrevu);
            pstmt.setInt(4, projetId);
            pstmt.setInt(5, projetId);
            pstmt.setDouble(6, 0.0); // Poids d'avancement initial par défaut
            pstmt.setString(7, "Planifié"); // Statut par défaut
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'activité : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    } // Méthode pour lister toutes les activités avec le titre du projet associé
    public static List<String[]> listerActivites() {
        List<String[]> listeActivites = new ArrayList<>();
        String sql = "SELECT a.id, a.code, a.libelle, a.budget_prevu, p.titre AS projet_titre " +
                     "FROM activite a LEFT JOIN projet p ON a.id_projet = p.id ORDER BY a.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] activite = new String[5];
                activite[0] = String.valueOf(rs.getInt("id"));
                activite[1] = rs.getString("code");
                activite[2] = rs.getString("libelle");
                activite[3] = String.valueOf(rs.getDouble("budget_prevu"));
                activite[4] = rs.getString("projet_titre") != null ? rs.getString("projet_titre") : "Aucun";
                
                listeActivites.add(activite);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des activités : " + e.getMessage());
            e.printStackTrace();
        }
        
        return listeActivites;
    }
}