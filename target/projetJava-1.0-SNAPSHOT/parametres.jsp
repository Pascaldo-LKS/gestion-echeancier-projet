<%@ page import="java.util.Map" %>
<%@ page import="com.mycompany.projetjava.dao.ParametreDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Map<String, String> params = ParametreDAO.obtenirTousLesParametres();
    String nomOrganisme = params.getOrDefault("nom_organisme", "Ministère / Entreprise");
    String devise = params.getOrDefault("devise", "FCFA");
    String seuilAlerte = params.getOrDefault("seuil_alerte", "80");
    String emailNotification = params.getOrDefault("email_notification", "admin@domaine.bj");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paramètres du Système - Configuration</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2> Configuration & Paramètres du Système</h2>
            <div class="user-badge"> <%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #bbf7d0;">
                ✅ Les paramètres ont été mis à jour avec succès !
            </div>
        <% } else if ("1".equals(request.getParameter("erreur"))) { %>
            <div style="background-color: #fef2f2; color: #991b1b; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #fecaca;">
                ❌ Une erreur est survenue lors de l'enregistrement.
            </div>
        <% } %>

        <div class="card">
            <h3>Paramètres Généraux de l'Application</h3>
            <form action="SauvegarderParametresServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Nom de l'Organisme / Entreprise :</label>
                        <input type="text" name="nom_organisme" value="<%= nomOrganisme %>" required>
                    </div>

                    <div class="form-group">
                        <label>Devise Principale :</label>
                        <input type="text" name="devise" value="<%= devise %>" required placeholder="Ex: FCFA, EUR, USD">
                    </div>

                    <div class="form-group">
                        <label>Seuil d'Alerte de Budget / Retard (%) :</label>
                        <input type="number" name="seuil_alerte" value="<%= seuilAlerte %>" min="1" max="100" required>
                    </div>

                    <div class="form-group">
                        <label>Email de Notification Administrateur :</label>
                        <input type="email" name="email_notification" value="<%= emailNotification %>" required>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit">Enregistrer les Modifications</button>
                </div>
            </form>
        </div>
    </div>

</body>
</html>