<%@ page import="java.util.Map" %>
<%@ page import="com.mycompany.projetjava.dao.ProjetDetailDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String idStr = request.getParameter("id");
    int projetId = (idStr != null) ? Integer.parseInt(idStr) : 1;
    Map<String, Object> info = ProjetDetailDAO.getIndicateursProjet(projetId);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Suivi Analytique du Projet</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Analyse & Suivi : <%= info.getOrDefault("titreProjet", "Projet") %></h2>
            <div class="user-badge">Module Analytique</div>
        </div>

        <div class="card" style="border-left: 5px solid <%= "danger".equals(info.get("alerteType")) ? "#ef4444" : ("warning".equals(info.get("alerteType")) ? "#f59e0b" : "#10b981") %>;">
            <h3>Diagnostic & Alertes Intelligentes</h3>
            <p style="font-size: 1.1rem; font-weight: 600; padding: 10px 0;">
                <%= info.getOrDefault("alerteMessage", "Analyse en cours...") %>
            </p>
        </div>

        <div class="kpi-container">
            <div class="kpi-card">
                <h3>Budget Alloué</h3>
                <p class="kpi-value"><%= info.getOrDefault("budgetPrevu", 0.0) %> FCFA</p>
            </div>
            <div class="kpi-card">
                <h3>Dépenses Réalisées</h3>
                <p class="kpi-value"><%= info.getOrDefault("totalDepenses", 0.0) %> FCFA</p>
            </div>
            <div class="kpi-card">
                <h3>Écart / Solde</h3>
                <p class="kpi-value"><%= info.getOrDefault("ecartBudget", 0.0) %> FCFA</p>
            </div>
            <div class="kpi-card">
                <h3>Avancement Physique</h3>
                <p class="kpi-value"><%= info.getOrDefault("avancementMoyen", 0.0) %> %</p>
            </div>
        </div>

    </div>

</body>
</html>