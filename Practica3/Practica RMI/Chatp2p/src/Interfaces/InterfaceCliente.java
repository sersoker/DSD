
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCliente extends Remote {
    //Obtener nombre dle usuario
    public String getNombre()throws RemoteException;
    //Realizar conexion
    public int conectarS()throws RemoteException;
    //Realizar conexion con un usuario
    public int conectarU(String usuario)throws RemoteException;
    //Cuando un usuario conecta, manda la peticion al otro para que se conecte a el.
    public int conectarU2(String usuario)throws RemoteException;
    //Recibir mensaje entrante de un usuario
    public void recibirMensaje(String usuario,String mensaje) throws RemoteException;
    //Cerrar conexion
    public void desconectar() throws RemoteException;
    //Actualiza la lista de usuarios
    public void actualizaListaUsuarios() throws RemoteException;
    
}
