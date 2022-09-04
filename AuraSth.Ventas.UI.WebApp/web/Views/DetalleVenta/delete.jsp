<%@page contentType="text/html" pageEncoding="UTF-8"%>
<<%@page import="aurasth.ventas.entidadesdenegocio.DetalleVenta" %>
<% DetalleVenta detalleVenta = (DetalleVenta) request.getAttribute("detalleVenta");%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Detalle Venta</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Detalle</h5>          
            <form action="DetalleVenta" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=detalleVenta.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                    <input disabled  id="txtCantidad" type="text" value="<%=detalleVenta.getCantidad()%>">
                    <label for="txtCantidad">Cantidad</label>
                </div> 
                     <div class="row">
                    <div class="input-field col l4 s12">
                    <input disabled  id="txtTotal" type="text" value="<%=detalleVenta.getTotal()%>">
                    <label for="txtTotal">Total</label>
                </div> 
                    <div class="input-field col l4 s12">
                        <input id="txtProducto" type="text" value="<%=detalleVenta.getProducto().getNombre()%>" disabled>
                        <label for="txtProducto">Producto</label>
                    </div> 
                        <div class="input-field col l4 s12">
                        <input id="txtVenta" type="text" value="<%=detalleVenta.getVenta().getId()%>" disabled>
                        <label for="txtVenta">Venta</label>
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="DetalleVenta" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

