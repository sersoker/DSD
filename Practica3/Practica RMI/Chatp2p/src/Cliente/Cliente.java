package Cliente;

import java.rmi.RemoteException;
import javax.swing.JTextArea;
import Interfaces.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.DefaultListModel;


public class Cliente implements InterfaceCliente {
    int id;
    String nombre = "cliente";
    InterfaceServidor SERVIDOR;
    InterfaceCliente USUARIO;
    JTextArea areaChat;
    boolean conexionActiva=false;
    DefaultListModel listaUsuarios;
    ventanaIndividual ventanaChat;
    
    public Cliente(DefaultListModel listaUsu){
        super();
        listaUsuarios = listaUsu;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    
    @Override
    public int conectarS(){
    System.out.println("Llegamos a Conectar");

        //Iniciamos el gestor de seguridad
        if (System.getSecurityManager() == null) { System.setProperty("java.security.policy", "./src/server.policy"); System.setSecurityManager(new SecurityManager()); }
        //Intentamos acceder al servidor
        try{ 
            //Buscamos en Registry
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",2000);
            // Almacenamos el servidor para que sea accesible
            SERVIDOR = (InterfaceServidor) reg.lookup("servidor");

            //Usamos como ID el numero de usuarios conectados( ya que hemos sido los ultimos en conectar el numero coincide)
            id = SERVIDOR.getNumeroUsuarios();
            //Obtenemos la lista de usuarios y comprobamos si podemos registrar el nombre
            ArrayList<String> listaUsuarios = SERVIDOR.getUsuariosConectados();
            if(listaUsuarios.contains(nombre)){
                 return -1;                //Error, nombre de usuario ya existe
            }

            //Cast del objeto
            InterfaceCliente stubCliente= (InterfaceCliente) UnicastRemoteObject.exportObject(this ,id);
            reg.rebind(nombre, stubCliente);

            //Registro del usuario
            SERVIDOR.registrar(nombre);
            System.out.println("Conexion Realizada con Exito");
            conexionActiva=true;
            this.actualizaListaUsuarios();
            return 0;
            
        }catch(NotBoundException | RemoteException e){
            System.out.print(e);
            return -1;
        }
    }
    

    @Override
    public String getNombre() throws RemoteException {
        return this.nombre;
    }

    @Override
    public void desconectar() throws RemoteException {
        if(SERVIDOR!=null)
            SERVIDOR.logoutUsuario(this.nombre);
        if(USUARIO!=null){    
            USUARIO.recibirMensaje(nombre, nombre+" ha salido del chat\n");
            USUARIO=null;
        }
    }

    @Override
    public void actualizaListaUsuarios() throws RemoteException {
       ArrayList<String> listaAux=SERVIDOR.getUsuariosConectados();
       listaUsuarios.clear();
       for(String i: listaAux){
           listaUsuarios.addElement(i);
        } 
    }

    @Override
    public int conectarU(String usuario) throws RemoteException {
     try{
        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 2000);
        USUARIO = (Interfaces.InterfaceCliente) reg.lookup(usuario);
        if(USUARIO.conectarU2(nombre)==0)
            abrirChat(usuario);
        return 0;
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e);
            return -1;
        }
    }
    
    @Override
     public int conectarU2(String usuario) throws RemoteException {
     try{
        Registry reg = LocateRegistry.getRegistry("127.0.0.1", 2000);
        USUARIO = (Interfaces.InterfaceCliente) reg.lookup(usuario);
        abrirChat(usuario);
        return 0;
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e);
            return -1;
        }
    }

    public void abrirChat(String usuario){
        ventanaChat = new ventanaIndividual(usuario, this);
        ventanaChat.setVisible(true);
    }
     
     
    @Override
    public void recibirMensaje(String usuario, String mensaje) throws RemoteException {
        this.ventanaChat.getAreaTexto().setText(this.ventanaChat.getAreaTexto().getText()+"["+usuario+"]"+mensaje+"\n");
    }
}