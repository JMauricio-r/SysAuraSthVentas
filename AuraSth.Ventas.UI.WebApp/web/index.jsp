<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aurasth.ventas.ui.webapp.utils.Utilidad"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Compiled and minified CSS -->
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">-->
        <link rel="stylesheet" href="<%=Utilidad.ObtenerRuta(request, "/wwwroot/lib/materialize/css/materialize.min.css")%>">
        <script
            src="https://code.jquery.com/jquery-3.3.1.js"
        integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
        <!-- Compiled and minified JavaScript -->
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>-->
        <script src="<%=Utilidad.ObtenerRuta(request, "/wwwroot/lib/materialize/js/materialize.min.js")%>"></script>
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body>
        <nav>
    <div class="nav-wrapper blue">
        <a href="Home" class="brand-logo">SysSeguridad</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>       
        <ul class="right hide-on-med-and-down">  
            <li><a href="Home">Inicio</a></li>
            <li><a href="Contacto">Contactos</a></li>
            <li><a href="Empresa">Empresas</a></li>
            <li><a href="Usuario">Usuarios</a></li>
            <li><a href="Rol/index.jsp">Roles</a></li>
            <li><a href="Usuario?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="Usuario?accion=login">Cerrar sesión</a></li>
        </ul>
    </div>
</nav>
    </body>
</html>
