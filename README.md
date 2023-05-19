![Social Music Unlimited](WebContent/logo.png)

# Desarrollo y construcción de una aplicación web que cumpla los requisitos de un sistema de información

## Índice

- Objetivo y alcance funcional de la aplicación.
   - Objetivos
   - Perfiles de usuario
   - Entidades de información
   - Funcionalidades del sistema
- Planteamiento del sistema y storyboard de la aplicación.
   - Modelo de negocio
   - Storyboard / Mapa de navegación
- Modelo de datos del sistema.
   - Modelo Entidad-Relación
   - Modelo Relacional
   - Diseño de las clases implementadas en la capa de persistencia de datos
- Diferencias con la primera versión planteada.
- Puesta en marcha del sistema de información.
   - Procedimiento para el despliegue e instalación de la aplicación
   - Cuestiones necesarias para el uso de la aplicación
- Cronograma con tiempos de dedicación al sistema y valoración grupal
- Bibliografía.


## Objetivo y alcance funcional de la aplicación.

### Objetivos

Esunhechoquelasprobabilidadesdecongeniarconpersonasquecompartangustosmusicales
similaresesmásaltaquecuandodichosgustossondisparejos.Anteestedato,sehaplanteado
desarrollarunaaplicaciónwebquecubralanecesidadderelacionarseconaquellasotraspersonas
quecompartandeterminadosgénerosmusicales.Paraayudarenelproceso,enlaredsociala
desarrollarsepublicaránlospróximoseventosdeacuerdoalgénero,permitiendoquenuevos
grupos de personas se junten y se conozcan en la vida real.

Así,ademásdetener lacapacidadderealizar publicaciones,seofreceráalusuariocomúnlos
mensajespublicadosporotrascuentas,aligualqueloseventosquevanaocurrirenlaspróximas
fechas con el número de personas totales que han indicado que iban a participar.

Laaplicaciónwebestaráorganizadaendistintosgrupossociales,cadaunodedicadoaungénero
musicalespecífico.Deestemodo,elusuariopodráseleccionarencualquiermomentolosgéneros
musicalesdondedeseaparticipar,aligualqueelegirelgrupodeentrelosqueperteneceapartir
del cual visualizará publicaciones y eventos relacionados.

Lainformaciónquesevaobtenerdelaaplicaciónwebresultarádelainteraccióndelosusuarios
conlapropiaredsocial,comopuedenserlaspublicacionesoeventosrealizadosatravésdeesta
en los grupos concretos.

Partededicha informaciónrecogidaserágestionadaenformadedatosestadísticosparasu
posteriorestudioporunadministrador.Entrelosdatosquepuedenobtenerlosadministradores
sepuedendestacarelnúmerodeparticipantesdecadagrupo,eldeusuariosregistrados,oelde
apuntados por cada evento.

Elpúblicoobjetivoalquevadirigidalaaplicaciónwebsonprincipalmentejóvenesdeentre 15 y 35
años,loscualesdisfrutanmásdelasactividadescomoconciertosyotroseventosmusicales,al
igualquetienenuncontrolmayordelasredessocialesengeneral.Sinembargo,laaplicaciónno
discrimina siempre que se tengan unos conocimientos mínimos sobre el uso de estas mismas.

Atravésdetodoestosehadesarrolladounprototipoderedsocialfuncionalquegestionalos
datos a través de un sistema de información completo.


### Perfiles de usuario

Existen tres perfiles de usuarios distintos; el usuario normal, el usuario especial y el administrador.

Elusuario normalpuedeinformarsedepróximoseventosatravésdeunpanelconcreto y
apuntarseaellos.Encadaeventopublicadopuedeobservar,apartedelainformaciónbásica
(fechas, lugar de celebración, etc.), el número de usuarios apuntados.
Además,puedeleerpublicacionesdeotrosusuariosenotropanelespecializado,aligualque
realizar sus propias publicaciones.
Dichousuariopuedeescribircomentariostantoenlasdistintaspublicacionesexistentescomoen
los diferentes eventos realizados
Enadición,puededecidirenquégénerosmusicalesdeseaparticipar,aligualqueseleccionarel
grupomusicaldeentrelosquepertenece,apartirdelcualvisualizarápublicacionesyeventos
relacionados.

