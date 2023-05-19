<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UsuariosVO"%>
<%@ page import="model.EventosVO"%>
<%@ page import="model.ComentariosEventVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.LocalDate"%>

<% UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario"); 
   ArrayList<String> gruposUsuario = (ArrayList<String>)request.getSession().getAttribute("gruposUsuario"); 
   String grupoActual = (String)request.getSession().getAttribute("grupoActual");
   ArrayList<String> nomGrupos = (ArrayList<String>)request.getSession().getAttribute("nomGrupos");
   ArrayList<Boolean> boolGrupos = (ArrayList<Boolean>)request.getSession().getAttribute("boolGrupos"); %>
   
<% String start = request.getParameter("start");
   String end = request.getParameter("end");
   if (start==null) start="1";   
   if (end==null) end="7";
   int startAsInt = new Integer(start);
   int endAsInt = new Integer(end);
   
   EventosVO eventoActual = (EventosVO)request.getSession().getAttribute("fuenteActual");
   ArrayList<ComentariosEventVO> comentarios = (ArrayList<ComentariosEventVO>)request.getSession().getAttribute("comentarios");
%>

<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>ComentariosE</title>
	
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
	                 	<option selected hidden> <%= grupoActual %> </option>
	                 	<% for (String i:gruposUsuario) { %>
	                     <option class="sel-opt" value="<%= i %>"> <%= i %> </option>
	                     <% } %>
	                 </select>
	            	</div>
	            </form>
	            <!-- Gestionar grupos -->
	            <div class="mx-4 my-2">
	                <a href="Grupos.jsp" class="btn btn-normal rounded-5"> Gestionar grupos </a>
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
							<button class="btn btn-normal rounded-5" type="button" data-toggle="modal" data-target="#informar"> Informar administrador </button>
		                </div>
		                <% if (usuario.getTipo().equals("Normal")) { %>
	                    <div class="px-3 my-3">
	                    	<button class="btn btn-normal rounded-5" type="button" data-toggle="modal" data-target="#solicitar"> Solicitar cuenta especial </button>
	                    </div>
	                    <% } %>
	                    <div class="px-3 my-3">
	                        <a class="btn btn-normal rounded-5" href="eliminar.jsp"> Eliminar cuenta </a>
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
	    
	    	<!-- Evento referente -->
	        <div class="card shadow px-2 mb-4">
	            <h5 class="mx-5 mt-4"> Evento </h5>
                <hr>
                <div id="<%= eventoActual.getId() %>" class="mx-5 my-4">
	        		<h4> <%= eventoActual.getNombre() %> </h4>	
	        		<div class="row">
	        			<% LocalDate fecha = eventoActual.getFechaEvento().toLocalDate(); %>
	        			<div class="col-4">
	        				<p> Fecha: <%= fecha.getDayOfMonth() %> / <%= fecha.getMonthValue() %> / <%= fecha.getYear() %> </p>
	        			</div>
	        			<div class="col">
	        				<p> Lugar: <%= eventoActual.getLugar() %> </p>
	        			</div>
	        		</div>
	        		<p> <%= eventoActual.getDescripcion() %> </p>
	        	</div>
	        </div>
	        
	        <!-- Subir comentario -->
	        <div class="card shadow px-4 mb-4">
	            <div class="card-body">
                	<form action="SubirComentarioE" method="post">
	                	<div class="row d-flex align-items-center">
	                		<div class="col-9 mx-3">
	                			<input class="form-control rounded-3" required type="text" name="descripcion" placeholder="Comenta tu punto de vista con respecto al evento...">
	                		</div>
	                		<div class="col mx-3">
	                			<input class="btn btn-peq-s rounded-5 my-1" required type="submit" value="Comentar">
	                		</div>
	                	</div>
                    </form>
	            </div>
	        </div>
	        
	        <!-- Listado comentarios -->
	        <div class="card shadow px-2 my-4">
	        	<h5 class="mx-5 mt-4"> Comentarios </h5>
                <hr>
	        	<% int count=0; 
	        	   if (comentarios.size() > 0) { %>
		        	<% for (ComentariosEventVO c:comentarios) { 
		        	       count++; %>
			        	<div id="<%= c.getId() %>" class="mx-5 my-4">
			        		<h4> <%= c.getNombre() %> </h4>	
			        		<p> <%= c.getDescripcion() %> </p>
			        	</div>
				        <% if (comentarios.get(comentarios.size()-1).getId() != c.getId()) { %>
				        	<hr class="mx-3">
				        <% } %>
		        	<% } %>
		        <% } else { %>
		        	<div class="mx-5 mt-4 text-center">
		        		<p><i> No hay comentarios que mostrar... <br> ¡Anímate y comenta algo! </i></p>
		        	</div>
		        <% } %>
	        </div>
	        
	        <!-- Páginas anterior y posterior, y botón de volver -->
	        <div class="row my-4 justify-content-center">
	        	<div class="col">
	        		<div class="card shadow px-2 mb-4">
	        			<div style="text-align:center" class="card-body">
	        				<a <% if (startAsInt>1) { %> href="MostrarComentariosE?start=<%=startAsInt-endAsInt%>&end=<%=endAsInt%>" <% } %> > 
	        					<button class="btn btn-peq-s rounded-5 my-1" <% if (startAsInt==1) { %> disabled <% } %> > &lt;- </button> 
	        				</a>
	        			</div>
	        		</div>
	        	</div>
	        	<div class="col">
	        		<div class="card shadow px-2 mb-4">
	        			<div style="text-align:center" class="card-body">
	        				<a href="Eventos.jsp#<%= eventoActual.getId() %>" > 
	        					<button class="btn btn-peq-n rounded-5 my-1"> Volver </button> 
	        				</a>
	        			</div>
	        		</div>
	        	</div>
	        	<div class="col">
	        		<div class="card shadow px-2 mb-4">
	        			<div style="text-align:center" class="card-body">
	        				<a <% if (endAsInt==count) { %> href="MostrarComentariosE?start=<%=startAsInt+endAsInt%>&end=<%=endAsInt%>" <% } %> > 
	        					<button class="btn btn-peq-s rounded-5 my-1" <% if (endAsInt>count) { %> disabled <% } %> > -&gt; </button> 
	        				</a>
	        			</div>
	        		</div>
	        	</div>
	        </div>
	    </div>
	
	    <!-- Derecha -->
	    <div class="col-3"></div>

	</div>
	
	<!-- Gestionar grupos -->    
    <div class="modal fade" id="gestionar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title mx-2"> Informar administrador </h5>
                </div>
                <form action="GestionarGrupos" method="post">
	                <div class="modal-body">
	                    <p class="mx-2"> Únete a nuevos grupos o sal de aquellos a los que perteneces: </p>
	                    <div class="form-control rounded-3" style="height: 10rem; overflow:auto;">
                        	<% String aux = "";
                        	for (int i=0; i<nomGrupos.size(); i++) { 
                        		aux=nomGrupos.get(i); %>
                        		<% if (boolGrupos.get(i)==true) { %>
								<input class="form-check-input mx-3 mb-3 check" checked type="checkbox" name="grupos" value="<%= aux %>"> 
								<% } else { %>
								<input class="form-check-input mx-3 mb-3 check" type="checkbox" name="grupos" value="<%= aux %>"> 
								<% } %>
								<label class="form-check-label" for="flexCheckDefault"> <%= aux %> </label> <br>
							<% } %>
                        </div>
	                    <div class="my-4 px-2 text-center">
	                        <button type="button" class="btn btn-peq-n rounded-5 mx-3" data-dismiss="modal"> Cancelar </button>
	                        <input class="btn btn-peq-s rounded-5 mx-3" required type="submit" value="Guardar">
	                    </div>
	                </div>
	            </form>
            </div>
        </div>
    </div>

	<!-- Informar administrador -->    
    <div class="modal fade" id="informar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title mx-2"> Informar administrador </h5>
                </div>
                <form action="InformarAdministrador" method="post">
	                <div class="modal-body">
	                    <p class="mx-2"> Redacta y envía información, propuestas o quejas relativas a Social Music Unlimited: </p>
	                    <div class="px-2">
	                        <input class="form-control rounded-3" required type="text" name="texto" placeholder="Escribe aquí...">
	                    </div>
	                    <div class="my-4 px-2 text-center">
	                        <button type="button" class="btn btn-peq-n rounded-5 mx-3" data-dismiss="modal"> Cancelar </button>
	                        <input class="btn btn-peq-s rounded-5 mx-3" required type="submit" value="Enviar">
	                    </div>
	                </div>
	            </form>
            </div>
        </div>
    </div>
    
    <!-- Solicitar cuenta especial -->    
    <div class="modal fade" id="solicitar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title mx-2"> Solicitar cuenta especial </h5>
                </div>
                <form action="SolicitarEspecial" method="post">
                    <div class="modal-body">
                    	<p class="mx-2"> ¿Deseas enviar una solicitud para obtener el privilegio de publicar eventos? </p>
	                    <div class="my-4 px-2 text-center">
	                        <button type="button" class="btn btn-peq-n rounded-5 mx-3" data-dismiss="modal"> Cancelar </button>
	                        <input class="btn btn-peq-s rounded-5 mx-3" required type="submit" value="Confirmar">
	                    </div>
	                </div>
	            </form>
            </div>
        </div>
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
        
	<!-- Scripts js bootstrap -->
	<script src="jquery.min.js"></script>
    <script src="bootstrap.bundle.min.js"></script>
</body>
</html>