<%-- 
    Document   : index
    Created on : 2 sep. 2022, 19:15:28
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aurasth.ventas.entidadesdenegocio.DetalleVenta"%>
<%@page import="aurasth.ventas.entidadesdenegocio.Producto"%>
<%@page import="aurasth.ventas.entidadesdenegocio.Venta"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<DetalleVenta> detalleVentas = (ArrayList<DetalleVenta>) request.getAttribute("detalleVentas");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (detalleVentas == null) {
        detalleVentas = new ArrayList();
    } else if (detalleVentas.size() > numReg) {
        double divNumPage = (double) detalleVentas.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Buscar Detalles de la venta</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Detalle</h5>
            <form action="DetalleVenta" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="cantidad">
                        <label for="txtCantidad">Cantidad</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" name="total">
                        <label for="txtTotal">Total</label>
                    </div> 
    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Producto/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Venta/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="DetalleVenta?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>Cantidad</th>  
                                    <th>Total</th> 
                                    <th>Producto</th>  
                                    <th>Venta</th> 
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (DetalleVenta detalleVenta : detalleVentas) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                       
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=detalleVenta.getCantidad()%></td>  
                                    <td><%=detalleVenta.getTotal()%></td> 
                                    <td><%=detalleVenta.getProducto().getNombre()%></td> 
                                    <td><%=detalleVenta.getVenta().getId()%></td> 
                                    <td>
                                        <div style="display:flex">
                                             <a href="DetalleVenta?accion=edit&id=<%=detalleVenta.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="DetalleVenta?accion=details&id=<%=detalleVenta.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="DetalleVenta?accion=delete&id=<%=detalleVenta.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>             
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>