<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UsuariosVO"%>
<%@ page import="model.GruposVO"%>
<%@ page import="model.EventosVO" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.LocalDate"%>

<% UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario"); 
   GruposVO grupoActual = (GruposVO)request.getSession().getAttribute("grupoActual");
   ArrayList<String> nomGrupos = (ArrayList<String>)request.getSession().getAttribute("nomGrupos"); 
   
   ArrayList<EventosVO> eventosSiempre = (ArrayList<EventosVO>)request.getSession().getAttribute("eventosSiempre");
   ArrayList<EventosVO> eventosAnyo = (ArrayList<EventosVO>)request.getSession().getAttribute("eventosAnyo");
   %>

<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>EstadisticasDeGrupo</title>
	
    <!-- Estilos css propios -->
    <link href="estilo.css" rel="stylesheet">
	<!-- Estilos css bootstrap -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body id="page-top" class="bg-dark">

    <!-- Barra superior -->
    <nav class="mb-2 bg-light shadow">
	    <div class="d-flex flex-xl-row flex-column justify-content-between align-items-center">
	    	<!-- Logo -->
	        <div class="mx-2 my-1">
	            <img src="logo.png" alt="Social Music" style="max-width:16rem">
	        </div>
	        
	        <div class="d-flex flex-sm-row flex-column justify-content-between align-items-center">
	        	<!-- Cambiar grupo actual -->
	            <form action="CambiarGrupoActual" method="post">
	            	<div class="mx-4 my-2">
	                 <select id="cambiarGrupoActual" name="cambiarGrupoActual" class="btn btn-normal rounded-5" onchange="this.form.submit()">
	                 	<option selected hidden> <%= grupoActual.getNombre() %> </option>
	                 	<% for (String i:nomGrupos) { %>
	                     <option class="sel-opt" value="<%= i %>"> <%= i %> </option>
	                     <% } %>
	                 </select>
	            	</div>
	            </form>
	            <!-- Realizar evento -->
	            <div class="mx-4 my-2">
	                <button class="btn btn-normal rounded-5" type="button" data-toggle="modal" data-target="#realizarEvento"> Realizar evento </button>
	            </div>
	        </div>
	        
	        <!-- Menú desplegable -->
	        <div class="ml-2">
	            <div class="drop-down">
	                <button type="button" class="btn btn-menu rounded-0"> 
	                	<%= usuario.getNombre() %> <br> <sup> Usuario <%= usuario.getTipo() %> </sup>
	                </button>
	                <div class="drop-down-content justify-content-center">
		                <div class="px-3 my-3">
							<form action="Informes" method="post">
		                		<input class="btn btn-normal rounded-5" required type="submit" value="Gestionar informes">
		                	</form>
		                </div>
	                    <div class="px-3 my-3">
	                    	<form action="Especiales" method="post">
		                		<input class="btn btn-normal rounded-5" required type="submit" value="Gestionar especiales">
		                	</form>
	                    </div>
	                    <div class="px-3 my-3">
	                        <button class="btn btn-normal rounded-5" type="button" data-toggle="modal" data-target="#cerrar"> Cerrar sesión </button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</nav>
	
	<!-- Cuerpo -->
	<div class="row my-4 mx-2 justify-content-center">
	                
	    <!-- Izquierda -->
	    <div class="col-3"></div>
	
	    <!-- Centro -->
	    <div class="col-6">
	    
	    	<!-- Elección: Estadisticas -->
	        <div class="card shadow px-2 mb-4">
	            <div class="card-body text-center" style="display:inline-block">
	            	<h5> Ver estadísticas: </h5>
	                <div class="row text-center" >
	                	<form class="col" action="MostrarDeGrupo" method="post">
	                    	<input class="btn btn-no-sel rounded-5 mx-2 my-1" required type="submit" value="de Grupo">
	                    </form>
	                    <form class="col" action="IniciarEventos" method="post">
	                    	<input class="btn btn-no-sel rounded-5 mx-2 my-1" required type="submit" value="por Evento">
	                    </form>
	                    <form class="col" action="MostrarTopEventos" method="post">
	                    	<input class="btn btn-sel rounded-5 mx-2 my-1" required type="submit" value="Top Eventos">
	                    </form>
	                </div>
	            </div>
	        </div>
	        
	        <!-- Estadisticas top evento (año) -->
	        <div class="card shadow px-2 my-4">
	        	<div class="card-body">
		        	<div class="text-center">
		        		<h5> Eventos del año con más usuarios interesados </h5>	
		        		<hr>
		        	</div>
		        	<% if (eventosAnyo.size() > 0) { %>
			        	<% for (EventosVO e:eventosAnyo) { %>
				        	<div id="<%= e.getId() %>" class="mx-5 my-4">
				        		<h5> <%= e.getEmpresa() %> </h5>	
				        		<div class="row">
				        			<% LocalDate fecha = e.getFechaEvento().toLocalDate(); %>
				        			<div class="col-4">
				        				<p> Fecha: <%= fecha.getDayOfMonth() %> / <%= fecha.getMonthValue() %> / <%= fecha.getYear() %> </p>
				        			</div>
				        			<div class="col">
				        				<p> Lugar: <%= e.getLugar() %> </p>
				        			</div>
				        		</div>
				        		<p> <%= e.getDescripcion() %> </p>
				        		<div class="row">
				        			<div class="col-4">
				        				<p> Número de interesados: </p>
				        			</div>
				        			<div class="col">
				        				<p> <b> <%= e.getNumApuntados() %> usuarios </b> </p>
				        			</div>
				        		</div>
				        		<% float porcentaje = ((float)e.getNumApuntados()/(float)grupoActual.getNumParticipantes())*100; %>
				        		<div class="row">
				        			<div class="col-4">
				        				<p> Porcentaje de interesados: </p>
				        			</div>
				        			<div class="col">
				        				<p> <b> <%= porcentaje %> % </b> </p>
				        			</div>
				        		</div>
				        	</div>
					        <% if (eventosAnyo.get(eventosAnyo.size()-1).getId() != e.getId()) { %>
					        	<hr class="mx-3">
					        <% } %>
			        	<% } %>
			        <% } else { %>
			        	<div class="mx-5 mt-4 text-center">
			        		<p><i> No se han realizado eventos este año... </i></p>
			        	</div>
			        <% } %>
			 	</div>
	        </div>
	  	
	  		<!-- Estadisticas top evento (siempre) -->
	        <div class="card shadow px-2 my-4">
	        	<div class="card-body">
		        	<div class="text-center">
		        		<h5> Eventos desde siempre con más usuarios interesados </h5>
		        		<hr>	
		        	</div>
		        	<% if (eventosSiempre.size() > 0) { %>
			        	<% for (EventosVO e:eventosSiempre) { %>
				        	<div id="<%= e.getId() %>" class="mx-5 my-4">
				        		<h4> <%= e.getEmpresa() %> </h4>	
				        		<div class="row">
				        			<% LocalDate fecha = e.getFechaEvento().toLocalDate(); %>
				        			<div class="col-4">
				        				<p> Fecha: <%= fecha.getDayOfMonth() %> / <%= fecha.getMonthValue() %> / <%= fecha.getYear() %> </p>
				        			</div>
				        			<div class="col">
				        				<p> Lugar: <%= e.getLugar() %> </p>
				        			</div>
				        		</div>
				        		<p> <%= e.getDescripcion() %> </p>
				        		<div class="row">
				        			<div class="col-4">
				        				<p> Número de interesados: </p>
				        			</div>
				        			<div class="col">
				        				<p> <b> <%= e.getNumApuntados() %> usuarios </b> </p>
				        			</div>
				        		</div>
				        		<% float porcentaje = ((float)e.getNumApuntados()/(float)grupoActual.getNumParticipantes())*100; %>
				        		<div class="row">
				        			<div class="col-4">
				        				<p> Porcentaje de interesados: </p>
				        			</div>
				        			<div class="col">
				        				<p> <b> <%= porcentaje %> % </b> </p>
				        			</div>
				        		</div>
				        	</div>
					        <% if (eventosSiempre.get(eventosSiempre.size()-1).getId() != e.getId()) { %>
					        	<hr class="mx-3">
					        <% } %>
			        	<% } %>
			        <% } else { %>
			        	<div class="mx-5 mt-4 text-center">
			        		<p><i> Nunca se ha realizado ningún evento... </i></p>
			        	</div>
			        <% } %>
			 	</div>
	        </div>
	  	
	  	</div>
	
	    <!-- Derecha -->
	    <div class="col-3"></div>

	</div>
    
    <!-- Cerrar sesión -->    
    <div class="modal fade" id="cerrar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title mx-2"> Cerrar sesión </h5>
                </div>
                <form action="CerrarSesion" method="post">
                    <div class="modal-body">
                    	<p class="mx-2"> ¿Deseas cerrar la sesión actual de la cuenta? </p>
	                    <div class="my-4 px-2 text-center">
	                        <button type="button" class="btn btn-peq-n rounded-5 mx-3" data-dismiss="modal"> Cancelar </button>
	                        <input class="btn btn-peq-s rounded-5 mx-3" required type="submit" value="Confirmar">
	                    </div>
	                </div>
	            </form>
            </div>
        </div>
    </div>
    
    <!-- Realizar evento -->    
    <div class="modal fade" id="realizarEvento" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title mx-2"> Realizar evento </h5>
                </div>
                <form action="SubirEvento" method="post">
                    <div class="modal-body">
                    	<div class="mx-2 my-4 row">
						    <label for="inputQuien" class="col-3 col-form-label">¿Quién?</label>
						    <div class="col">
						    	<input required type="text" class="form-control rounded-3" id="inputQuien" name="empresa" placeholder="Empresa organizadora">
						    </div>
						</div>
						<div class="mx-2 my-4 row">
						    <label for="inputCuando" class="col-3 col-form-label">¿Cuándo?</label>
						    <div class="col">
						    	<input required type="date" class="form-control rounded-3" id="inputCuando" name="fecha">
						    </div>
						</div>
						<div class="mx-2 my-4 row">
						    <label for="inputDonde" class="col-3 col-form-label">¿Dónde?</label>
						    <div class="col">
						    	<input required type="text" class="form-control rounded-3" id="inputDonde" name="lugar" placeholder="Lugar">
						    </div>
						</div>
						<div class="mx-2 my-4 row">
						    <label for="inputQue">¿Qué?</label>
						    <div class="col mt-2">
						    	<input required type="text" class="form-control rounded-3" id="inputQue" name="descripcion" placeholder="Descripción">
						    </div>
						</div>
	                    <div class="my-4 px-2 text-center">
	                        <button type="button" class="btn btn-peq-n rounded-5 mx-3" data-dismiss="modal"> Cancelar </button>
	                        <input class="btn btn-peq-s rounded-5 mx-3" required type="submit" value="Publicar">
	                    </div>
	                </div>
	            </form>
            </div>
        </div>
    </div>
        
	<!-- Scripts js bootstrap -->
	<script src="jquery.min.js"></script>
    <script src="bootstrap.bundle.min.js"></script>
</body>
</html>