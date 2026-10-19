package com.mycompany.projetjava.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Paramètres de connexion à votre base de données PostgreSQL locale
    private static final String URL = "jdbc:postgresql://localhost:5432/gestion_echeancier";
    private static final String USER = "postgres"; // Remplacez par votre nom d'utilisateur PostgreSQL si différent
    private static final String PASSWORD = "6603"; // Remplacez par votre mot de passe PostgreSQL

    // Méthode statique qui retourne une connexion active à la base de données
    public static Connection getConnection() {
        Connection connexion = null;
        try {
            // 1. Chargement du pilote JDBC PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // 2. Établissement de la connexion avec les paramètres
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à PostgreSQL réussie avec succès !");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote PostgreSQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return connexion;
    }
}