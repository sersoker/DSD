/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Interfaces.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author Javier
 */
public class Servidor extends UnicastRemoteObject implements Servidor_I {
    TreeMap<String, Cliente_I> ventanas;
    
    Servidor() throws RemoteException{
        super();
        ventanas = new TreeMap<>();
    }   
    
    @Override
    public int getNumeroUsuarios() {
        return ventanas.size();
    }
    
    @Override
    public ArrayList<String> getUsuariosConectados() throws RemoteException {
        return new ArrayList<> (ventanas.keySet());
    }
    
     
    void actualizaUsuarios() throws RemoteException{
        for(Map.Entry<String, Cliente_I> i: ventanas.entrySet()){
            i.getValue().actualizarUsuarios();
        }
    }
    
    @Override
    public int registrar(String usuario) throws RemoteException {

        try {
            if(!ventanas.containsKey(usuario)){
                Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1002);
                ventanas.put(usuario, (Cliente_I) reg.lookup(usuario));
                actualizaUsuarios();
                return 1;
            }
            
        } catch (NotBoundException | RemoteException e) {
            System.err.println(e);
            return -1;
        }
        return -2;
    }

    @Override
    public void desconectar(String usuario) throws RemoteException {
        Cliente_I remove = ventanas.remove(usuario);
        String cadena = "El usuario " + remove.getNombre()+ " se ha desconectado del chat." + "\n";
        actualizaUsuarios();
    }

        
    public static void main (String args[]){

        if(System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "./src/server.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry reg = LocateRegistry.createRegistry(1002);
            reg.bind("server", new Servidor());
            System.out.println("Servidor iniciado y funcionando");
            
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(e);
        }
    }
}
