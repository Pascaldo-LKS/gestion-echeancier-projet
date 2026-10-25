package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AffectationDAO;
import com.mycompany.projetjava.dao.AuditDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterAffectationServlet", urlPatterns = {"/AjouterAffectationServlet"})
public class AjouterAffectationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String dateAffectation = request.getParameter("dateAffectation");
        boolean actif = request.getParameter("actif") != null;
        String idUtilisateurStr = request.getParameter("idUtilisateur");
        String idActiviteStr = request.getParameter("idActivite");
        
        try {
            int idUtilisateur = Integer.parseInt(idUtilisateurStr);
            int idActivite = Integer.parseInt(idActiviteStr);
            
            boolean succes = AffectationDAO.insererAffectation(dateAffectation, actif, idUtilisateur, idActivite);
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            if (succes) {
                AuditDAO.enregistrerAction("CREATION", "Affectation", 0, idActeur);
                response.sendRedirect("affectations.jsp?succes=1");
            } else {
                response.sendRedirect("affectations.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("affectations.jsp?erreur=format");
        }
    }
}