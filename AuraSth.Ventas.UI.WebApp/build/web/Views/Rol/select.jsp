<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aurasth.ventas.entidadesdenegocio.Rol"%>
<%@page import="aurasth.ventas.logicadenegocio.*"%> 
<%@page import="java.util.ArrayList"%>

<%
    RolBL _rolBL = new RolBL();
    ArrayList<Rol> roles = _rolBL.ObtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slRol" name="idRol">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Rol rol : roles) {%>
    <option <%=(id == rol.getId()) ? "selected" : ""%>  value="<%=rol.getId()%>"><%= rol.getNombre()%></option>
    <%}%>
</select>
<label for="idRol">Rol</label>