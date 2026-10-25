package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.dao.AuditDAO;
import com.mycompany.projetjava.dao.SuiviPhysiqueDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AjouterSuiviServlet", urlPatterns = {"/AjouterSuiviServlet"})
public class AjouterSuiviServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String activiteIdStr = request.getParameter("activiteId");
        String dateSuivi = request.getParameter("dateSuivi");
        String tauxStr = request.getParameter("tauxAvancement");
        String qtePrevueStr = request.getParameter("quantitePrevue");
        String qteRealiseeStr = request.getParameter("quantiteRealisee");
        String statutValidation = request.getParameter("statutValidation");
        String description = request.getParameter("description");
        
        try {
            int activiteId = Integer.parseInt(activiteIdStr);
            double tauxAvancement = Double.parseDouble(tauxStr);
            
            Double quantitePrevue = null;
            if (qtePrevueStr != null && !qtePrevueStr.trim().isEmpty()) {
                quantitePrevue = Double.parseDouble(qtePrevueStr);
            }
            
            Double quantiteRealisee = null;
            if (qteRealiseeStr != null && !qteRealiseeStr.trim().isEmpty()) {
                quantiteRealisee = Double.parseDouble(qteRealiseeStr);
            }
            
            HttpSession session = request.getSession(false);
            int idActeur = 1; 
            if (session != null && session.getAttribute("idUser") != null) {
                idActeur = (int) session.getAttribute("idUser");
            }
            
            boolean succes = SuiviPhysiqueDAO.insererSuivi(activiteId, dateSuivi, tauxAvancement, quantitePrevue, quantiteRealisee, statutValidation, description, idActeur);
            
            if (succes) {
                AuditDAO.enregistrerAction("CREATION", "Suivi Physique", 0, idActeur);
                response.sendRedirect("suivi_physique.jsp?succes=1");
            } else {
                response.sendRedirect("suivi_physique.jsp?erreur=1");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("suivi_physique.jsp?erreur=format");
        }
    }
}