package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepenseDAO {

    public static boolean insererDepense(String dateDepense, double montant, String reference, String categorie, int idActivite) {
        String sql = "INSERT INTO depense (date_depense, montant, reference, categorie, statut, id_activite, id_saisie) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (dateDepense != null && !dateDepense.trim().isEmpty()) {
                pstmt.setDate(1, java.sql.Date.valueOf(dateDepense));
            } else {
                pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }
            
            pstmt.setDouble(2, montant);
            pstmt.setString(3, reference != null && !reference.trim().isEmpty() ? reference : "REF-" + System.currentTimeMillis());
            pstmt.setString(4, categorie != null && !categorie.trim().isEmpty() ? categorie : "Générale");
            pstmt.setString(5, "Validée"); // Statut par défaut
            pstmt.setInt(6, idActivite);
            pstmt.setInt(7, 1); // ID de saisie / auteur par défaut
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la dépense : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> listerDepenses() {
        List<String[]> liste = new ArrayList<>();
        String sql = "SELECT d.id, d.date_depense, d.montant, d.reference, d.categorie, d.statut, a.libelle AS activite_libelle " +
                     "FROM depense d LEFT JOIN activite a ON d.id_activite = a.id ORDER BY d.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String[] dep = new String[7];
                dep[0] = String.valueOf(rs.getInt("id"));
                dep[1] = rs.getDate("date_depense") != null ? rs.getDate("date_depense").toString() : "";
                dep[2] = String.valueOf(rs.getDouble("montant")) + " FCFA";
                dep[3] = rs.getString("reference");
                dep[4] = rs.getString("categorie");
                dep[5] = rs.getString("statut");
                dep[6] = rs.getString("activite_libelle") != null ? rs.getString("activite_libelle") : "Aucune";
                
                liste.add(dep);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des dépenses : " + e.getMessage());
            e.printStackTrace();
        }
        
        return liste;
    }
}