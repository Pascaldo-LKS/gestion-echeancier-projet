package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuiviPhysiqueDAO {

    public static boolean insererSuivi(int activiteId, String dateSuivi, double tauxAvancement, Double quantitePrevue, Double quantiteRealisee, String statutValidation, String description, int idAuteur) {
        String sql = "INSERT INTO suivi_physique (id_activite, date_constat, taux_avancement, quantite_prevue, quantite_realisee, statut_validation, commentaire, id_auteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, activiteId);
            
            if (dateSuivi != null && !dateSuivi.trim().isEmpty()) {
                pstmt.setDate(2, java.sql.Date.valueOf(dateSuivi));
            } else {
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            }
            
            pstmt.setDouble(3, tauxAvancement);
            
            if (quantitePrevue != null) {
                pstmt.setDouble(4, quantitePrevue);
            } else {
                pstmt.setNull(4, java.sql.Types.NUMERIC);
            }
            
            if (quantiteRealisee != null) {
                pstmt.setDouble(5, quantiteRealisee);
            } else {
                pstmt.setNull(5, java.sql.Types.NUMERIC);
            }
            
            pstmt.setString(6, statutValidation);
            pstmt.setString(7, description);
            pstmt.setInt(8, idAuteur);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du suivi physique : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerSuivis() {
        List<String[]> listeSuivis = new ArrayList<>();
        String sql = "SELECT s.id, a.id AS activite_id, a.libelle AS activite_libelle, s.date_constat, s.taux_avancement, s.quantite_prevue, s.quantite_realisee, s.statut_validation, s.commentaire " +
                     "FROM suivi_physique s JOIN activite a ON s.id_activite = a.id ORDER BY s.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] suivi = new String[8];
                suivi[0] = String.valueOf(rs.getInt("activite_id"));
                suivi[1] = rs.getString("activite_libelle");
                suivi[2] = rs.getDate("date_constat") != null ? rs.getDate("date_constat").toString() : "";
                suivi[3] = String.valueOf(rs.getDouble("taux_avancement")) + " %";
                suivi[4] = rs.getObject("quantite_prevue") != null ? String.valueOf(rs.getDouble("quantite_prevue")) : "-";
                suivi[5] = rs.getObject("quantite_realisee") != null ? String.valueOf(rs.getDouble("quantite_realisee")) : "-";
                suivi[6] = rs.getString("statut_validation") != null ? rs.getString("statut_validation") : "En attente";
                suivi[7] = rs.getString("commentaire") != null ? rs.getString("commentaire") : "";
                
                listeSuivis.add(suivi);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des suivis physiques : " + e.getMessage());
            e.printStackTrace();
        }
        
        return listeSuivis;
    }
}