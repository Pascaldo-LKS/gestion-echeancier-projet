package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuiviPhysiqueDAO {

    // Méthode pour insérer un suivi physique alignée sur les colonnes exactes de la base
    public static boolean insererSuivi(int activiteId, String dateSuivi, double tauxAvancement, String description) {
        String sql = "INSERT INTO suivi_physique (id_activite, date_constat, taux_avancement, commentaire) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, activiteId);
            
            if (dateSuivi != null && !dateSuivi.trim().isEmpty()) {
                pstmt.setDate(2, java.sql.Date.valueOf(dateSuivi));
            } else {
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            }
            
            pstmt.setDouble(3, tauxAvancement);
            pstmt.setString(4, description);
            
            int lignesAffectees = pstmt.executeUpdate();
            return lignesAffectees > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du suivi physique : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour lister tous les suivis physiques enregistrés
    public static List<String[]> listerSuivis() {
        List<String[]> listeSuivis = new ArrayList<>();
        String sql = "SELECT s.id, a.libelle AS activite_libelle, s.date_constat, s.taux_avancement, s.commentaire " +
                     "FROM suivi_physique s JOIN activite a ON s.id_activite = a.id ORDER BY s.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] suivi = new String[5];
                suivi[0] = String.valueOf(rs.getInt("id"));
                suivi[1] = rs.getString("activite_libelle");
                suivi[2] = rs.getDate("date_constat") != null ? rs.getDate("date_constat").toString() : "";
                suivi[3] = String.valueOf(rs.getDouble("taux_avancement")) + " %";
                suivi[4] = rs.getString("commentaire") != null ? rs.getString("commentaire") : "";
                
                listeSuivis.add(suivi);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des suivis physiques : " + e.getMessage());
            e.printStackTrace();
        }
        
        return listeSuivis;
    }
}