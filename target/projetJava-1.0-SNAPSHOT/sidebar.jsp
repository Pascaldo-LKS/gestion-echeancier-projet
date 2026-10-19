<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String currentUri = request.getRequestURI();
    
    // Récupération du rôle depuis la session (valeur par défaut si non définie)
    String userRole = (String) session.getAttribute("role");
    if (userRole == null) {
        userRole = "ADMIN";
    }
    userRole = userRole.toUpperCase();
%>
<button class="menu-toggle" onclick="toggleSidebar()">Menu</button>

<div class="sidebar" id="appSidebar">
    <div class="sidebar-brand">
        <h2>Gestion Échéancier</h2>
    </div>
    <ul class="sidebar-menu">
        <!-- Tableau de bord : accessible à tous les rôles -->
        <li>
            <a href="tableau.jsp" class="<%= currentUri.endsWith("tableau.jsp") ? "active" : "" %>">
                Tableau de bord
            </a>
        </li>

        <!-- Menus accessibles à l'ADMIN et au CP (Chef de Projet) -->
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
        <% } %>

        <!-- Menus accessibles à l'ADMIN, au CP et à l'AGENT TERRAIN (Saisie de l'exécution périodique) -->
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
        <% } %>

        <!-- Menus réservés exclusivement à l'ADMIN -->
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

        <!-- Bouton de Déconnexion -->
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