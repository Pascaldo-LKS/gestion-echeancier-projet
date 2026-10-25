package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AffectationDAO {

    public static boolean insererAffectation(String dateAffectation, boolean actif, int idUtilisateur, int idActivite) {
        String sql = "INSERT INTO affectation (date_affectation, actif, id_utilisateur, id_activite) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (dateAffectation != null && !dateAffectation.trim().isEmpty()) {
                pstmt.setDate(1, java.sql.Date.valueOf(dateAffectation));
            } else {
                pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }
            
            pstmt.setBoolean(2, actif);
            pstmt.setInt(3, idUtilisateur);
            pstmt.setInt(4, idActivite);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'affectation : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerAffectations() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT a.id, a.date_affectation, a.actif, u.nom_complet, ac.libelle AS activite_libelle " +
                     "FROM affectation a " +
                     "JOIN utilisateur u ON a.id_utilisateur = u.id " +
                     "JOIN activite ac ON a.id_activite = ac.id " +
                     "ORDER BY a.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] aff = new String[5];
                aff[0] = rs.getDate("date_affectation") != null ? rs.getDate("date_affectation").toString() : "";
                aff[1] = rs.getBoolean("actif") ? "Actif" : "Inactif";
                aff[2] = rs.getString("nom_complet");
                aff[3] = rs.getString("activite_libelle");
                
                liste.add(aff);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des affectations : " + e.getMessage());
            e.printStackTrace();
        }
        
        return liste;
    }
}