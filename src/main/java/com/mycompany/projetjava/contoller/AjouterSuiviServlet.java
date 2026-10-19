package com.mycompany.projetjava.contoller;

import com.mycompany.projetjava.config.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AjouterSuiviServlet", urlPatterns = {"/AjouterSuiviServlet"})
public class AjouterSuiviServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String activiteIdStr = request.getParameter("activiteId");
        String dateSuivi = request.getParameter("dateSuivi");
        String tauxStr = request.getParameter("tauxAvancement");
        String description = request.getParameter("description");
        
        try (PrintWriter out = response.getWriter()) {
            int activiteId = Integer.parseInt(activiteIdStr);
            double tauxAvancement = Double.parseDouble(tauxStr);
            
            // On ajoute id_auteur (fixé à 1 par défaut pour éviter la violation NOT NULL)
            String sql = "INSERT INTO suivi_physique (id_activite, date_constat, taux_avancement, commentaire, id_auteur) VALUES (?, ?, ?, ?, ?)";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setInt(1, activiteId);
                if (dateSuivi != null && !dateSuivi.trim().isEmpty()) {
                    pstmt.setDate(2, java.sql.Date.valueOf(dateSuivi));
                } else {
                    pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                }
                pstmt.setDouble(3, tauxAvancement);
                pstmt.setString(4, description);
                pstmt.setInt(5, 1); // ID de l'auteur par défaut (ex: 1)
                
                pstmt.executeUpdate();
                response.sendRedirect("suivi_physique.jsp?succes=1");
                
            } catch (SQLException e) {
                out.println("<h2>ERREUR SQL EXACTE (SUIVI PHYSIQUE) :</h2>");
                out.println("<p style='color:red; font-weight:bold;'>" + e.getMessage() + "</p>");
                out.println("<br><a href='suivi_physique.jsp'>Retour</a>");
            }
            
        } catch (Exception e) {
            response.getWriter().println("Erreur : " + e.getMessage());
        }
    }
}