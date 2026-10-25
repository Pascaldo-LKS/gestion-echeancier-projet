package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {

    public static boolean insererProjet(String code, String titre, String dateDebut, String dateFin, double budgetPrevu, String statut) {
        String sql = "INSERT INTO projet (code, titre, date_debut_prevue, date_fin_prevue, budget_prevu, statut) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            pstmt.setString(2, titre);
            
            if (dateDebut != null && !dateDebut.trim().isEmpty()) {
                pstmt.setDate(3, java.sql.Date.valueOf(dateDebut));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }
            
            if (dateFin != null && !dateFin.trim().isEmpty()) {
                pstmt.setDate(4, java.sql.Date.valueOf(dateFin));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            
            pstmt.setDouble(5, budgetPrevu);
            pstmt.setString(6, statut);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du projet : " + e.getMessage());
            return false;
        }
    }

    public static List<String[]> listerProjets() {
        List<String[]> listeProjets = new ArrayList<>();
        String sql = "SELECT id, code, titre, date_debut_prevue, date_fin_prevue, budget_prevu, statut FROM projet ORDER BY id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] projet = new String[7];
                projet[0] = String.valueOf(rs.getInt("id"));
                projet[1] = rs.getString("code");
                projet[2] = rs.getString("titre");
                projet[3] = rs.getDate("date_debut_prevue") != null ? rs.getDate("date_debut_prevue").toString() : "";
                projet[4] = rs.getDate("date_fin_prevue") != null ? rs.getDate("date_fin_prevue").toString() : "";
                projet[5] = String.valueOf(rs.getDouble("budget_prevu"));
                projet[6] = rs.getString("statut");
                
                listeProjets.add(projet);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la liste des projets : " + e.getMessage());
        }
        
        return listeProjets;
    }

    public static boolean modifierProjet(int id, String titre, String dateDebut, String dateFin, double budgetPrevu, String statut) {
        String sql = "UPDATE projet SET titre = ?, date_debut_prevue = ?, date_fin_prevue = ?, budget_prevu = ?, statut = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, titre);
            
            if (dateDebut != null && !dateDebut.trim().isEmpty()) {
                pstmt.setDate(2, java.sql.Date.valueOf(dateDebut));
            } else {
                pstmt.setNull(2, java.sql.Types.DATE);
            }
            
            if (dateFin != null && !dateFin.trim().isEmpty()) {
                pstmt.setDate(3, java.sql.Date.valueOf(dateFin));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }
            
            pstmt.setDouble(4, budgetPrevu);
            pstmt.setString(5, statut);
            pstmt.setInt(6, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du projet : " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerProjet(int id) {
        String sql = "DELETE FROM projet WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
            return false;
        }
    }
}