package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.ActiviteDAO;
import com.mycompany.projetjava.dao.AuditDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterActiviteServlet", urlPatterns = {"/AjouterActiviteServlet"})
public class AjouterActiviteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String code = request.getParameter("code");
        String libelle = request.getParameter("libelle");
        String dateDebutPrevue = request.getParameter("dateDebutPrevue");
        String dateFinPrevue = request.getParameter("dateFinPrevue");
        String budgetPrevuStr = request.getParameter("budgetPrevu");
        String poidsAvancementStr = request.getParameter("poidsAvancement");
        String statut = request.getParameter("statut");
        String projetIdStr = request.getParameter("projetId");
        
        try {
            double budgetPrevu = 0.0;
            if (budgetPrevuStr != null && !budgetPrevuStr.trim().isEmpty()) {
                budgetPrevu = Double.parseDouble(budgetPrevuStr);
            }
            
            double poidsAvancement = 0.0;
            if (poidsAvancementStr != null && !poidsAvancementStr.trim().isEmpty()) {
                poidsAvancement = Double.parseDouble(poidsAvancementStr);
            }
            
            int projetId = Integer.parseInt(projetIdStr);
            
            boolean insertionReussie = ActiviteDAO.insererActivite(code, libelle, dateDebutPrevue, dateFinPrevue, budgetPrevu, poidsAvancement, statut, projetId);
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (insertionReussie) {
                AuditDAO.enregistrerAction("CREATION", "Activite", 0, idActeur);
                response.sendRedirect("activites.jsp?succes=1");
            } else {
                response.sendRedirect("activites.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("activites.jsp?erreur=format");
        }
    }
}