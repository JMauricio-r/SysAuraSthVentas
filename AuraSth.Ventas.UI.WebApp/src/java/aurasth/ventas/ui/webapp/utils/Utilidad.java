package aurasth.ventas.ui.webapp.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilidad {
    
    public static String GetParameter(HttpServletRequest request, String pKey, String pDefault) {
        String result = "";
        String value = request.getParameter(pKey);
        if (value != null && value.trim().length() > 0) {
            result = value;
        } else {
            result = pDefault;
        }
        return result;
    }

    public static void EnviarError(String pError, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", pError);
        request.getRequestDispatcher("Views/Shared/error.jsp").forward(request, response);
    }

    public static String ObtenerRuta(HttpServletRequest request, String pStrRuta) {
        return request.getContextPath() + pStrRuta;
    }
}
