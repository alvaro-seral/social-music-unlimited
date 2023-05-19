<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Object error = request.getParameter("error");
String msgErrorNombre = "<br>";
String msgErrorContrasenya = "<br>";
if (error != null) {
	if (error.toString().equals("user")) msgErrorNombre = "Nombre de usuario no encontrado";
	if (error.toString().equals("contra")) msgErrorContrasenya = "Contraseña incorrecta";
}
%>

<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Iniciar sesión</title>

    <!-- Estilos propios -->
    <link href="estilo.css" rel="stylesheet">
	<!-- Estilos bootstrap -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body class="bg-dark">
    <div class="container">
        
        <div class="row justify-content-center">
            <div class="col-lg-12 col-md-9">
                <div class="card o-hidden border-0 my-5">
                    <div class="card-body p-0">
            
                        <div class="row">
                            <!-- Izquierda : Imagen -->
                            <div class="col-lg-6 bg-login-image"></div>
                            
                            <!-- Derecha : Cuerpo -->
                            <div class="col-lg-6 bg-light">
                                <div class="p-5">
                                    <!-- Logo -->
                                    <div class="mb-5">
                                        <img src="logo.png" alt="Social Music" style="max-width:100%">
                                    </div>

                                    <!-- "Iniciar sesion" -->
                                    <form action="IniciarSesionServlet" method="post">
                                        <div class="mx-5 px-2">
                                            <input class="form-control rounded-3" required type="text" name="nombre" placeholder="Nombre de usuario">
                                        </div>
                                        <div class="mb-3 mx-5 px-2 texto-error">
                                            <%= msgErrorNombre %>
                                        </div>
                                        <div class="mx-5 px-2">
                                            <input class="form-control rounded-3" required type="password" name="contrasenya" placeholder="Contraseña">
                                        </div>
                                        <div class="mb-4 mx-5 px-2 texto-error">
                                            <%= msgErrorContrasenya %>
                                        </div>
                                        <div class="mx-5 px-2 text-center">
                                            <input class="btn btn-si rounded-5" required type="submit" value="Inicar sesión">
                                        </div>
                                    </form>
                                    
                                    <!-- Linea separadora -->
                                    <hr class="my-4 mx-5 px-2">

                                    <!-- "Registrate ahora" -->
                                   	<div class="mx-5 px-2 text-center">
                                   		<a class="btn btn-si rounded-5" href="signin1.jsp"> ¡Regístrate ahora! </a>
                                   	</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>