Losusuariosespecialestienenasudisposicióntodaslasaccionesquepuedenrealizarlosusuarios
normalesconelextradequeposeenpermisosparapublicarpróximoseventosenlaplataforma.
Se incluyen en este tipo de usuarios a autores y organizadores de prestigio.

Enlorelativoalosadministradores,habrácomomínimo 1 porcadagrupomusicalcreado.Entre
susfuncionespuedenpublicarotroseventosparaeldisfrutedelosusuariosnormales,yconceder
permisos a usuarios normales de modo que se conviertan en usuarios especiales.
Tienenasudisposicióntodoslosdatosestadísticosobtenidosdelalecturadeinformación,como
puede ser el radio de popularidad de cada grupo, los eventos con más usuarios apuntados, etc.


### Entidades de información

Esnecesarioalmacenarytratarlainformaciónbásicadelacuentadecadausuario,entendiendo
poréstaelnombredeusuario,lacontraseña,elcorreoelectrónicoyeltipodecuenta,demodo
que se pueda iniciar sesión y mostrar sesiones distintas en función del tipo de usuario registrado.

Juntoconcadagrupomusical,esprecisoalmacenartodoslosusuariosquepertenecenacada
grupo.

Todoslaspublicaciones,eventosocomentariosquerealicenlosusuariosesnecesariocapturarlos
yalmacenarlosdeformaquepuedanserdevueltosalrestodecuentascuandosemetanenel
panel concreto.

Conelobjetivodesacarestadísticas,segestionanlosdatosdelsistemademodoqueseobtengan
elnúmero de participantes decada grupo, elnúmero deusuariosregistradostotales, yel
porcentaje de popularidad por grupo, resultado de evaluar la relación entre los últimos dos datos.
Tambiénsepuedenobtenerloseventosdondemáspersonassehanapuntadosegúnperiodosde
tiempodistintos(últimoañoydesdesiempre).Dichosestadísticosleapareceránaladministrador
en distintos paneles específicos dentro de su sesión.

En adición, sealmacenaránlassolicitudesdeconversióndecuentaaespecialrealizadaspor
usuariosnormalesylosinformesdequejasescritostantoporlosusuariosnormalescomoporlos
especiales, permitiendo al administrador observar y tratar dichos datos.


### Funcionalidades del sistema

- Elsistemadebepermitiratodoslostiposdeusuariosregistradosiniciarsesiónmediante
    credenciales únicas.
- Elsistemadebepermitirlacreacióndecuentasdetiponormalmedianteunregistrode
    usuario, de modo que se almacenen el nombre de usuario, la contraseña, el correo
    electrónico del usuario y los grupos musicales a los que pertenecerá el nuevo usuario.
       - Elnuevonombredeusuariointroducidoduranteelregistrotienequeserúnico,esdecir,
          no puede existir otra cuenta asociada al mismo nombre de usuario.
       - Elnuevocorreoelectrónicointroducidoduranteelregistrotienequeserúnico,esdecir,
          no puede existir otra cuenta asociada al mismo correo electrónico.
- El sistema debepermitir a los usuarios normales yespecialesseleccionarlosgéneros
    musicalesenlosqueparticipar,demodoqueloscapture,almaceneygestioneresultandoen
    los grupos musicales donde dicho usuario pertenecerá.
       - Elsistemanodebepermitirqueunusuarionormaloespecialnopertenezcaaningún
          grupo musical.
- Elsistemadebepermitiralosusuariosnormalesyespecialesseleccionarungrupomusicalde
    entreaquellosalosquepertenece,siendoesteelgénerodelquemostraránpublicacionesy
    eventos.
- Elsistemadebecapturarlaspublicacionesrealizadasporlosusuariosnormalesyespeciales
    engruposmusicalesconcretos,almacenarlas,ydevolverlasparamostrarlasenelpanelde
    publicaciones correspondiente.
