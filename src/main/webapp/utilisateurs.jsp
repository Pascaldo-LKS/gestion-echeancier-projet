<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.GestionUtilisateurDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Utilisateurs - Sécurité</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2> Gestion des Utilisateurs & Rôles</h2>
            <div class="user-badge"> <%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #bbf7d0;">
                Utilisateur ajouté avec succès !
            </div>
        <% } else if ("1".equals(request.getParameter("succes_suppr"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #bbf7d0;">
                 Utilisateur supprimé avec succès !
            </div>
        <% } %>

        <div class="card">
            <h3>Ajouter un nouvel utilisateur</h3>
            <form action="AjouterUtilisateurServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Nom Complet :</label>
                        <input type="text" name="nom_complet" required placeholder="Ex: Jean Dupont">
                    </div>

                    <div class="form-group">
                        <label>Email :</label>
                        <input type="email" name="email" required placeholder="Ex: jean@domaine.bj">
                    </div>

                    <div class="form-group">
                        <label>Mot de passe :</label>
                        <input type="password" name="password" required placeholder="••••••••">
                    </div>

                    <div class="form-group">
                        <label>Rôle :</label>
                        <select name="role" required>
                            <option value="admin">Administrateur</option>
                            <option value="chef_projet">Chef de Projet</option>
                            <option value="agent">Agent / Collaborateur</option>
                        </select>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit">Créer l'Utilisateur</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Liste des Utilisateurs Enregistrés</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                       <!-- <th>ID</th> -->   
                        <th>Nom Complet</th>
                        <th>Email</th>
                        <th>Rôle</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> utilisateurs = GestionUtilisateurDAO.listerUtilisateurs();
                        if (utilisateurs != null && !utilisateurs.isEmpty()) {
                            for (String[] u : utilisateurs) {
                    %>
                    <tr>
                      <!--  <td><%= u[0] %></td> -->
                        <td><strong><%= u[1] %></strong></td>
                        <td><%= u[2] %></td>
                        <td><span class="badge" style="background: #f1f5f9; color: #0f172a; padding: 4px 8px; border-radius: 4px;"><%= u[3] %></span></td>
                        <td><span style="color: <%= "Actif".equals(u[4]) ? "#16a34a" : "#dc2626" %>; font-weight: bold;"><%= u[4] %></span></td>
                        <td>
                            <a href="SupprimerUtilisateurServlet?id=<%= u[0] %>" 
                               onclick="return confirm('Voulez-vous vraiment supprimer cet utilisateur ?');" 
                               style="color: #ef4444; text-decoration: none; font-weight: bold;">
                                🗑️ Supprimer
                            </a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">Aucun utilisateur enregistré.</td>
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