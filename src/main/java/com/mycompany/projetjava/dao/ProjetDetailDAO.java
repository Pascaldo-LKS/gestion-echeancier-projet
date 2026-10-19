package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProjetDetailDAO {

    public static Map<String, Object> getIndicateursProjet(int projetId) {
        Map<String, Object> data = new HashMap<>();
        
        String sqlBudget = "SELECT titre, budget_prevu FROM projet WHERE id = ?";
        String sqlDepenses = "SELECT COALESCE(SUM(d.montant), 0) AS total_dep " +
                             "FROM depense d JOIN activite a ON d.id_activite = a.id WHERE a.id_projet = ?";
        String sqlAvancement = "SELECT COALESCE(AVG(s.taux_avancement), 0) AS avg_avancement " +
                               "FROM suivi_physique s JOIN activite a ON s.id_activite = a.id WHERE a.id_projet = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // 1. Infos du projet
            try (PreparedStatement ps = conn.prepareStatement(sqlBudget)) {
                ps.setInt(1, projetId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        data.put("titreProjet", rs.getString("titre"));
                        data.put("budgetPrevu", rs.getDouble("budget_prevu"));
                    }
                }
            }
            
            // 2. Dépenses réelles du projet
            try (PreparedStatement ps = conn.prepareStatement(sqlDepenses)) {
                ps.setInt(1, projetId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        data.put("totalDepenses", rs.getDouble("total_dep"));
                    }
                }
            }
            
            // 3. Avancement physique moyen du projet
            try (PreparedStatement ps = conn.prepareStatement(sqlAvancement)) {
                ps.setInt(1, projetId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        data.put("avancementMoyen", Math.round(rs.getDouble("avg_avancement") * 10.0) / 10.0);
                    }
                }
            }
            
            // 4. Calculs des écarts et alertes automatiques
            double budget = (double) data.getOrDefault("budgetPrevu", 0.0);
            double depenses = (double) data.getOrDefault("totalDepenses", 0.0);
            double ecart = budget - depenses;
            
            data.put("ecartBudget", ecart);
            
            // Gestion des alertes et failles
            if (depenses > budget) {
                data.put("alerteType", "danger");
                data.put("alerteMessage", "🚨 ALERTE : Dépassement budgétaire détecté ! (" + Math.abs(ecart) + " FCFA de dépassement)");
            } else if (depenses >= (budget * 0.85)) {
                data.put("alerteType", "warning");
                data.put("alerteMessage", "⚠️ ATTENTION : Le budget est consommé à plus de 85%.");
            } else {
                data.put("alerteType", "success");
                data.put("alerteMessage", "✅ Statut financier sain : Budget maîtrisé.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return data;
    }
}