- Elsistemadebecapturarloseventosrealizadosporlosadministradoresyusuariosespeciales
    engruposmusicalesconcretos,almacenarlos,ydevolverlosparamostrarlosenelpanelde
    eventos correspondiente.
- Elsistemadebecapturarloscomentariosrealizadosporlosusuariosnormalesyespecialesen
    publicacionesyeventosconcretos,almacenarlos,ydevolverlosparamostrarlosenelpanel
    de comentarios correspondiente.
- Elsistemadebealmacenar elnúmero deusuariosquesehan apuntadoenunevento
    concreto, y mostrarlo en el evento correspondiente.
- El sistema debe almacenar, para cada evento, cada usuario que se ha apuntado.
- El sistema debe permitir a los usuarios normales y especiales ordenar los eventos
    cronológicamente o por número de apuntados.
- Elsistemadebemostraraladministradorlasestadísticasobtenidasdelaevaluacióndelas
    interacciones de los usuarios.
       - Elsistemamostraráaladministradorelnúmerodeusuariospertenecientesacadagrupo,
          junto con el número de usuarios totales registrados.
       - El sistema mostrará al administrador el número de apuntados de cada evento.
       - Elsistemamostraráaladministradoreleventoconmásusuariosapuntados,tantoenel
          último año como desde siempre.
- El sistema debe permitir a los administradores ordenar las estadísticas de eventos
    cronológicamente o por número de apuntados.


- Elsistemadebepermitiralosusuariosnormalessolicitarelcambiodecuentaausuario
    especial, de modo que la información de la solicitud se gestione y se devuelve al
    administrador.
- Elsistemadebepermitiraladministradorconcederonegarpermisosdeusuarioespecialalos
    usuarios normales cuando se requiera.
- Elsistemadebepermitiraladministradorquitarpermisosdeusuarioespecialalosusuarios
    especiales cuando se requiera, de modo que se conviertan en cuentas normales.
- Elsistemadebepermitiralosusuariosnormalesyespecialesenviarinformessobrequejaso
    propuestas, de modo que la información del informe se gestione y se devuelva al
    administrador.
- Elsistemadebepermitir aladministrador descartar losinformesrecibidosqueyahaya
    gestionado, eliminandolos del sistema.


## Planteamiento del sistema y storyboard de la aplicación.

### Modelo de negocio

Puestoqueeldesarrollodelaaplicaciónwebquesehaplanteadosebasaenunaredsocial,en
una primera instancia surgieron tres opciones a evaluar.

Laprimera constaba dehacer quelosusuariosespeciales,quetienenelprivilegiodepoder
publicar eventos, tuviesen querealizar un pago único para conseguir dichospermisos.Sin
embargo, después de analizarlo fríamente se llegó a la conclusión de que esto era
contraproducenteyaquenormalmentenoleshacefaltapagarparapromocionarsuspropios
eventos,puestoqueyatienencuentasgratuitasenotrasredessocialescomopuedeserInstagram
dondesubenlainformacióndesuseventoscomopublicacionesnormales,demodoquelos
usuariosinteresadosseenterandetodasformas.Unabasedenuestromodeloesfacilitarla
consultadeestoseventos,yelhechodequelasempresasnoseunanporquedebanpagar
contradice esta idea de facilitación.

Despuésdedesechar laprimera opción, seprocedióaevaluarotrasopciones.Unadeellas
consistíaenponeranunciosrelacionadosconelgénerodelgrupoaloslateralesdelaaplicación
web.Estaalternativaestuvomuybienvistayaquelosmárgeneslateralesseencontrabanvacíos
enlosbocetosdela interfazdelosquesedisponía, acausadequetodoelcuerpodela
informaciónseencontrasecentrado.Además,comodichosanunciosestabanrelacionadoscon
los gustos de los usuarios, no interfería de forma molesta con la actividad de los usuarios.

