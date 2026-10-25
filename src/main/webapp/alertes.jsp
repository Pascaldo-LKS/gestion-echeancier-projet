<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.AlerteDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String userRole = (String) session.getAttribute("role");
    if (userRole == null) {
        userRole = "ADMIN";
    }
    userRole = userRole.toUpperCase();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Alertes - Pascal'do Solut</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Journal des Alertes & Notifications</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Alerte enregistrée avec succès !
            </div>
        <% } %>

        <% if ("ADMIN".equals(userRole)) { %>
        <div class="card">
            <h3>Créer une nouvelle alerte</h3>
            <form action="AjouterAlerteServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Type d'alerte :</label>
                        <select name="type" required>
                            <option value="Retard">Retard</option>
                            <option value="Budget">Budget</option>
                            <option value="Information">Information</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Statut :</label>
                        <select name="statut" required>
                            <option value="Non lu">Non lu</option>
                            <option value="Lu">Lu</option>
                            <option value="Traité">Traité</option>
                        </select>
                    </div>

                    <div class="form-group" style="grid-column: span 2;">
                        <label>Message de l'alerte :</label>
                        <input type="text" name="message" required placeholder="Ex: Dépassement du budget prévu">
                    </div>
                </div>

                <div style="margin-top: 15px;">
                    <button type="submit">Enregistrer l'Alerte</button>
                </div>
            </form>
        </div>
        <% } %>

        <div class="card">
            <h3>Liste des Alertes du Système</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>Type</th>
                        <th>Message</th>
                        <th>Date de création</th>
                        <th>Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> alertes = AlerteDAO.listerAlertes();
                        if (alertes != null && !alertes.isEmpty()) {
                            for (String[] alt : alertes) {
                    %>
                    <tr>
                        <td><strong><%= alt[0] %></strong></td>
                        <td><%= alt[1] %></td>
                        <td><%= alt[2] %></td>
                        <td><%= alt[3] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Aucune alerte enregistrée.</td>
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