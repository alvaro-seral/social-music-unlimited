<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%
Object error = request.getParameter("error");
String msgErrorNombre = "<br>";
String msgErrorCorreo = "<br>";
if (error != null) {
	if (error.toString().equals("user")) msgErrorNombre = "Ese nombre de usuario ya está en uso";
	if (error.toString().equals("mail")) msgErrorCorreo = "Ya existe una cuenta asociada a ese correo";
}
%>
    
<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Registrarse 1</title>

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
                            <div class="col-lg-6 bg-signin1-image"></div>
                            
                            <!-- Derecha : Cuerpo -->
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <!-- Titulo -->
                                    <div class="mb-5">
                                        <h1 class="text-center texto-titulo"> ¡Regístrate ahora! </h1>
                                        <div class="progress mt-4 mx-5">
											<div class="progress-bar" role="progressbar" style="width: 33%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">33%</div>
										</div>
                                    </div>

                                    <!-- Linea separadora -->
                                    <hr class="mb-5 mx-5 px-2">

                                    <!-- "Resgitrarse" -->
                                    <form action="Registrarse1Servlet" method="post">
                                    	<div class="mx-5 px-2">
                                            <h4> ¿Cómo quieres que te conozcan? </h4>
                                            <input class="form-control rounded-3" required type="text" name="nombre" placeholder="Nombre de usuario">
                                        </div>
                                        <div class="mb-4 mx-5 px-2 texto-error">
                                            <%= msgErrorNombre %>
                                        </div>
                                        <div class="mx-5 px-2">
                                            <h4> ¿Cuál es tu dirección de correo? </h4>
                                            <input class="form-control rounded-3" required type="email" name="correo" placeholder="Correo electrónico">
                                        </div>
                                        <div class="mb-5 mx-5 px-2 texto-error">
                                            <%= msgErrorCorreo %>
                                        </div>
                                        <div class="mx-5 px-2 text-center">
                                            <input class="btn btn-si rounded-5" required type="submit" value="Siguiente">
                                        </div>
                                    </form>
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