Porúltimo,surgiólaideadevenderlasestadísticasnuméricasdeloseventosdelasempresas
organizadorascorrespondientes,demodoqueellasmismaspudieranutilizarlasparaconocerqué
eventoshabíantenidomáséxitoohabíanllamadomáslaatenciónentrelosusuarios,obteniendo
información muy útil para la elaboración de próximos eventos.

Contodasestasopcionesrecogidas,finalmentesehadecididoquesellevaránacabolasúltimas
dosopcionespropuestas,ofreciendocomoconsecuenciaunmodelodondeaparecenanuncios
afinesalosgustosdelosusuariosenlosmárgenesdesocupadosdelasdistintaspáginas,ydonde
sevendelainformaciónestadísticaobtenidadelosdistintosvaloresnuméricosrecogidosporel
sistemadeinformaciónalasempresasorganizadorasinteresadasconfinescomerciales(elaborar
nuevos eventos con mayor tasa de éxito y, por ende, con mayores ganancias).


### Storyboard / Mapa de navegación

Conelpropósitodeconseguirunamayorlegibilidad,sehanseparado 4 submapasdiferentesque
enconjuntocomponenunmapadenavegacióncompleto.Unoesparalaspantallasdeiniciode
sesiónderegistro,ylosotrostresparalasinterfacesdecadatipodeusuario(normal,especialy
administrador).

Además,conelfindefavorecerla comprensióndelosmapassinsaturarlosdecapturasrepetidas,
enlosusuariosquedisponendebotonessimilaresquellevanaaccionesigualmentesemejantes,
solo se ha puesto la captura de la página correspondiente a dicha operación en uno de ellos.

Enadición,sehanomitidolasflechasquesurgendelascrucessituadasenlasesquinassuperior
derechadelospop-upsdemodoqueseeviteunasobrecargadeflechas,promoviendoigualque
anteriormenteunamejorcomprensión.Lafunciónquerealizanestascrucesescerrarelpop-up
volviendo a la ventana que ha iniciado la aparición de dicho pop-up.

Comolaformaenqueseexpresanloserroresprincipalesesmostrandounacadenadetextoenla
zona donde ha ocurridodichoproblema, siendoqueelrestodela páginaesigualquela
predecesoraysiguesiendofuncional,sehaoptadopormostrar lascapturasconlosposibles
erroresmedianteflechasdiscontinuasydejandolascapturassinflechasdeoperaciones,demodo
queseentienda queesla mismapáginaconlasmismasfuncionalidadesperoconlaúnica
excepción de la aparición de un aviso de error.

Dependiendodelascredencialesqueseinsertenenloscamposdeiniciodesesión,laventana
queaparecerácorresponderáalainterfazrelativadeltipodeusuarioiniciado.Asíesquesise
introducenunascredencialesdeadministrador,sedirigiráalaventanapredeterminadaparael
tipo de usuario administrador, siendo los mismos casos para usuarios normales y especiales.

Elusuarioespecialdisponedelosmismospanelesparapublicacionesyeventos,conlaexcepción
dequeeneldeeventosapareceunanuevaopciónpara“Publicareventos”.Laotradiferencia
apreciableesqueenelmenúdesplegablenoapareceelbotón“Solicitarespecial”,puestoqueel
usuario ya dispone de una cuenta con permisos especiales.

Los distintos submapas son:


- Inicio de sesión y registro


- Usuario normal


- Usuario especial


- Administrador


## Modelo de datos del sistema.

### Modelo Entidad-Relación

EldiseñodelmodeloEntidad-Relaciónutilizadoenlabasededatosrequeridaparaalmacenarlos
datos de la aplicación web es el siguiente.

Además, se deben tener en cuenta las siguientes restricciones:

- SololosUsuariosdetipoNormalpuedenenviarSolicitudesparaconvertirseenUsuarios
    detipo Especial, ysolo losUsuariosdetipo Administrador puedengestionardichas
    Solicitudes.
- SololosUsuariosdetipoNormaloEspecialpuedenescribirQuejas,ysololosUsuariosde
    tipo Administrador pueden gestionar dichas Quejas.
