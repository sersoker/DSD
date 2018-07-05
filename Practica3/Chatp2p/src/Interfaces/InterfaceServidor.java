package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public interface InterfaceServidor extends Remote{
    // throws RemoteException es necesario para que podamos implementar UnicastRemoteObject en en lado del servidor.    
    //Devuelve el numero de usuarios (se puede usar como ID)
    int getNumeroUsuarios()throws RemoteException;
    //Dado un nombre de usuario intenta registrarlo.
    int registrar(String usuario)throws RemoteException;
    //Dado un usuario borramos su entrada en la lista de usuarios del servidor.
    void logoutUsuario(String usuario)throws RemoteException;
    //Devolvemos la lista de usuarios
    ArrayList getUsuariosConectados()throws RemoteException;
    //ACtualizamos la lista de usuarios en cada cliente.
    void refrescarUsuariosCliente()throws RemoteException;

}
