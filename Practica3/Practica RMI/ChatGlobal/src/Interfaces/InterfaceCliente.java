
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCliente extends Remote {
    //Obtener nombre dle usuario
    public String getNombre()throws RemoteException;
    //Realizar conexion
    public int conectar()throws RemoteException;
    //Enviar mensaje al servidor
    public void envioMensaje(String mensaje) throws RemoteException;    
    //Recibir mensaje entrante dle servidor
    public void recibirMensaje(String mensaje) throws RemoteException;
    //Cerrar conexion
    public void desconectar() throws RemoteException;
}
