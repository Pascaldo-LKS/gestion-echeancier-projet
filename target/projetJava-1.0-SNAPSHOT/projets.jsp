<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.ProjetDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Projets</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Gestion des Projets</h2>
            <div class="user-badge"> <%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Le projet a été enregistré avec succès !
            </div>
        <% } else if ("1".equals(request.getParameter("erreur"))) { %>
            <div style="background-color: #fef2f2; color: #991b1b; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ❌ Une erreur est survenue lors de l'enregistrement du projet.
            </div>
        <% } %>

        <div class="card">
            <h3>Nouveau Projet</h3>
            <form action="AjouterProjetServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Code Projet (facultatif) :</label>
                        <input type="text" name="code" placeholder="Ex: PRJ-2026-01">
                    </div>

                    <div class="form-group">
                        <label>Titre / Nom du Projet :</label>
                        <input type="text" name="titre" required placeholder="Ex: Construction Bâtiment A">
                    </div>

                    <div class="form-group">
                        <label>Date de Début :</label>
                        <input type="date" name="date_debut" required>
                    </div>

                    <div class="form-group">
                        <label>Date de Fin :</label>
                        <input type="date" name="date_fin" required>
                    </div>

                    <div class="form-group">
                        <label>Budget Prévu (FCFA) :</label>
                        <input type="number" step="0.01" name="budget_prevu" required placeholder="Ex: 5000000">
                    </div>

                    <div class="form-group">
                        <label>Statut :</label>
                        <select name="statut" required>
                            <option value="Planifié">Planifié</option>
                            <option value="En cours">En cours</option>
                            <option value="Terminé">Terminé</option>
                        </select>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit">Enregistrer le Projet</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Projets Enregistrés</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                       <!-- <th>ID</th> -->
                        <th>Code</th>
                        <th>Titre</th>
                        <th>Début</th>
                        <th>Fin</th>
                        <th>Budget</th>
                        <th>Statut</th>
                        <th>Actions / Suivi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> projets = ProjetDAO.listerProjets();
                        if (projets != null && !projets.isEmpty()) {
                            for (String[] p : projets) {
                    %>
                    <tr>
                      <!--  <td><%= p[0] %></td> -->
                        <td><strong><%= p[1] %></strong></td>
                        <td><%= p[2] %></td>
                        <td><%= p[3] %></td>
                        <td><%= p[4] %></td>
                        <td><%= p[5] %> FCFA</td>
                        <td><%= p[6] %></td>
                        <td>
                            <a href="SupprimerProjetServlet?id=<%= p[0] %>" 
                               onclick="return confirm('Voulez-vous vraiment supprimer ce projet ?');" 
                               style="color: #ef4444; text-decoration: none; font-weight: bold;">
                                Supprimer
                            </a>
                               <a href="detail_projet.jsp?id=<%= p[0] %>" style="background-color: #f59e0b; color: #111827; padding: 6px 12px; border-radius: 4px; text-decoration: none; font-weight: 600; font-size: 0.85rem;">
                                Voir le Suivi & Analyses
                            </a>

                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">Aucun projet enregistré.</td>
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