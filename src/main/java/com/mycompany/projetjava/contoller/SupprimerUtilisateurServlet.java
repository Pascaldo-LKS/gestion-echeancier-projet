package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AuditDAO;
import com.mycompany.projetjava.dao.GestionUtilisateurDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SupprimerUtilisateurServlet", urlPatterns = {"/SupprimerUtilisateurServlet"})
public class SupprimerUtilisateurServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean succes = GestionUtilisateurDAO.supprimerUtilisateur(id);
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (succes) {
                // Enregistrement de l'audit (Action, Type d'objet, ID de l'objet, ID de l'acteur)
                AuditDAO.enregistrerAction("SUPPRESSION", "Utilisateur", id, idActeur);
                response.sendRedirect("utilisateurs.jsp?succes_suppr=1");
            } else {
                response.sendRedirect("utilisateurs.jsp?erreur=suppr");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("utilisateurs.jsp?erreur=format");
        }
    }
}