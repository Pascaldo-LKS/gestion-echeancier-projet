<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.projetjava.dao.AuditDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Journal d'Audit - Traçabilité</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <jsp:include page="sidebar.jsp" />

    <div class="main-content">
        <div class="dashboard-header">
            <h2>📜 Journal d'Audit & Traçabilité</h2>
            <div class="user-badge">👤 <%= session.getAttribute("utilisateur") != null ? session.getAttribute("utilisateur") : "Administrateur" %></div>
        </div>

        <div class="card">
            <h3>Historique des actions du système</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Utilisateur</th>
                        <th>Action</th>
                        <th>Description</th>
                        <th>Date & Heure</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<String[]> logs = AuditDAO.listerAudit();
                        if (logs != null && !logs.isEmpty()) {
                            for (String[] log : logs) {
                    %>
                    <tr>
                        <td><%= log[0] %></td>
                        <td><strong><%= log[1] %></strong></td>
                        <td><span class="badge" style="background: #e0f2fe; color: #0369a1; padding: 4px 8px; border-radius: 4px;"><%= log[2] %></span></td>
                        <td><%= log[3] %></td>
                        <td><%= log[4] %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Aucune action enregistrée dans le journal d'audit.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>