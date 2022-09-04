<%@page import="aurasth.ventas.entidadesdenegocio.DetalleVenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% DetalleVenta detalleVenta = (DetalleVenta) request.getAttribute("detalleVenta");%>
<!DOCTYPE html>
<html>
   <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Detalle Venta</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar detalle</h5>
            <form action="DetalleVenta" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=detalleVenta.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="Cantidad" value="<%=detalleVenta.getCantidad()%>" required class="validate" maxlength="30">
                        <label for="txtCantidad">Cantidad</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" name="Total" value="<%=detalleVenta.getTotal()%>" required class="validate" maxlength="30">
                        <label for="txtTotal">Total</label>
                    </div> 
                   
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Producto/select.jsp">                           
                            <jsp:param name="id" value="<%=detalleVenta.getIdProducto() %>" />  
                        </jsp:include>  
                        <span id="slDetalleVenta_error" style="color:red" class="helper-text"></span>
                    </div>
                        <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Venta/select.jsp">                           
                            <jsp:param name="id" value="<%=detalleVenta.getIdVenta() %>" />  
                        </jsp:include>  
                        <span id="slDetalleVenta_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>
                    <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="DetalleVenta" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />  
</html>
