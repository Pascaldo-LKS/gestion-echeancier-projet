<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.ActiviteDAO" %>
<%@ page import="com.mycompany.projetjava.dao.SuiviPhysiqueDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suivi Physique - Gestion des Échéanciers</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Suivi Physique des Activités</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <div class="card">
            <h3>Enregistrer un rapport d'avancement</h3>
            <form action="AjouterSuiviServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Activité concernée :</label>
                        <select name="activiteId" required>
                            <option value="">-- Sélectionner une activité --</option>
                            <%
                                List<String[]> activites = ActiviteDAO.listerActivites();
                                if (activites != null) {
                                    for (String[] act : activites) {
                            %>
                            <option value="<%= act[0] %>"><%= act[1] %> - <%= act[2] %> (<%= act[4] %>)</option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>Date du Suivi :</label>
                        <input type="date" name="dateSuivi" required>
                    </div>
                    
                    <div class="form-group">
                        <label>Taux d'Avancement (%) :</label>
                        <input type="number" step="0.1" min="0" max="100" name="tauxAvancement" required placeholder="Ex: 45.5">
                    </div>
                    
                    <div class="form-group">
                        <label>Description / Remarques :</label>
                        <input type="text" name="description" placeholder="Ex: Fin de la phase de conception UI">
                    </div>
                </div>
                
                <div style="margin-top: 15px;">
                    <button type="submit">Enregistrer le Suivi</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Historique des Suivis Physiques</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                      <!--  <th>ID</th> -->
                        <th>Activité</th>
                        <th>Date</th>
                        <th>Taux d'Avancement</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> suivis = SuiviPhysiqueDAO.listerSuivis();
                        if (suivis != null && !suivis.isEmpty()) {
                            for (String[] s : suivis) {
                    %>
                    <tr>
                      <!--  <td><%= s[0] %></td> -->
                        <td><strong><%= s[1] %></strong></td>
                        <td><%= s[2] %></td>
                        <td><%= s[3] %></td>
                        <td><%= s[4] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Aucun suivi physique enregistré pour le moment.</td>
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