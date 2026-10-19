package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AuditDAO;
import com.mycompany.projetjava.dao.ProjetDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ModifierProjetServlet", urlPatterns = {"/ModifierProjetServlet"})
public class ModifierProjetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titre = request.getParameter("titre");
            String dateDebut = request.getParameter("date_debut");
            String dateFin = request.getParameter("date_fin");
            String statut = request.getParameter("statut");
            
            double budgetPrevu = 0.0;
            if (request.getParameter("budget_prevu") != null && !request.getParameter("budget_prevu").isEmpty()) {
                budgetPrevu = Double.parseDouble(request.getParameter("budget_prevu"));
            }
            
            boolean succes = ProjetDAO.modifierProjet(id, titre, dateDebut, dateFin, budgetPrevu, statut);
            
            HttpSession session = request.getSession(false);
            String utilisateur = (session != null && session.getAttribute("utilisateur") != null) 
                                 ? (String) session.getAttribute("utilisateur") : "Administrateur";
            
            if (succes) {
                AuditDAO.enregistrerAction(utilisateur, "MODIFICATION", "Modification du projet ID : " + id);
                response.sendRedirect("projets.jsp?succes=modif");
            } else {
                response.sendRedirect("projets.jsp?erreur=modif");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("projets.jsp?erreur=format");
        }
    }
}