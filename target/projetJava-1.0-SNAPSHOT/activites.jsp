<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.ProjetDAO" %>
<%@ page import="com.mycompany.projetjava.dao.ActiviteDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Activités - Pascal'do Solut</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Gestion des Activités</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Activité enregistrée avec succès !
            </div>
        <% } %>

        <div class="card">
            <h3>Ajouter une nouvelle activité</h3>
            <form action="AjouterActiviteServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Code de l'Activité :</label>
                        <input type="text" name="code" required placeholder="Ex: ACT-01">
                    </div>
                    
                    <div class="form-group">
                        <label>Libellé de l'Activité :</label>
                        <input type="text" name="libelle" required placeholder="Ex: Conception maquette UI">
                    </div>
                    
                    <div class="form-group">
                        <label>Date Début Prévue :</label>
                        <input type="date" name="dateDebutPrevue" required>
                    </div>
                    
                    <div class="form-group">
                        <label>Date Fin Prévue :</label>
                        <input type="date" name="dateFinPrevue" required>
                    </div>
                    
                    <div class="form-group">
                        <label>Budget Prévu (FCFA) :</label>
                        <input type="number" step="0.01" name="budgetPrevu" required placeholder="Ex: 500000">
                    </div>
                    
                    <div class="form-group">
                        <label>Poids d'Avancement (%) :</label>
                        <input type="number" step="0.01" name="poidsAvancement" required value="0.00" placeholder="Ex: 0">
                    </div>
                    
                    <div class="form-group">
                        <label>Statut :</label>
                        <select name="statut" required>
                            <option value="Planifié">Planifié</option>
                            <option value="En cours">En cours</option>
                            <option value="Terminé">Terminé</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>Projet Rattaché :</label>
                        <select name="projetId" required>
                            <option value="">-- Sélectionner un projet --</option>
                            <%
                                List<String[]> projets = ProjetDAO.listerProjets();
                                if (projets != null) {
                                    for (String[] p : projets) {
                            %>
                            <option value="<%= p[0] %>"><%= p[1] %> - <%= p[2] %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                
                <div style="margin-top: 15px;">
                    <button type="submit">Enregistrer l'Activité</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Liste des Activités Enregistrées</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Libellé</th>
                        <th>Date Début</th>
                        <th>Date Fin</th>
                        <th>Budget Prévu (FCFA)</th>
                        <th>Poids (%)</th>
                        <th>Statut</th>
                        <th>Projet Associé</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> activites = ActiviteDAO.listerActivites();
                        if (activites != null && !activites.isEmpty()) {
                            for (String[] act : activites) {
                    %>
                    <tr>
                        <td><%= act[1] %></td>
                        <td><%= act[2] %></td>
                        <td><%= act[3] %></td>
                        <td><%= act[4] %></td>
                        <td><%= act[5] %></td>
                        <td><%= act[6] %></td>
                        <td><%= act[7] %></td>
                        <td><strong><%= act[8] %></strong></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">Aucune activité enregistrée.</td>
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