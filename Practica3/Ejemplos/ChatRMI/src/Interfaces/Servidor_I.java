/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.*;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public interface Servidor_I extends Remote {
    int getNumeroUsuarios()throws RemoteException;
    int registrar(String usuario)throws RemoteException;
    void desconectar(String usuario)throws RemoteException;
    ArrayList<String> getUsuariosConectados()throws RemoteException;
}