- Solo los Usuarios de tipo Especial o Administrador pueden realizar Eventos.
- Solo losUsuariosdetipoNormalo Especialpueden publicarPublicacionesoañadir
    Comentarios tanto de Publicaciones como de Eventos.
- Solo los Usuarios de tipo Normal o Especial pueden apuntarse a Eventos.
- El número de personas apuntadas (Num_apuntados) a un Evento nunca puede ser < 0.

El número de participantes de un grupo (Num_participantes) nunca puede ser < 0.


### Modelo Relacional

A partir del modelo Entidad-Relación planteado seha desarrollado el siguiente esquema
relacional.LastablasGrupos_UsuariosyApuntadossonelresultadodeevaluarlasrelacionesN:M
del esquema E-R anterior.

```
Usuarios (
Nombre: cadena;
Correo : cadena, único, no nulo;
Contrasenya : cadena, no nulo;
Tipo : cadena(“Normal”, “Especial”, “Administrador”), no nulo );
```
```
Grupos (
Nombre: cadena;
Num_Participantes : natural, no nulo );
```
```
Publicaciones (
id: entero con auto-incremento ;
Nombre : cadena, no nulo, clave ajena de Usuarios ;
Descripcion : cadena, no nulo;
Grupo : cadena, no nulo, clave ajena de Grupos );
```
```
Eventos (
id: entero;
Empresa : cadena, no nulo;
Fecha_Evento : fecha, no nulo;
Lugar : cadena, no nulo;
Descripcion : cadena, no nulo;
Num_Apuntados : natural, no nulo;
Grupo : cadena, no nulo, clave ajena de Grupos ;
Nombre : cadena, no nulo, clave ajena de Usuarios );
```
```
Comentarios_Publi (
id: entero;
Nombre : cadena, no nulo, clave ajena de Usuarios ;
Descripcion : cadena, no nulo;
Publicacion : entero, no nulo, clave ajena de Publicaciones );
```
```
Comentarios_Event (
id: entero;
Nombre : cadena, no nulo, clave ajena de Usuarios ;
Descripcion : cadena, no nulo;
Evento : entero, no nulo, clave ajena de Eventos );
```
```
Quejas (
id: entero;
Nombre : cadena, no nulo, clave ajena de Usuarios ;
Descripcion : cadena, no nulo );
```
```
Solicitudes (
Nombre: cadena, clave ajena de Usuarios );
```
```
Grupos_Usuarios (
Usuario: cadena, clave ajena de Usuarios ;
Grupo: cadena, clave ajena de Grupos );
```
```
Apuntados (
Usuario: cadena, clave ajena de Usuarios ;
Evento: cadena, clave ajena de Eventos );
```

Para observar una ideamásvisualdel modelo Relacionalsepresenta un esquema conlos
atributosdelastablas,destacandolasclavesprimariasylasrelacionesdeclavesajenasentrelas
distintas tablas.


### Diseño de las clases implementadas en la capa de persistencia de datos

Los _“DAO”_ quecomponenlacapadepersistenciadedatospermitenabstraeryencapsularla
comunicaciónconlabasededatos,demaneraquelacapadelógicadelaaplicaciónnonecesita
enningúnmomentoconocercómoestáimplementadalabasepordentro(tablas)niacuálse
conecta.

Siguiendodichomodelo,sehadecididodistribuirlos _“DAO”_ paracadaunadelasfuncionalidades
quenuestrosistemapuedellegaraofrecer,detalformaquelosdesarrolladoresqueimplementan
lapartelógicade la aplicaciónsepanintuitivamentedondehandehacer lasllamadasalos
métodos de los propios _“DAO”_ para obtener la informaciónque deseen de las distintas entidades.

Unejemplodefuncionalidadseríaelsistemade _“Quejas”_ ,dondelosadministradorespueden
consultartodaslasquejaspresentadasporlosdistintosusuariosconelmétodo _“obtenerQuejas(...)”_ ,
los usuarios pueden enviar dichas quejas mediante _“presentarQueja(...)”_ , etc.


## Diferencias con la primera versión planteada.

