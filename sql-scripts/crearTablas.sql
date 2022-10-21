
-- create schema

CREATE SCHEMA social_music AUTHORIZATION postgres;

-- create tables

-- social_music.usuarios definition

CREATE TABLE social_music.usuarios (
	nombre varchar NOT NULL,
	correo varchar NOT NULL,
	contrasenya varchar NOT NULL,
	tipo varchar NOT NULL,
	CONSTRAINT usuarios_check CHECK ((((tipo)::text = 'Normal'::text) OR ((tipo)::text = 'Especial'::text) OR ((tipo)::text = 'Administrador'::text))),
	CONSTRAINT usuarios_pk PRIMARY KEY (nombre),
	CONSTRAINT usuarios_un UNIQUE (correo)
);

-- social_music.grupos definition

CREATE TABLE social_music.grupos (
	nombre varchar NOT NULL,
	num_participantes int4 NOT NULL DEFAULT 0,
	CONSTRAINT grupo_check CHECK ((num_participantes >= 0)),
	CONSTRAINT grupo_pk PRIMARY KEY (nombre)
);

-- social_music.publicaciones definition

CREATE TABLE social_music.publicaciones (
	id serial NOT NULL,
	nombre varchar NOT NULL,
	descripcion varchar NOT NULL,
	grupo varchar NOT NULL,
	CONSTRAINT publicaciones_pk PRIMARY KEY (id)
);

-- social_music.publicaciones foreign keys

ALTER TABLE social_music.publicaciones ADD CONSTRAINT publicaciones_fk_grupo FOREIGN KEY (grupo) REFERENCES social_music.grupos(nombre) ON DELETE CASCADE;
ALTER TABLE social_music.publicaciones ADD CONSTRAINT publicaciones_fk_usuario FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;

-- social_music.eventos definition

CREATE TABLE social_music.eventos (
	id serial NOT NULL,
	empresa varchar NOT NULL,
	fecha_evento date NOT NULL,
	lugar varchar NOT NULL,
	descripcion varchar NOT NULL,
	num_apuntados int4 NOT NULL DEFAULT 0,
	grupo varchar NOT NULL,
	nombre varchar NOT NULL,
	CONSTRAINT eventos_check CHECK ((num_apuntados >= 0)),
	CONSTRAINT eventos_pk PRIMARY KEY (id)
);

-- social_music.eventos foreign keys

ALTER TABLE social_music.eventos ADD CONSTRAINT eventos_fk FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;
ALTER TABLE social_music.eventos ADD CONSTRAINT eventos_fk_grupo FOREIGN KEY (grupo) REFERENCES social_music.grupos(nombre) ON DELETE CASCADE;

-- social_music.comentarios_publi definition

CREATE TABLE social_music.comentarios_publi (
	id serial NOT NULL,
	nombre varchar NOT NULL,
	descripcion varchar NOT NULL,
	publicacion int4 NOT NULL,
	CONSTRAINT comentarios_publi_pk PRIMARY KEY (id)
);

-- social_music.comentarios_publi foreign keys

ALTER TABLE social_music.comentarios_publi ADD CONSTRAINT comentarios_publi_fk FOREIGN KEY (publicacion) REFERENCES social_music.publicaciones(id) ON DELETE CASCADE;
ALTER TABLE social_music.comentarios_publi ADD CONSTRAINT comentarios_publi_fk_usuario FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;

-- social_music.comentarios_event definition

CREATE TABLE social_music.comentarios_event (
	id serial NOT NULL,
	nombre varchar NOT NULL,
	descripcion varchar NOT NULL,
	evento int4 NOT NULL,
	CONSTRAINT comentarios_event_pk PRIMARY KEY (id)
);

-- social_music.comentarios_event foreign keys

ALTER TABLE social_music.comentarios_event ADD CONSTRAINT comentarios_event_fk_evento FOREIGN KEY (evento) REFERENCES social_music.eventos(id) ON DELETE CASCADE;
ALTER TABLE social_music.comentarios_event ADD CONSTRAINT comentarios_event_fk_usuario FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;

-- social_music.quejas definition

CREATE TABLE social_music.quejas (
	id serial NOT NULL,
	nombre varchar NOT NULL,
	descripcion varchar NOT NULL,
	CONSTRAINT quejas_pk PRIMARY KEY (id)
);

-- social_music.quejas foreign keys

ALTER TABLE social_music.quejas ADD CONSTRAINT quejas_fk_usuario FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;

-- social_music.solicitudes definition

CREATE TABLE social_music.solicitudes (
	nombre varchar NOT NULL,
	CONSTRAINT solicitudes_pk PRIMARY KEY (nombre)
);

-- social_music.solicitudes foreign keys

ALTER TABLE social_music.solicitudes ADD CONSTRAINT "social_music.solicitudes.solicitudes_fk_usuario" FOREIGN KEY (nombre) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;

-- social_music.grupos_usuarios definition

CREATE TABLE social_music.grupos_usuarios (
	usuario varchar NOT NULL,
	grupo varchar NOT NULL,
	CONSTRAINT grupos_usuarios_pk PRIMARY KEY (usuario, grupo)
);

-- social_music.grupos_usuarios foreign keys

ALTER TABLE social_music.grupos_usuarios ADD CONSTRAINT grupos_usuarios_fk_usuario FOREIGN KEY (usuario) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;
ALTER TABLE social_music.grupos_usuarios ADD CONSTRAINT "social_music.grupos_usuarios.grupos_usuarios_fk_grupo" FOREIGN KEY (grupo) REFERENCES social_music.grupos(nombre) ON DELETE CASCADE;

-- social_music.apuntados definition

CREATE TABLE social_music.apuntados (
	usuario varchar NOT NULL,
	evento int4 NOT NULL,
	CONSTRAINT apuntados_pk PRIMARY KEY (usuario, evento)
);

-- social_music.apuntados foreign keys

ALTER TABLE social_music.apuntados ADD CONSTRAINT apuntados_fk_eventos FOREIGN KEY (evento) REFERENCES social_music.eventos(id) ON DELETE CASCADE;
ALTER TABLE social_music.apuntados ADD CONSTRAINT apuntados_fk_usuarios FOREIGN KEY (usuario) REFERENCES social_music.usuarios(nombre) ON DELETE CASCADE;
