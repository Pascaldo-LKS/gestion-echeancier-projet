package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AlerteDAO;
import com.mycompany.projetjava.dao.AuditDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterAlerteServlet", urlPatterns = {"/AjouterAlerteServlet"})
public class AjouterAlerteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String type = request.getParameter("type");
        String message = request.getParameter("message");
        String statut = request.getParameter("statut");
        
        try {
            boolean succes = AlerteDAO.insererAlerte(type, message, statut);
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (succes) {
                AuditDAO.enregistrerAction("CREATION", "Alerte", 0, idActeur);
                response.sendRedirect("alertes.jsp?succes=1");
            } else {
                response.sendRedirect("alertes.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("alertes.jsp?erreur=format");
        }
    }
}