
package aurasth.ventas.ui.webapp.controllers;

import aurasth.ventas.entidadesdenegocio.DetalleVenta;
import aurasth.ventas.logicadenegocio.DetalleVentaBL;
import aurasth.ventas.ui.webapp.utils.SessionUser;
import aurasth.ventas.ui.webapp.utils.Utilidad;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "DetalleVentaServlet", urlPatterns = {"/DetalleVentaServlet"})
public class DetalleVentaServlet extends HttpServlet {

  private DetalleVentaBL _detalleVentaBL = new DetalleVentaBL(); 


     private DetalleVenta obtenerDetalleVenta(HttpServletRequest request){
        String accion = Utilidad.GetParameter(request, "accion", "index");
         DetalleVenta detalleVenta = new DetalleVenta();
        if (accion.equals("create")== false) {
            detalleVenta.setId(Integer.parseInt(Utilidad.GetParameter(request, "id", "0")));
        } 
       
        detalleVenta.setCantidad(Integer.parseInt(Utilidad.GetParameter(request, "cantidad", "")));
        detalleVenta.setTotal(Integer.parseInt(Utilidad.GetParameter(request, "total", "")));
        detalleVenta.setIdProducto(Integer.parseInt(Utilidad.GetParameter(request, "idProducto", "0")));
        detalleVenta.setIdVenta(Integer.parseInt(Utilidad.GetParameter(request, "idVenta", "0")));
        if(accion.equals("index")){
            detalleVenta.setTop_aux(Integer.parseInt(Utilidad.GetParameter(request, "top_aux", "10")));
            detalleVenta.setTop_aux(detalleVenta.getTop_aux() == 0 ? Integer.MAX_VALUE : detalleVenta.getTop_aux());
        }
        return detalleVenta;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setTop_aux(10);
            ArrayList<DetalleVenta> detalleVentas = _detalleVentaBL.Buscar(detalleVenta);
            request.setAttribute("detalleVentas", detalleVenta);
            request.setAttribute("top_aux", detalleVenta.getTop_aux());
            request.getRequestDispatcher("Views/DetalleVenta/index.jsp").forward(request, response);
        } catch (Exception e) {
            Utilidad.enviarError(e.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        try {
            DetalleVenta detalleVenta = obtenerDetalleVenta(request);
            ArrayList<DetalleVenta> detalleVentas = _detalleVentaBL.Buscar(detalleVenta);
            request.setAttribute("detalleVentas", detalleVentas);
            request.setAttribute("top_aux", detalleVenta.getTop_aux());
            request.getRequestDispatcher("Views/DetalleVenta/index.jsp").forward(request, response);
        } catch (Exception e) {
            Utilidad.enviarError(e.getMessage(), request, response);
        }
    }
    
    
    
    private void doGetRequestCreate(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        request.getRequestDispatcher("Views/DetalleVenta/create.jsp");
    }
    
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            DetalleVenta detalleVenta = obtenerDetalleVenta(request);
            int result = _detalleVentaBL.Agregar(detalleVenta);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No Se Logro Registrar ", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            DetalleVenta detalleVenta = obtenerDetalleVenta(request);
            DetalleVenta detalleVenta_result = _detalleVentaBL.ObtenerPorId(detalleVenta);
            if (detalleVenta_result.getId() > 0) {
                request.setAttribute("detalleVenta", detalleVenta_result);
            } else {
                Utilidad.enviarError("El Id:" + detalleVenta.getId()
                        + " No existe", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
 
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response)
            
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/DetalleVenta/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            DetalleVenta detalleVenta = obtenerDetalleVenta(request);
            int result = _detalleVentaBL.Modificar(detalleVenta);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se actualizo el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/DetalleVenta/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/DetalleVenta/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            DetalleVenta detalleVenta = obtenerDetalleVenta(request);
            int result = _detalleVentaBL.Eliminar(detalleVenta);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se elimino el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.GetParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.GetParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
}
    
//    
//    
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try ( PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet DetalleVentaServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet DetalleVentaServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
