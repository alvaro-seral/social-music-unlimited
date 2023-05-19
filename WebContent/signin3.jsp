<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>

<% ArrayList<String> nomGrupos = (ArrayList<String>)request.getSession().getAttribute("nomGrupos"); %>

<%
Object error = request.getParameter("error");
String msgErrorGrupos = "<br>";
if (error != null) {
	if (error.toString().equals("contra")) msgErrorGrupos = "Hay que seleccionar un 1 género de música como mínimo";
}
%>

<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Registrarse 3</title>
	
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
                            <div class="col-lg-6 bg-signin3-image"></div>
                            
                            <!-- Derecha : Cuerpo -->
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <!-- Titulo -->
                                    <div class="mb-5">
                                        <h1 class="text-center texto-titulo"> ¡Regístrate ahora! </h1>
                                        <div class="progress mt-4 mx-5">
											<div class="progress-bar" role="progressbar" style="width: 99%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">99%</div>
										</div>
                                    </div>

                                    <!-- Linea separadora -->
                                    <hr class="my-5 mx-5 px-2">

                                    <!-- "Resgitrarse" -->
                                    <form action="Registro3Servlet" method="post">
                                        <div class="mx-5 px-2">
                                            <h4> ¿Qué géneros disfrutas más? </h4>
                                            <div class="form-control rounded-3" style="height: 10rem; overflow:auto;">
                                            	<% for (String i:nomGrupos) { %>
												<input class="form-check-input mx-3 mb-3 check" type="checkbox" name="grupos" value="<%= i %>"> 
												<label class="form-check-label" for="flexCheckDefault"> <%= i %> </label> <br>
												<% } %>
                                            </div>
										</div>
                                        <div class="mb-5 mx-5 px-2 texto-error">
                                        	<%= msgErrorGrupos %>
                                        </div>
                                        <div class="mx-5 px-2 text-center">
                                            <input class="btn btn-si rounded-5" required type="submit" value="Crear cuenta">
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