package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AuditDAO;
import com.mycompany.projetjava.dao.ParametreDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SauvegarderParametresServlet", urlPatterns = {"/SauvegarderParametresServlet"})
public class SauvegarderParametresServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String nomOrganisme = request.getParameter("nom_organisme");
        String devise = request.getParameter("devise");
        String seuilAlerte = request.getParameter("seuil_alerte");
        String emailNotification = request.getParameter("email_notification");
        
        boolean p1 = ParametreDAO.sauvegarderParametre("nom_organisme", nomOrganisme);
        boolean p2 = ParametreDAO.sauvegarderParametre("devise", devise);
        boolean p3 = ParametreDAO.sauvegarderParametre("seuil_alerte", seuilAlerte);
        boolean p4 = ParametreDAO.sauvegarderParametre("email_notification", emailNotification);
        
        HttpSession session = request.getSession(false);
        int idActeur = 1; 
        if (session != null && session.getAttribute("idUser") != null) {
            idActeur = (int) session.getAttribute("idUser");
        }
        
        if (p1 && p2 && p3 && p4) {
            // Enregistrement de l'audit (Action, Type d'objet, ID de l'objet, ID de l'acteur)
            AuditDAO.enregistrerAction("MISE_A_JOUR", "Parametres", 0, idActeur);
            response.sendRedirect("parametres.jsp?succes=1");
        } else {
            response.sendRedirect("parametres.jsp?erreur=1");
        }
    }
}