Alcompararlasversionesinicialesdelproyectoconelsistemadeinformaciónfinal,sepueden
encontrar algunas diferencias que valen la pena mencionar.

Enprimerlugar,esapreciableelaumentoenlacantidaddefuncionalidadesquesepresentan,
ademásdecadaunasermásespecífica.Estosedebealaexistenciadeunavisiónmásdetalladay
ajustadadelfuncionamientodelsistemaydelaaplicaciónweb.Sinembargo,alahoradeanalizar
labasedelasmismas,seobservaqueambasdisponendelasmismasoperacionesdisponibles
para realizar, aunque en esta última versión se encuentren mejor estructuradas.

Además,alestudiarambosstoryboards,tantoelprimerorealizadocomoelrelativoalaversión
definitiva, hay pequeñas disparidades de las que es importante destacar tres.
Laprimeraconsisteenlaeliminacióndelaopciónparaconfirmarlacontraseñaalsolicitarun
usuarionormallaconversióndecuentaaunaconpermisosespeciales.Estamodificaciónsellevó
acabodebidoaquelaoperaciónnoesuncambiodirectoalacuenta,sinoquedependedeun
tercero,enestecasounadministrador,queteconcedalospermisossolicitados,yestenodebería
entregarlosamenosqueseanrequeridosporunaempresaorganizadoradeeventosounautoro
banda del género.
Elsegundotratadeincorporarcomomedidadeseguridadlaaccióndeconfirmarcontraseñaal
eliminarlacuentayaque,alcontrarioqueantes,estaoperaciónsiproduceunamodificación
directa a la cuenta, y en este caso, permanente.
Elúltimoradicaenañadiruncampoderepetircontraseñaduranteelregistro,demodoquese
eviten fallos del usuario al escribir la nueva contraseña incorrectamente.


## Puesta en marcha del sistema de información.

### Procedimiento para el despliegue e instalación de la aplicación

Elúnicorequisitoprevioaldesplieguedelaaplicaciónestenerinstaladodockerenelequipoen
cuestión.Noesnecesarioinstalarnadamásdebidoaquedocker,alvirtualizarlaaplicaciónconsus
dependenciasrequeridas,evitacontratiemposcomolibreríasfaltantes,conflictosdeversioneso
ficherosdeconfiguraciónproblemáticos.Paraello,encasodequenosetengainstalado,se
deberían ejecutar los siguientes comandos en la terminal del equipo ubuntu o semejante:

```
> sudo apt install docker
> sudo apt install docker.io
```
Unavezpreparadoelequipo,sedeberádescomprimirelarchivo.zipquecontienelosarchivos
necesariosparaeldespliegueeinstalacióndelaaplicación.Endichopaquetedearchivosse
encuentraelproyectoconelcódigofuentedelaredsocialdesarrollada,unaimagenquecontiene
la aplicación junto con el servidor Tomcat, y otra que abarca la base de datos de postgresql.

