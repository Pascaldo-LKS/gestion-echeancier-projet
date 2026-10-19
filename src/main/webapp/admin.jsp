<%@ page import="com.mycompany.projetjava.dao.GestionUtilisateurDAO" %>
<%@ page import="com.mycompany.projetjava.dao.AuditDAO" %>
<%@ page import="com.mycompany.projetjava.dao.ProjetDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administration - Système de Gestion</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2> Console d'Administration</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-bottom: 30px;">
            
            <div class="card" style="text-align: center; border-top: 4px solid #0284c7;">
                <h3> Gestion des Utilisateurs</h3>
                <p>Créer, modifier ou révoquer les accès des utilisateurs de la plateforme.</p>
                <div style="margin-top: 15px;">
                    <a href="utilisateurs.jsp" class="btn" style="padding: 8px 16px; background: #0284c7; color: white; text-decoration: none; border-radius: 4px;">Gérer les utilisateurs</a>
                </div>
            </div>

            <div class="card" style="text-align: center; border-top: 4px solid #16a34a;">
                <h3>Journal d'Audit</h3>
                <p>Consulter l'historique complet des actions et de la traçabilité du système.</p>
                <div style="margin-top: 15px;">
                    <a href="journal_audit.jsp" class="btn" style="padding: 8px 16px; background: #16a34a; color: white; text-decoration: none; border-radius: 4px;">Voir les audits</a>
                </div>
            </div>

            <div class="card" style="text-align: center; border-top: 4px solid #d97706;">
                <h3> Paramètres Système</h3>
                <p>Configurer les devises, les seuils d'alerte et les informations générales.</p>
                <div style="margin-top: 15px;">
                    <a href="parametres.jsp" class="btn" style="padding: 8px 16px; background: #d97706; color: white; text-decoration: none; border-radius: 4px;">Paramétrer</a>
                </div>
            </div>

        </div>

        <div class="card">
            <h3>Informations Techniques & Sécurité</h3>
            <ul style="line-height: 1.8; color: #334155; padding-left: 20px;">
                <li><strong>Rôle de l'utilisateur connecté :</strong> <span style="color: #0284c7; font-weight: bold;"><%= session.getAttribute("role") != null ? session.getAttribute("role") : "Admin" %></span></li>
            </ul>
        </div>
    </div>

</body>
</html>