package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAO {

    // Récupérer les métriques et calculs globaux
    public static Map<String, Object> getStatistiquesGlobales() {
        Map<String, Object> stats = new HashMap<>();
        
        String sqlProjets = "SELECT COUNT(*) AS total FROM projet";
        String sqlActivites = "SELECT COUNT(*) AS total, COALESCE(SUM(budget_prevu), 0) AS total_budget FROM activite";
        String sqlDepenses = "SELECT COUNT(*) AS total, COALESCE(SUM(montant), 0) AS total_depenses FROM depense";
        String sqlAvancement = "SELECT COALESCE(AVG(taux_avancement), 0) AS avancement_moyen FROM suivi_physique";

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // 1. Total Projets
            try (PreparedStatement ps = conn.prepareStatement(sqlProjets); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stats.put("totalProjets", rs.getInt("total"));
            }
            
            // 2. Total Activités & Budget Prévu
            try (PreparedStatement ps = conn.prepareStatement(sqlActivites); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalActivites", rs.getInt("total"));
                    stats.put("totalBudget", rs.getDouble("total_budget"));
                }
            }
            
            // 3. Total Dépenses
            try (PreparedStatement ps = conn.prepareStatement(sqlDepenses); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalDepenses", rs.getDouble("total_depenses"));
                }
            }
            
            // 4. Avancement Moyen Global
            try (PreparedStatement ps = conn.prepareStatement(sqlAvancement); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.put("avancementMoyen", Math.round(rs.getDouble("avancement_moyen") * 10.0) / 10.0);
                }
            }
            
            // Calcul de l'écart budgétaire
            double totalBudget = (double) stats.getOrDefault("totalBudget", 0.0);
            double totalDepenses = (double) stats.getOrDefault("totalDepenses", 0.0);
            stats.put("ecartBudget", totalBudget - totalDepenses);

        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul des statistiques : " + e.getMessage());
            e.printStackTrace();
        }
        
        return stats;
    }

    // Récupérer les 5 dernières dépenses pour le tableau de bord
    public static List<String[]> getDernieresDepenses() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT d.date_depense, d.montant, d.reference, a.libelle AS activite " +
                     "FROM depense d LEFT JOIN activite a ON d.id_activite = a.id ORDER BY d.id DESC LIMIT 5";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String[] item = new String[4];
                item[0] = rs.getDate("date_depense") != null ? rs.getDate("date_depense").toString() : "";
                item[1] = String.valueOf(rs.getDouble("montant"));
                item[2] = rs.getString("reference");
                item[3] = rs.getString("activite") != null ? rs.getString("activite") : "N/A";
                liste.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
}