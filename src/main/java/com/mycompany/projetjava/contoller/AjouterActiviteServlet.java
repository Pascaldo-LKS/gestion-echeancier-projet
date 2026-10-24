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
            
            // Insertion de l'activité
            boolean insertionReussie = ActiviteDAO.insererActivite(code, libelle, budgetPrevu, projetId);
            
            // Récupération de l'ID de l'utilisateur connecté depuis la session (par défaut 1 si non trouvé)
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (insertionReussie) {
                // Enregistrement de l'action dans le journal d'audit (Action, Type d'objet, ID de l'objet, ID de l'acteur)
                // Note: On met 0 pour l'ID de l'objet créé si on ne le récupère pas immédiatement, ou l'ID réel de l'activité
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