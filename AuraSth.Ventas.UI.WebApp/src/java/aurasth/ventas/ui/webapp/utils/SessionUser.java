
package aurasth.ventas.ui.webapp.utils;

import aurasth.ventas.entidadesdenegocio.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionUser {
    
    public static void autenticarUser(HttpServletRequest request,
            Usuario pUsuario) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("auth", true);
        session.setAttribute("user", pUsuario.getLogin());
        session.setAttribute("rol", pUsuario.getRol().getNombre()); 
    }
    
    public static boolean isAuth(HttpServletRequest request){
        
        HttpSession seccion = (HttpSession) request.getSession();
        boolean auth = seccion.getAttribute("auth") != null ? 
                (boolean ) seccion.getAttribute("auth") : false;
        return auth;
    }

    public static String getUser(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) { 
            user = session.getAttribute("user") != null ? (String)
                    session.getAttribute("user") : "";
        }
        return user;
    }

    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) {
            user = session.getAttribute("rol") != null ?
                    (String) session.getAttribute("rol") : "";
        }
        return user;
    }

    public static void authorize(HttpServletRequest request, HttpServletResponse
            response,IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionUser.isAuth(request)) {
            pIAuthorize.authorize();
        } else {
            response.sendRedirect("Usuario?accion=login");
        }
    }

    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
}
