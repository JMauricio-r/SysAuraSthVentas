

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Detalle de la Venta</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Detalle de la Venta</h5>
            <form action="DetalleVenta" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    
                     <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="Cantidad" required class="validate" maxlength="30">
                        <label for="txtCantidad">Cantidad</label>
                    </div>   
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" name="Total" required class="validate" maxlength="30">
                        <label for="txtTotal">Total</label>
                    </div>   
                   
                </div>
                 <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Producto/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slProducto_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Venta/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slVenta_error" style="color:red" class="helper-text"></span>
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
    </body>
</html>

