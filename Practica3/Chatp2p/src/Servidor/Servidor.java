package Servidor;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListModel;

public class Servidor extends UnicastRemoteObject implements Interfaces.InterfaceServidor{
    TreeMap<String, Interfaces.InterfaceCliente> CLIENTES;
    
    Servidor() throws RemoteException{
        super();
        CLIENTES=new TreeMap<>();
    }

    @Override
    public int getNumeroUsuarios() throws RemoteException {
        return this.CLIENTES.size();
    }

    @Override
    public int registrar(String usuario) throws RemoteException {
        System.out.println("PETICON DE CONEXION");
        boolean existe=CLIENTES.containsKey(usuario);
        try {
            if(!existe){
                Registry reg = LocateRegistry.getRegistry("127.0.0.1", 2000);
                CLIENTES.put(usuario, (Interfaces.InterfaceCliente) reg.lookup(usuario));
                         for(Map.Entry<String, Interfaces.InterfaceCliente> c: CLIENTES.entrySet()){
                           c.getValue().actualizaListaUsuarios();
                        }
                return 0; //Sin errores
            }
            else{
                return -1; //Ya existe el usuario
            }
            
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e);
            return -2; //Error
        }
        
        
    }

    @Override
    public void refrescarUsuariosCliente() throws RemoteException {
          for(Map.Entry<String,Interfaces.InterfaceCliente> cli : CLIENTES.entrySet()){
              cli.getValue().actualizaListaUsuarios();
            }
    }
    
    @Override
    public void logoutUsuario(String usuario) throws RemoteException {
        CLIENTES.remove(usuario);
        for(Map.Entry<String, Interfaces.InterfaceCliente> c: CLIENTES.entrySet()){
            c.getValue().actualizaListaUsuarios();
        }
    }

    @Override
    public ArrayList getUsuariosConectados() throws RemoteException {
        ArrayList Lista =new ArrayList<>(this.CLIENTES.keySet());
        return Lista;
    }

    
    public static void main (String args[]){
        if(System.getSecurityManager() == null) {  System.setProperty("java.security.policy", "./src/server.policy");  System.setSecurityManager(new SecurityManager());   }
        try {
            Registry reg = LocateRegistry.createRegistry(2000);
            reg.bind("servidor", new Servidor());
            System.out.println("Servidor Online");
            
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(e);
        }
    }


    
}
