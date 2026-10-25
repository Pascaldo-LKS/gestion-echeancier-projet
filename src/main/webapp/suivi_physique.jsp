<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.ActiviteDAO" %>
<%@ page import="com.mycompany.projetjava.dao.SuiviPhysiqueDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Suivi Physique - Pascal'do Solut</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Suivi Physique des Activités</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Suivi physique enregistré avec succès !
            </div>
        <% } %>

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
                            <option value="<%= act[0] %>"><%= act[1] %> - <%= act[2] %></option>
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
                        <input type="number" step="0.01" min="0" max="100" name="tauxAvancement" required placeholder="Ex: 45.5">
                    </div>
                    
                    <div class="form-group">
                        <label>Quantité Prévue :</label>
                        <input type="number" step="0.01" name="quantitePrevue" placeholder="Ex: 100">
                    </div>

                    <div class="form-group">
                        <label>Quantité Réalisée :</label>
                        <input type="number" step="0.01" name="quantiteRealisee" placeholder="Ex: 45">
                    </div>

                    <div class="form-group">
                        <label>Statut de Validation :</label>
                        <select name="statutValidation">
                            <option value="En attente">En attente</option>
                            <option value="Validé">Validé</option>
                            <option value="Rejeté">Rejeté</option>
                        </select>
                    </div>
                    
                    <div class="form-group" style="grid-column: span 2;">
                        <label>Commentaire / Remarques :</label>
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
                        <th>Activité</th>
                        <th>Date Constat</th>
                        <th>Taux</th>
                        <th>Qté Prévue</th>
                        <th>Qté Réalisée</th>
                        <th>Statut</th>
                        <th>Commentaire</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> suivis = SuiviPhysiqueDAO.listerSuivis();
                        if (suivis != null && !suivis.isEmpty()) {
                            for (String[] s : suivis) {
                    %>
                    <tr>
                        <td><strong><%= s[1] %></strong></td>
                        <td><%= s[2] %></td>
                        <td><%= s[3] %></td>
                        <td><%= s[4] %></td>
                        <td><%= s[5] %></td>
                        <td><%= s[6] %></td>
                        <td><%= s[7] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">Aucun suivi physique enregistré pour le moment.</td>
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