Finalmenteparadesplegarlaaplicaciónwebbastaconejecutarelscriptbashqueseencuentra
dentrodelacarpeta“ _Docker”_ .Mediantedichoficherosemontanloscontenedoresrelativosal
servidorTomcatyalabasededatos,ademásdecrearunNATdelpuerto 8080 denuestrohostal
puerto 8080 deloscontenedores,deformaquesepuedaabrirlaaplicaciónwebatravésdelaurl
_[http://localhost:](http://localhost:) 8080_.

```
> sudo ./Montar_Contenedores.sh
```
Adicionalmente, se puede ejecutar,

- si se desea detener los contenedores tanto del servidor Tomcat como de la base de datos:

```
> sudo docker stop sisinf-tomcat
> sudo docker stop sisinf-database
```
- si se desea eliminar los contenedores tanto del servidor Tomcat como de la base de datos:

```
> sudo docker rm sisinf-tomcat
> sudo docker rm sisinf-database
```

### Cuestiones necesarias para el uso de la aplicación

Alahoradeempezarautilizarlaaplicaciónwebesprecisodestacarlaexistenciadelosgrupos
musicales por defecto, de modo que se pueda probar con una experiencia global el
funcionamiento del conjunto de todas las operaciones.

Alahoradeiniciarsesión,bastaconintroducirlascredencialesúnicasdelacuentaenlapáginade
inicio, demodoque,dependiendodeltipodecuentaqueseposea(usuarionormal,usuario
especial o administrador), se redirija a distintas páginas específicas.

Comoenlaaplicaciónbasenoexisteningúnusuarionormal,esnecesarioregistrarseprimero
antesdepoderiniciarsesióncomounusuariodeestetipo.Además,alnohabertampocousuarios
especiales,sisedeseatenerunacuentadeestetiposedeberásolicitaraladministrador(enel
menú desplegable de la derecha, botón _“Solicitar cuenta especial”_ ), y esteúltimo deberá
concederle permisos por medio de la página dedicada _“Gestionar solicitudes”_.

Conelfindequesepuedanprobarlasaccionesexclusivasdeladministrador,sefacilitanunas
credenciales de una cuenta administrativa:
Nombre de usuario: admin
Contraseña: admin


## Cronograma con tiempos de dedicación al sistema y valoración grupal

Durantelaevaluacióndelproyectoentretodoslosmiembrosdelgrupo,lasconclusionesalasque
sehanllegadosonunánimes.Estetrabajo,aunquelargo,haresultadoparticularmenteútilala
horadecomprenderdeformavisualelfuncionamientointernodeunsistemadeinformacióny
comooperanentresílasdistintascapasexistentes(control,persistencia,etc).Atravésdeél
hemosreforzadolosconocimientosrelativosalaestructuradelosservletsydelosDAOs,aligual
quehemosaprendidonuevosmétodosdediseñodepáginaswebatravésdeplataformasde
apoyo como Bootstrap. Es destacable el beneficio cognitivo obtenido tras las comunes
resolucionesdeerroresduranteelpasodeatributosyparámetrosenlosservletsyficherosjsp,y
durante la comunicación con la base de datos, especialmente al tratar con el tipo de dato _“date”_.

Podríadecirsequeloquemástiempohallevado,ignorandoproblemasexternoscomoelpobre
rendimientodelamáquinavirtualendeterminadosequipos,hasidoelconjuntoderealizartodos
losservletsdeformafuncionalconlosficherosjsp.Tambiénsegastóuntiemposignificanteenla
tareadedocumentación,graciasalacualseconsiguióaprender,entreotrascosas,aexponer
variasfilasdeinformaciónhastaunlímitemáximo,permitiendolavisualizacióntotaldedicha
información mediante distintaspáginas comunicadasmediante botonespara acceder a las
pestañas anteriores o posteriores a la del momento.

Alfinaldetodo,noshabríagustadopoderimplementarunanuevafuncionalidadexclusivadel
administrador,dedicadaaeliminaryañadirnuevosgruposalconjuntodeexistentes,estoparael
casodequesurgierannuevosgénerosmusicalesyfueranecesarioincorporarlosalsistemade
información.Encualquiercaso,alnohaberlapodidodesarrollar,sehaincorporadoalplande
mantenimiento propuesto en la práctica anterior.


## Bibliografía.

Documentación base:

- PDFs del curso de _Sistemas de Información_ de la plataforma _https://moodle.unizar.es/_

Plataforma para la realización del Storyboard:

- https://moqups.com/

Referencias utilizadas para la comprensión e implementación de los ficheros VO y DAO, al igual
que la configuración del PoolConnectionManager:

- https://moodle.unizar.es/add/pluginfile.php/ 7072995 /mod_resource/content/ 2 /PatronDA
    O.pdf

Referencias empleadas para utilizar el campo _fecha_evento_ correctamente dentro de las consultas
empleadas en los DAO:

- https://dba.stackexchange.com/
- https://database.guide/

Uso y empleo de las clases específicas de bootstrap:

_- https://getbootstrap.com/_

Corrección de errores:

_- https://stackoverflow.com/_

