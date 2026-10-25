<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String currentUri = request.getRequestURI();
    
    String userRole = (String) session.getAttribute("role");
    if (userRole == null) {
        userRole = "ADMIN";
    }
    userRole = userRole.toUpperCase();
%>
<button class="menu-toggle" onclick="toggleSidebar()">Menu</button>

<div class="sidebar" id="appSidebar">
    <div class="sidebar-brand" style="text-align: center; padding: 15px 10px;">
        <img src="images/logo.jpeg" alt="Logo Pascal'do Solut" style="width: 50px; height: 50px; object-fit: contain; border-radius: 50%; margin-bottom: 5px;">
        <h2 style="font-size: 1.1rem; color: #d97706; margin: 0;">Pascal'do Solut</h2>
        <span style="font-size: 0.75rem; color: #94a3b8;">Gestion Échéancier</span>
    </div>
    <ul class="sidebar-menu">
        <li>
            <a href="tableau.jsp" class="<%= currentUri.endsWith("tableau.jsp") ? "active" : "" %>">
                Tableau de bord
            </a>
        </li>

        <% if ("ADMIN".equals(userRole) || "CHEF_PROJET".equals(userRole) || "CP".equals(userRole)) { %>
        <li>
            <a href="projets.jsp" class="<%= currentUri.endsWith("projets.jsp") ? "active" : "" %>">
                Projets
            </a>
        </li>
        <li>
            <a href="activites.jsp" class="<%= currentUri.endsWith("activites.jsp") ? "active" : "" %>">
                Activités
            </a>
        </li>
        <li>
            <a href="affectations.jsp" class="<%= currentUri.endsWith("affectations.jsp") ? "active" : "" %>">
                Affectations
            </a>
        </li>
           
        <% } %>

        <% if ("ADMIN".equals(userRole) || "CHEF_PROJET".equals(userRole) || "CP".equals(userRole) || "AGENT".equals(userRole) || "AT".equals(userRole)) { %>
        <li>
            <a href="suivi_physique.jsp" class="<%= currentUri.endsWith("suivi_physique.jsp") ? "active" : "" %>">
                Suivi Physique
            </a>
        </li>
        <li>
            <a href="depenses.jsp" class="<%= currentUri.endsWith("depenses.jsp") ? "active" : "" %>">
                Dépenses
            </a>
        </li>
        <li>
            <a href="alertes.jsp" class="<%= currentUri.endsWith("alertes.jsp") ? "active" : "" %>">
                Alertes
            </a>
        </li>   
        <% } %>

        <% if ("ADMIN".equals(userRole)) { %>
        <li>
            <a href="utilisateurs.jsp" class="<%= currentUri.endsWith("utilisateurs.jsp") ? "active" : "" %>">
                Utilisateurs
            </a>
        </li>
        <li>
            <a href="journal_audit.jsp" class="<%= currentUri.endsWith("journal_audit.jsp") ? "active" : "" %>">
                Journal d'Audit
            </a>
        </li>
        <li>
            <a href="parametres.jsp" class="<%= currentUri.endsWith("parametres.jsp") ? "active" : "" %>">
                Paramètres
            </a>
        </li>
        <li>
            <a href="admin.jsp" class="<%= currentUri.endsWith("admin.jsp") ? "active" : "" %>">
                Administration
            </a>
        </li>
        <% } %>

        <li style="margin-top: 25px; border-top: 1px solid #334155; padding-top: 15px;">
            <a href="login.jsp" style="background-color: #ef4444; color: #ffffff !important; display: block; padding: 10px; text-align: center; border-radius: 6px; font-weight: bold; text-decoration: none;">
                Déconnexion
            </a>
        </li>
    </ul>
</div>

<script>
    function toggleSidebar() {
        const sidebar = document.getElementById('appSidebar');
        sidebar.classList.toggle('active');
    }
</script>