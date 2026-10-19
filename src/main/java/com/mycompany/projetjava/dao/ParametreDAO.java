package com.mycompany.projetjava.dao;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ParametreDAO {

    // Récupérer tous les paramètres sous forme de Map (Clé -> Valeur)
    public static Map<String, String> obtenirTousLesParametres() {
        Map<String, String> parametres = new HashMap<>();
        String sql = "SELECT cle, valeur FROM parametre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                parametres.put(rs.getString("cle"), rs.getString("valeur"));
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des paramètres : " + e.getMessage());
        }
        return parametres;
    }

    // Enregistrer ou mettre à jour un paramètre
    public static boolean sauvegarderParametre(String cle, String valeur) {
        String sql = "INSERT INTO parametre (cle, valeur) VALUES (?, ?) " +
                     "ON CONFLICT (cle) DO UPDATE SET valeur = EXCLUDED.valeur";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cle);
            pstmt.setString(2, valeur);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du paramètre " + cle + " : " + e.getMessage());
            return false;
        }
    }
}