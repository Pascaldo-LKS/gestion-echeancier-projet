<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.AffectationDAO" %>
<%@ page import="com.mycompany.projetjava.dao.UtilisateurDAO" %>
<%@ page import="com.mycompany.projetjava.dao.ActiviteDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Affectations - Pascal'do Solut</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Gestion des Affectations</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Affectation enregistrée avec succès !
            </div>
        <% } %>

        <div class="card">
            <h3>Affecter un utilisateur à une activité</h3>
            <form action="AjouterAffectationServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Utilisateur :</label>
                        <select name="idUtilisateur" required>
                            <option value="">-- Sélectionner un utilisateur --</option>
                            <%
                                List<String[]> utilisateurs = UtilisateurDAO.listerUtilisateurs();
                                if (utilisateurs != null) {
                                    for (String[] u : utilisateurs) {
                            %>
                            <option value="<%= u[0] %>"><%= u[1] %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Activité :</label>
                        <select name="idActivite" required>
                            <option value="">-- Sélectionner une activité --</option>
                            <%
                                List<String[]> activites = ActiviteDAO.listerActivites();
                                if (activites != null) {
                                    for (String[] act : activites) {
                            %>
                            <option value="<%= act[0] %>"><%= act[1] %> - <%= act[2] %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Date d'affectation :</label>
                        <input type="date" name="dateAffectation" required>
                    </div>

                    <div class="form-group">
                        <label>Statut :</label>
                        <div style="margin-top: 10px;">
                            <input type="checkbox" name="actif" value="true" checked> Actif
                        </div>
                    </div>
                </div>

                <div style="margin-top: 15px;">
                    <button type="submit">Enregistrer l'Affectation</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Liste des Affectations</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Utilisateur</th>
                        <th>Activité</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> affectations = AffectationDAO.listerAffectations();
                        if (affectations != null && !affectations.isEmpty()) {
                            for (String[] aff : affectations) {
                    %>
                    <tr>
                        <td><%= aff[0] %></td>
                        <td><%= aff[1] %></td>
                        <td><strong><%= aff[2] %></strong></td>
                        <td><%= aff[3] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Aucune affectation enregistrée.</td>
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