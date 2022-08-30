
package aurasth.ventas.ui.webapp.controllers;

import aurasth.ventas.entidadesdenegocio.*;
import aurasth.ventas.logicadenegocio.RolBL;
import aurasth.ventas.logicadenegocio.UsuarioBL;
import aurasth.ventas.ui.webapp.utils.SessionUser;
import aurasth.ventas.ui.webapp.utils.Utilidad;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "UsuarioServlet", urlPatterns = {"/Usuario"})
public class UsuarioServlet extends HttpServlet {
    
    private UsuarioBL _usuarioBL = new UsuarioBL();
    private RolBL _rolBL = new RolBL();

    private Usuario obtenerUsuario(HttpServletRequest request) {
        String accion = Utilidad.GetParameter(request, "accion", "index");
        Usuario usuario = new Usuario();
        usuario.setNombre(Utilidad.GetParameter(request, "nombre", ""));
        usuario.setApellido(Utilidad.GetParameter(request, "apellido", ""));
        usuario.setLogin(Utilidad.GetParameter(request, "login", ""));
        usuario.setIdRol(Integer.parseInt(Utilidad.GetParameter(request, "idRol", "0")));
        usuario.setEstado(Byte.parseByte(Utilidad.GetParameter(request, "estatus", "0")));

        if (accion.equals("index")) {
            usuario.setTop_aux(Integer.parseInt(Utilidad.GetParameter(request, "top_aux", "10")));
            usuario.setTop_aux(usuario.getTop_aux() == 0 ? Integer.MAX_VALUE : usuario.getTop_aux());
        }

        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpass")) {
            usuario.setContrasenia(Utilidad.GetParameter(request, "password", ""));
            usuario.setConfirmar_contrasenia(Utilidad.GetParameter(request, "confirmPassword_aux", ""));
            if (accion.equals("cambiarpass")) {
                usuario.setId(Integer.parseInt(Utilidad.GetParameter(request, "id", "0")));
            }
        } else {
            usuario.setId(Integer.parseInt(Utilidad.GetParameter(request, "id", "0")));
        }
        return usuario;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setTop_aux(10);
            ArrayList<Usuario> usuarios = _usuarioBL.BuscarIncluirRol(usuario);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("Views/Usuario/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            ArrayList<Usuario> usuarios = _usuarioBL.BuscarIncluirRol(usuario);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("Views/Usuario/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/Usuario/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            int result = _usuarioBL.Agregar(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
        private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            Usuario usuario_result = _usuarioBL.ObtenerPorId(usuario);
            if (usuario_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(usuario_result.getIdRol());
                usuario_result.setRol(_rolBL.ObtenerPorId(rol));
                request.setAttribute("usuario", usuario_result);
            } else {
                Utilidad.enviarError("El Id:" + usuario_result.getId() + 
                        " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
        
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Usuario/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            int result = _usuarioBL.Modificar(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Usuario/details.jsp").forward(request, response);
    } 
    
        private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Usuario/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            int result = _usuarioBL.Eliminar(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.cerrarSession(request);
        request.getRequestDispatcher("Views/Usuario/login.jsp").forward(request, response);
    }

    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            Usuario usuario_auth = _usuarioBL.Login(usuario);
            if (usuario_auth.getId() != 0 && usuario_auth.getLogin().equals(usuario.getLogin())) {
                Rol rol = new Rol();
                rol.setId(usuario_auth.getIdRol());
                usuario_auth.setRol(_rolBL.ObtenerPorId(rol));
                SessionUser.autenticarUser(request, usuario_auth);
                response.sendRedirect("Home");
            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.setAttribute("accion", "login");
                doGetRequestLogin(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }
  
    private void doGetRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setLogin(SessionUser.getUser(request));
            Usuario usuario_result = _usuarioBL.Buscar(usuario).get(0);
            if (usuario_result.getId() > 0) {
                request.setAttribute("usuario", usuario_result);
                request.getRequestDispatcher("Views/Usuario/cambiarPassword.jsp").forward(request, response);
            } else {
                Utilidad.enviarError("El Id:" + usuario_result.getId() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuario(request);
            String passActual = Utilidad.GetParameter(request, "passwordActual", "");
            int result = _usuarioBL.CambiarContraseña(usuario, passActual);
            if (result != 0) {
                response.sendRedirect("Usuario?accion=login");
            } else {
                Utilidad.enviarError("No se logro cambiar el password", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.GetParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doGetRequestLogin(request, response);
        } else {
            SessionUser.authorize(request, response, () -> {
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
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doGetRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.GetParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doPostRequestLogin(request, response);
        } else {
            SessionUser.authorize(request, response, () -> {
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
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doPostRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }
        

}
