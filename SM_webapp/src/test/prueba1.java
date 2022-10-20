package test;

import java.sql.SQLException;

import model.*;

public class prueba1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UsuariosVO usuario = new UsuariosVO("Pepe", "Palvaper@unizar.es", "Resano", "Normal");

        UsuariosDAO.insertarUsuario(usuario);
    }

}