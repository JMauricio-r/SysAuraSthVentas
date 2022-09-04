
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aurasth.ventas.entidadesdenegocio.DetalleVenta"%>
<% DetalleVenta detalleVenta = (DetalleVenta) request.getAttribute("detalleVenta");%>
<!DOCTYPE html>
<html>
     <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Venta</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Venta</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" value="<%=detalleVenta.getCantidad()%>" disabled>
                        <label for="txtCantidad">Cantidad</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" value="<%=detalleVenta.getTotal()%>" disabled>
                        <label for="txtTotal">Total</label>
                    </div> 
                   
                    <div class="input-field col l4 s12">
                        <input id="txtProducto" type="text" value="<%=detalleVenta.getProducto().getNombre() %>" disabled>
                        <label for="txtProducto">Producto</label>
                    </div> 
                        <div class="input-field col l4 s12">
                        <input id="txtVenta" type="text" value="<%=detalleVenta.getVenta().getId() %>" disabled>
                        <label for="txtVenta">Venta</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="DetalleVenta?accion=edit&id=<%=detalleVenta.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="DetalleVenta" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
