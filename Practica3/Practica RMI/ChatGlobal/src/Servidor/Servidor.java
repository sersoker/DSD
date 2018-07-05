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
                String mensajeConexion = usuario+" se ha conectado al Chat\n";
                compartirMensaje(mensajeConexion);
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
    public void mensaje(String usuario, String Mensaje) throws RemoteException {

        String textoFormateado = usuario+": "+Mensaje+"\n";
        this.compartirMensaje(textoFormateado);
    }

    @Override
    public void logoutUsuario(String usuario) throws RemoteException {
                        System.out.println("LLEGA A LOGOUT");

        Interfaces.InterfaceCliente cerrarSesion = CLIENTES.remove(usuario);
        String mensajeSalida = cerrarSesion.getNombre()+ " dejó el chat \n";
        this.compartirMensaje(mensajeSalida);
    }

    @Override
    public ArrayList<String> getUsuariosConectados() throws RemoteException {
        ArrayList<String> Lista =new ArrayList<>(this.CLIENTES.keySet());
        return Lista;
    }

    @Override
    public void compartirMensaje(String mensaje) throws RemoteException {
        //Para cada cliente conectado al servidor, mandamos el mensaje
         for(Map.Entry<String, Interfaces.InterfaceCliente> c: CLIENTES.entrySet()){
            System.out.println("Mandando mensaje a cliente");
            c.getValue().recibirMensaje(mensaje) ;
        }
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
