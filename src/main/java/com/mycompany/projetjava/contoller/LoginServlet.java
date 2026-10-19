package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.UtilisateurDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String nomComplet = request.getParameter("nom_complet");
        String password = request.getParameter("password");
        
        String role = UtilisateurDAO.authentifier(nomComplet, password);
        
        if (role != null) {
            // Création de la session HTTP
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", nomComplet);
            session.setAttribute("role", role);
            
            // Redirection vers le tableau de bord
            response.sendRedirect("tableau.jsp");
        } else {
            // En cas d'échec, retour à la page de connexion avec message d'erreur
            response.sendRedirect("login.jsp?erreur=1");
        }
    }
}