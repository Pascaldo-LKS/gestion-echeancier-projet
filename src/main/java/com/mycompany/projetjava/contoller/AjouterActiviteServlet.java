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
        String budgetPrevuStr = request.getParameter("budgetPrevu");
        String projetIdStr = request.getParameter("projetId");
        
        try {
            double budgetPrevu = 0.0;
            if (budgetPrevuStr != null && !budgetPrevuStr.trim().isEmpty()) {
                budgetPrevu = Double.parseDouble(budgetPrevuStr);
            }
            
            int projetId = Integer.parseInt(projetIdStr);
            
            // Appel au DAO avec insertion du poids d'avancement par défaut à 0.0
            boolean insertionReussie = ActiviteDAO.insererActivite(code, libelle, budgetPrevu, projetId);
            
            HttpSession session = request.getSession(false);
            String utilisateur = (session != null && session.getAttribute("utilisateur") != null) 
                                 ? (String) session.getAttribute("utilisateur") : "Administrateur";
            
            if (insertionReussie) {
                AuditDAO.enregistrerAction(utilisateur, "CREATION", "Ajout de l'activité : " + libelle);
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