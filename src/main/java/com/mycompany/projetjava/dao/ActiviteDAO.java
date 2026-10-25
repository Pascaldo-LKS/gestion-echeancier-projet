package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiviteDAO {

    public static boolean insererActivite(String code, String libelle, String dateDebutPrevue, String dateFinPrevue, double budgetPrevu, double poidsAvancement, String statut, int projetId) {
        String sql = "INSERT INTO activite (code, libelle, date_debut_prevue, date_fin_prevue, budget_prevu, poids_avancement, statut, id_projet, projet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            pstmt.setString(2, libelle);
            if (dateDebutPrevue != null && !dateDebutPrevue.trim().isEmpty()) {
                pstmt.setDate(3, java.sql.Date.valueOf(dateDebutPrevue));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }
            if (dateFinPrevue != null && !dateFinPrevue.trim().isEmpty()) {
                pstmt.setDate(4, java.sql.Date.valueOf(dateFinPrevue));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setDouble(5, budgetPrevu);
            pstmt.setDouble(6, poidsAvancement);
            pstmt.setString(7, statut);
            pstmt.setInt(8, projetId);
            pstmt.setInt(9, projetId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'activité : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerActivites() {
        List<String[]> listeActivites = new ArrayList<>();
        String sql = "SELECT a.id, a.code, a.libelle, a.date_debut_prevue, a.date_fin_prevue, a.budget_prevu, a.poids_avancement, a.statut, p.titre AS projet_titre " +
                     "FROM activite a LEFT JOIN projet p ON a.id_projet = p.id ORDER BY a.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] activite = new String[9];
                activite[0] = String.valueOf(rs.getInt("id"));
                activite[1] = rs.getString("code");
                activite[2] = rs.getString("libelle");
                activite[3] = rs.getDate("date_debut_prevue") != null ? rs.getDate("date_debut_prevue").toString() : "";
                activite[4] = rs.getDate("date_fin_prevue") != null ? rs.getDate("date_fin_prevue").toString() : "";
                activite[5] = String.valueOf(rs.getDouble("budget_prevu"));
                activite[6] = String.valueOf(rs.getDouble("poids_avancement"));
                activite[7] = rs.getString("statut");
                activite[8] = rs.getString("projet_titre") != null ? rs.getString("projet_titre") : "Aucun";
                
                listeActivites.add(activite);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des activités : " + e.getMessage());
            e.printStackTrace();
        }
        
        return listeActivites;
    }
}