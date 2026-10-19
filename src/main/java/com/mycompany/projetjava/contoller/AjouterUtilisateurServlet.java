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

@WebServlet(name = "AjouterUtilisateurServlet", urlPatterns = {"/AjouterUtilisateurServlet"})
public class AjouterUtilisateurServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String nomComplet = request.getParameter("nom_complet");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        boolean succes = GestionUtilisateurDAO.ajouterUtilisateur(nomComplet, email, password, role);
        
        HttpSession session = request.getSession(false);
        String utilisateur = (session != null && session.getAttribute("utilisateur") != null) 
                             ? (String) session.getAttribute("utilisateur") : "Administrateur";
        
        if (succes) {
            AuditDAO.enregistrerAction(utilisateur, "CREATION", "Ajout de l'utilisateur : " + nomComplet);
            response.sendRedirect("utilisateurs.jsp?succes=1");
        } else {
            response.sendRedirect("utilisateurs.jsp?erreur=1");
        }
    }
}