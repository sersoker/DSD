/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Javier
 */
public interface Cliente_I extends Remote{
    public String getNombre()throws RemoteException;
    public int conectarServidor()throws RemoteException;
    public void actualizarUsuarios() throws RemoteException;
    
    /*********** Parte P2P ***********/
    public int conectarUsuario(String usuario)throws RemoteException;
    public void recibirMensaje(String usuario, String mensaje) throws RemoteException;
    public void desconectarUsuario(String usuario) throws RemoteException;
}
