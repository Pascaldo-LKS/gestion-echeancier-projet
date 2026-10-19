<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.ActiviteDAO" %>
<%@ page import="com.mycompany.projetjava.dao.DepenseDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Dépenses - Échéancier</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>Gestion des Dépenses</h2>
            <div class="user-badge"><%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <% if ("1".equals(request.getParameter("succes"))) { %>
            <div style="background-color: #dcfce7; color: #166534; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
                ✅ Dépense enregistrée avec succès !
            </div>
        <% } %>

        <div class="card">
            <h3>Enregistrer une nouvelle dépense</h3>
            <form action="AjouterDepenseServlet" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Date de la Dépense :</label>
                        <input type="date" name="dateDepense" required>
                    </div>
                    
                    <div class="form-group">
                        <label>Montant (FCFA) :</label>
                        <input type="number" step="0.01" name="montant" required placeholder="Ex: 25000">
                    </div>
                    
                    <div class="form-group">
                        <label>Référence / Pièce :</label>
                        <input type="text" name="reference" placeholder="Ex: FACT-2026-01">
                    </div>
                    
                    <div class="form-group">
                        <label>Catégorie :</label>
                        <input type="text" name="categorie" placeholder="Ex: Matériel, Transport...">
                    </div>
                    
                    <div class="form-group">
                        <label>Activité Associée :</label>
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
                </div>
                
                <div style="margin-top: 15px;">
                    <button type="submit">Enregistrer la Dépense</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h3>Historique des Dépenses</h3>
            <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        
                        <th>Date</th>
                        <th>Montant</th>
                        <th>Référence</th>
                        <th>Catégorie</th>
                        <th>Statut</th>
                        <th>Activité Associée</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> depenses = DepenseDAO.listerDepenses();
                        if (depenses != null && !depenses.isEmpty()) {
                            for (String[] dep : depenses) {
                    %>
                    <tr>
                        
                        <td><%= dep[1] %></td>
                        <td><strong><%= dep[2] %></strong></td>
                        <td><%= dep[3] %></td>
                        <td><%= dep[4] %></td>
                        <td><%= dep[5] %></td>
                        <td><%= dep[6] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">Aucune dépense enregistrée pour le moment.</td>
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