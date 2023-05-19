<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%
Object error = request.getParameter("error");
String msgErrorContra = "<br>";
if (error != null) {
	if (error.toString().equals("contra")) msgErrorContra = "Contraseña incorrecta";
}
%>
    
<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Eliminar</title>

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
                        	<div class="col-lg-6">
								<div id="carouselExampleControls" class="carousel slide carousel-fade" data-ride="carousel">
								 	<div class="carousel-inner" style="text-center">
									    <div class="carousel-item active">
									      <img class="d-block" src="triste/1.jpg" style="object-fit: cover; height:42rem;">
									    </div>
									    <div class="carousel-item">
									      <img class="d-block w-100" src="triste/2.jpg" style="object-fit: cover; height:42rem;">
									    </div>
									    <div class="carousel-item">
									      <img class="d-block w-100" src="triste/3.jpg" style="object-fit: cover; height:42rem;">
									    </div>
									    <div class="carousel-item">
									      <img class="d-block w-100" src="triste/4.jpg" style="object-fit: cover; height:42rem;">
									    </div>
									    <div class="carousel-item">
									      <img class="d-block w-100" src="triste/5.jpg" style="object-fit: cover; height:42rem;">
										</div>
							 		</div>
								<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
								    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
								    
								  </a>
								  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
								    <span class="carousel-control-next-icon" aria-hidden="true"></span>
								  </a>
								</div>
							</div>
                            <!-- Derecha : Cuerpo -->
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <!-- Titulo -->
                                    <div class="mb-5">
                                        <h1 class="text-center texto-titulo"> Eliminar cuenta </h1>
                                    </div>

                                    <!-- Linea separadora -->
                                    <hr class="my-5 mx-5 px-2">

                                    <!-- Mensajes de tristeza y accón de eliminar -->
                                    <form action="EliminarCuenta" method="post">
	                                    <div class="text-center">
	                                    	<h4 class="mx-5 px-2 mb-4"> ¡No te vayas! </h4>
	                                    	<h4 class="mx-5 px-2 mb-4"> Estaremos tristes sin ti... </h4>
	                                    	<h4 class="mx-5 px-2 mb-4"> ¿Realmente deseas eliminar la cuenta? </h4>
	                                    	<h4 class="mx-5 px-2 mb-4"> Si es así confirma tu contraseña... </h4>
	                                    </div>
                                    	<div class="mx-5 px-2">
                                            <input class="form-control rounded-3" required type="password" name="contrasenya" placeholder="Contraseña">
                                        </div>
                                        <div class="mb-4 mx-5 px-2 texto-error">
                                            <%= msgErrorContra %>
                                        </div>
                                        <div class="mx-5 px-2 text-center">
                                            <input class="btn btn-si rounded-5" required type="submit" value="Eliminar">
                                        </div>
                                    </form>
                                    <hr class="my-4 mx-5 px-2">
                                    <div class="mx-5 px-2 text-center">
                                    	<a class="btn btn-normal rounded-5" href="Publicaciones.jsp"> Volver </a>
                                    </div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

	<!-- Scripts js bootstrap -->
    <script src="jquery.min.js"></script>
    <script src="bootstrap.bundle.min.js"></script>
</body>
</html>