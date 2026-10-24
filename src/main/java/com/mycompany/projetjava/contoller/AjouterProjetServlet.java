package com.mycompany.projetjava.controller;

import com.mycompany.projetjava.dao.AuditDAO;
import com.mycompany.projetjava.dao.ProjetDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterProjetServlet", urlPatterns = {"/AjouterProjetServlet"})
public class AjouterProjetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            String titre = request.getParameter("titre");
            String dateDebut = request.getParameter("date_debut");
            String dateFin = request.getParameter("date_fin");
            String statut = request.getParameter("statut");
            double budgetPrevu = 0.0;
            
            if (request.getParameter("budget_prevu") != null && !request.getParameter("budget_prevu").isEmpty()) {
                budgetPrevu = Double.parseDouble(request.getParameter("budget_prevu"));
            }
            
            String code = request.getParameter("code");
            if (code == null || code.trim().isEmpty()) {
                code = "PRJ-" + (System.currentTimeMillis() % 100000);
            }
            
            boolean succes = ProjetDAO.insererProjet(code, titre, dateDebut, dateFin, budgetPrevu, statut);
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (succes) {
                // Enregistrement de l'audit (Action, Type d'objet, ID de l'objet, ID de l'acteur)
                AuditDAO.enregistrerAction("CREATION", "Projet", 0, idActeur);
                response.sendRedirect("projets.jsp?succes=1");
            } else {
                response.sendRedirect("projets.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("projets.jsp?erreur=1");
        }
    }
}