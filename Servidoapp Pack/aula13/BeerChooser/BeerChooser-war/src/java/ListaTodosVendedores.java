/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import entidades.Vendedor;
import entidades.VendedorFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrador
 */
@WebServlet(name = "ListaTodosVendedores", urlPatterns = {"/ListaTodosVendedores"})
public class ListaTodosVendedores extends HttpServlet {
    @EJB
    private VendedorFacade vendedorFacade;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListaTodosVendedores</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Lista Todos Vendedores</h3>");
            out.println("<br>");
            
            List vendedor = vendedorFacade.findAll();
            for (Iterator i = vendedor.iterator(); i.hasNext();){
            Vendedor vend =(Vendedor) i.next();
            out.println("<p>");
            out.println("Vendedor" + vend.getId());
            out.println("nome:" + vend.getNome());
            out.println("</p>");
            }
            out.println("<a herf=\"index.jsp\">Novo</a>");
            out.println("</body>");
            out.println("</html>");
             
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
