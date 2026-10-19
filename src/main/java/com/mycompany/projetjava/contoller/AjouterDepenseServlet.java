package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.DepenseDAO;
import com.mycompany.projetjava.dao.AuditDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterDepenseServlet", urlPatterns = {"/AjouterDepenseServlet"})
public class AjouterDepenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String dateDepense = request.getParameter("dateDepense");
        String montantStr = request.getParameter("montant");
        String reference = request.getParameter("reference");
        String categorie = request.getParameter("categorie");
        String activiteIdStr = request.getParameter("activiteId");
        
        try {
            double montant = Double.parseDouble(montantStr);
            int idActivite = Integer.parseInt(activiteIdStr);
            
            boolean succes = DepenseDAO.insererDepense(dateDepense, montant, reference, categorie, idActivite);
            
            HttpSession session = request.getSession(false);
            String utilisateur = (session != null && session.getAttribute("utilisateur") != null) 
                                 ? (String) session.getAttribute("utilisateur") : "Administrateur";
            
            if (succes) {
                AuditDAO.enregistrerAction(utilisateur, "CREATION", "Ajout d'une dépense de " + montant + " FCFA");
                response.sendRedirect("depenses.jsp?succes=1");
            } else {
                response.sendRedirect("depenses.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("depenses.jsp?erreur=format");
        }
    }
}