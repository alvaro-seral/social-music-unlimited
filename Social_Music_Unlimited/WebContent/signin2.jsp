<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%
Object error = request.getParameter("error");
String msgErrorContrasenya = "<br>";
String msgErrorCorreo = "<br>";
if (error != null) {
	if (error.toString().equals("contra")) msgErrorContrasenya = "La contraseña debe ser la misma en ambos campos";
}
%>
    
<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Registrarse 2</title>

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
                            <div class="col-lg-6 bg-signin2-image"></div>
                            
                            <!-- Derecha : Cuerpo -->
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <!-- Titulo -->
                                    <div class="mb-5">
                                        <h1 class="text-center texto-titulo"> ¡Regístrate ahora! </h1>
                                        <div class="progress mt-4 mx-5">
											<div class="progress-bar" role="progressbar" style="width: 66%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">66%</div>
										</div>
                                    </div>

                                    <!-- Linea separadora -->
                                    <hr class="my-5 mx-5 px-2">

                                    <!-- "Resgitrarse" -->
                                    <form action="Registrarse2Servlet" method="post">
                                    	<div class="mx-5 px-2">
                                            <h4> Establece una contraseña: </h4>
                                            <input class="form-control rounded-3" required type="password" name="contra" placeholder="Contraseña">
                                        </div>
                                        <div class="mb-4 mx-5 px-2 texto-error">
                                            <br>
                                        </div>
                                        <div class="mx-5 px-2">
                                            <h4> Repite la contraseña: </h4>
                                            <input class="form-control rounded-3" required type="password" name="contraRep" placeholder="Repetir contraseña">
                                        </div>
                                        <div class="mb-5 mx-5 px-2 texto-error">
                                            <%= msgErrorContrasenya %>
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