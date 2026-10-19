<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.DashboardDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Map<String, Object> stats = DashboardDAO.getStatistiquesGlobales();
    List<String[]> dernieresDepenses = DashboardDAO.getDernieresDepenses();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - Gestion d'Échéancier</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        
        <div class="dashboard-header">
            <h2>Tableau de Bord Analytics</h2>
            <div class="user-badge">
                Utilisateur : <%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %>
            </div>
        </div>

        <div class="kpi-container">
            
            <div class="kpi-card">
                <h3>Projets</h3>
                <p class="kpi-value"><%= stats.getOrDefault("totalProjets", 0) %></p>
                <span class="kpi-label">Projets enregistrés</span>
            </div>

            <div class="kpi-card">
                <h3>Activités</h3>
                <p class="kpi-value"><%= stats.getOrDefault("totalActivites", 0) %></p>
                <span class="kpi-label">Activités planifiées</span>
            </div>

            <div class="kpi-card">
                <h3>Budget Prévu Total</h3>
                <p class="kpi-value"><%= stats.getOrDefault("totalBudget", 0.0) %> FCFA</p>
                <span class="kpi-label">Cout global estimé</span>
            </div>

            <div class="kpi-card">
                <h3>Dépenses Réalisées</h3>
                <p class="kpi-value"><%= stats.getOrDefault("totalDepenses", 0.0) %> FCFA</p>
                <span class="kpi-label">Total consommé</span>
            </div>

            <div class="kpi-card">
                <h3>Écart Budgétaire</h3>
                <p class="kpi-value"><%= stats.getOrDefault("ecartBudget", 0.0) %> FCFA</p>
                <span class="kpi-label">Reste disponible</span>
            </div>

            <div class="kpi-card">
                <h3>Avancement Global</h3>
                <p class="kpi-value"><%= stats.getOrDefault("avancementMoyen", 0.0) %> %</p>
                <span class="kpi-label">Taux moyen d'exécution</span>
            </div>

        </div>

        <div class="card">
            <h3>Dernières Dépenses Enregistrées</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Référence</th>
                        <th>Activité</th>
                        <th>Montant (FCFA)</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (dernieresDepenses != null && !dernieresDepenses.isEmpty()) {
                            for (String[] dep : dernieresDepenses) {
                    %>
                    <tr>
                        <td><%= dep[0] %></td>
                        <td><%= dep[2] %></td>
                        <td><%= dep[3] %></td>
                        <td><strong><%= dep[1] %> FCFA</strong></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4">Aucune dépense récente.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            </div>
        </div>

    </div>

</body>
</html>