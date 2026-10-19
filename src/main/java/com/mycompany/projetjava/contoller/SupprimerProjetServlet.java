package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.ProjetDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SupprimerProjetServlet", urlPatterns = {"/SupprimerProjetServlet"})
public class SupprimerProjetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean succes = ProjetDAO.supprimerProjet(id);
            
            if (succes) {
                response.sendRedirect("tableau.jsp?succes=suppr");
            } else {
                response.sendRedirect("tableau.jsp?erreur=suppr");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("tableau.jsp?erreur=format");
        }
    }
}