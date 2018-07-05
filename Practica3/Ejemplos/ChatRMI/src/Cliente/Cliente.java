/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Interfaces.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

public class Cliente implements Cliente_I {
    int id;
    String nombre = "cliente";
    Servidor_I stubServidor;
    ArrayList<String> usuarios = new ArrayList<>();
    TreeMap<String, Cliente_I> stubclientes = new TreeMap<>();;
    TreeMap<String, JTextArea> UsuariosGlobal = new TreeMap<>();
    DefaultListModel ListaUsuarios;

    public Cliente(DefaultListModel listaUsu){
        super();
        ListaUsuarios = listaUsu;
    }

    public Cliente(){
        super();
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getUsuarios() {
        return usuarios;
    }
    
    @Override
    public int conectarServidor(){
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "./src/server.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try{ 
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1002);
            stubServidor = (Servidor_I) reg.lookup("server");
            id = stubServidor.getNumeroUsuarios();
            
            ArrayList<String> usuarios = stubServidor.getUsuariosConectados();
            
            if(usuarios.contains(nombre)){
                return -2;
            }
            
            Cliente_I stub = (Cliente_I) UnicastRemoteObject.exportObject(this, id); 
	    reg.rebind(nombre, stub);
            
            stubServidor.registrar(nombre);
            
            System.out.println("Conectado con el servidor");
            return 1;
            
        }catch(NotBoundException | RemoteException e){
            System.out.println(e);
            return -1;
        }
    }
    
    @Override
    public void actualizarUsuarios() throws RemoteException{
        ListaUsuarios.removeAllElements();
        usuarios = stubServidor.getUsuariosConectados();
        
        for(String u: usuarios){
            ListaUsuarios.addElement(u);
        }
        
        ListaUsuarios.removeElement(nombre);
        usuarios.remove(nombre);
    }


    public void desconectarServidor(){
        try {
            for(Map.Entry<String, Cliente_I> i: stubclientes.entrySet()){
                    desconectarUsuario(i.getKey());
            }
            if (stubServidor != null)
                stubServidor.desconectar(nombre);
            
        } catch (RemoteException e) {
            System.err.println(e);
        }
        
    }
    
    
   
    @Override
    public int conectarUsuario(String usuario) throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1002);
            Cliente_I cliente = (Cliente_I) reg.lookup(usuario);
            
            stubclientes.put(usuario, cliente);
            
            abrirChat(usuario);
            
            return 1;
            
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e);
            return -1;
        }
    }
    
    
    public int ConectarConUsuario(String usuario){
        if(UsuariosGlobal.containsKey(usuario))
            return -2;
        try{ 
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1002);
            Cliente_I cliente = (Cliente_I) reg.lookup(usuario);
            
            int resultado = cliente.conectarUsuario(nombre);
            
            if(resultado == 1){
                stubclientes.put(usuario, cliente);
                abrirChat(usuario);
                
                return 1;
            }
            return resultado;
            
        }catch(NotBoundException | RemoteException e){
            return -1;
        }
    }
    
    public void abrirChat(String usuario){
        ventanaChatUsuarios nuevo = new ventanaChatUsuarios(usuario, this);
        UsuariosGlobal.put(usuario, nuevo.getmensajesGlobal());
        nuevo.pack();
        nuevo.setVisible(true);
    }
    
    @Override
    public void recibirMensaje(String usuario, String mensaje) throws RemoteException {
        if (UsuariosGlobal.containsKey(usuario) && stubclientes.containsKey(usuario)){
            JTextArea area = UsuariosGlobal.get(usuario);
            area.setText(area.getText()+mensaje);
        }
    }
    
    public void envio(String usuario, String mensaje) {
        if (UsuariosGlobal.containsKey(usuario) && stubclientes.containsKey(usuario)){
            try {
                String add = usuario+": "+mensaje+"\n";

                stubclientes.get(usuario).recibirMensaje(nombre, add);
                JTextArea area = UsuariosGlobal.get(usuario);
                area.setText(area.getText()+add);

            } catch (RemoteException e) {
                System.err.println(e);
            }
        }
    }
    
    @Override
    public void desconectarUsuario(String usuario) throws RemoteException {
        if (UsuariosGlobal.containsKey(usuario) && stubclientes.containsKey(usuario)){
            UsuariosGlobal.remove(usuario);
            stubclientes.remove(usuario);
        }
    }
    
    public void desconectarDeUsuario(String usuario){
        if (UsuariosGlobal.containsKey(usuario) && stubclientes.containsKey(usuario)){
            try {
                recibirMensaje(usuario, "Has salido del chat");
                stubclientes.get(usuario).recibirMensaje(nombre, "El usuario se ha ido del chat.\n");

                stubclientes.get(usuario).desconectarUsuario(nombre);
                UsuariosGlobal.remove(usuario);
                stubclientes.remove(usuario);

            } catch (RemoteException e) {
                System.err.println(e);
            }
        }
    }
}