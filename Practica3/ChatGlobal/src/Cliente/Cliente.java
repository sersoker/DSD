package Cliente;

import java.rmi.RemoteException;
import javax.swing.JTextArea;
import Interfaces.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Cliente implements InterfaceCliente {
    int id;
    String nombre = "cliente";
    InterfaceServidor SERVIDOR;
    JTextArea areaChat;
    boolean conexionActiva=false;

    public Cliente(JTextArea panel){
        super();
        areaChat = panel;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    
    @Override
    public int conectar(){
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
            areaChat.setEnabled(true);
            conexionActiva=true;
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
    public void envioMensaje(String mensaje) throws RemoteException  {
        try {  if (SERVIDOR != null)   {       System.out.println("Hay servidor"); SERVIDOR.mensaje(this.nombre, mensaje);      } }
        catch (RemoteException e) {     System.err.println(e);                                 }
    }


    @Override
    public void recibirMensaje(String mensaje)  throws RemoteException  {
        System.out.println(mensaje);
        
        //Cuando recibimos un mensaje, se añade.
        DateFormat df = new SimpleDateFormat("[HH:mm]:");
        Date today = Calendar.getInstance().getTime();        
        String hora = df.format(today);       
        areaChat.setText(areaChat.getText()+hora+mensaje);
    }

    @Override
    public void desconectar() throws RemoteException {
        SERVIDOR.logoutUsuario(nombre);
    }
}