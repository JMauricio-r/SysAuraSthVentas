
package aurasth.ventas.ui.webapp.controllers;

import aurasth.ventas.entidadesdenegocio.Rol;
import aurasth.ventas.logicadenegocio.RolBL;
import aurasth.ventas.ui.webapp.utils.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RolServlet", urlPatterns = {"/Rol"})
public class RolServlet extends HttpServlet {
    
    private RolBL _rolBL = new RolBL();
    
    private Rol obtenerRol(HttpServletRequest request){
        String accion = Utilidad.GetParameter(request, "accion", "index");
        Rol rol = new Rol();
        if (accion.equals("create")== false) {
            rol.setId(Integer.parseInt(Utilidad.GetParameter(request, "id", "0")));
        } 
        rol.setNombre(Utilidad.GetParameter(request, "nombre", ""));
        if(accion.equals("index")){
            rol.setTop_aux(Integer.parseInt(Utilidad.GetParameter(request, "top_aux", "10")));
            rol.setTop_aux(rol.getTop_aux() == 0 ? Integer.MAX_VALUE : rol.getTop_aux());
        }
        return rol;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Rol rol = new Rol();
            rol.setTop_aux(10);
            ArrayList<Rol> roles = _rolBL.Buscar(rol);
            request.setAttribute("roles", roles);
            request.setAttribute("top_aux", rol.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response);
        } catch (Exception e) {
            Utilidad.EnviarError(e.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        try {
            Rol rol = obtenerRol(request);
            ArrayList<Rol> roles = _rolBL.Buscar(rol);
            request.setAttribute("roles", roles);
            request.setAttribute("top_aux", rol.getTop_aux());
            request.getRequestDispatcher("Views/Rol/index.jsp").forward(request, response);
        } catch (Exception e) {
            Utilidad.EnviarError(e.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        request.getRequestDispatcher("Views/Rol/create.jsp");
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request);
            int result = _rolBL.Agregar(rol);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.EnviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.EnviarError(ex.getMessage(), request, response);
        }
    }
    
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request);
            Rol rol_result = _rolBL.ObtenerPorId(rol);
            if (rol_result.getId() > 0) {
                request.setAttribute("rol", rol_result);
            } else {
                Utilidad.EnviarError("El Id:" + rol.getId()
                        + " no existe en la tabla de Rol", request, response);
            }
        } catch (Exception ex) {
            Utilidad.EnviarError(ex.getMessage(), request, response);
        }
    }
 
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request);
            int result = _rolBL.Modificar(rol);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.EnviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.EnviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Rol/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Rol rol = obtenerRol(request);
            int result = _rolBL.Eliminar(rol);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.EnviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.EnviarError(ex.getMessage(), request, response);
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
