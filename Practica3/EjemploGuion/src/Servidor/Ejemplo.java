/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import Interfaces.Ejemplo_I;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Ejemplo implements Ejemplo_I{
    @Override
    public void escribir_mensaje(int id_proceso) {
        System.out.println("Recibida peticion de proceso"+id_proceso);
        if(id_proceso == 0){
            try{
                System.out.println("Empezamos a dormir");
                Thread.sleep(5000);
                System.out.println("Terminamos de dormir");
            }
            catch(Exception e){
                System.err.println("Ejemplo excepcion:");
                e.printStackTrace();
            }
        }
        System.out.println("\nHebra"+id_proceso);
    }

    public static void main(String [] args){
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            Ejemplo_I prueba = new Ejemplo();
            Ejemplo_I stub = (Ejemplo_I)UnicastRemoteObject.exportObject(prueba, 0);
                Registry registry = LocateRegistry.getRegistry();
                String objetoRemoto = "un_Nombre";
                registry.rebind(objetoRemoto, stub);
                    System.out.println("Ejemplo bound");
        } catch(Exception e){
            System.err.println("EJEMPLO EXCEPCION:");
            e.printStackTrace();
        }
    }

    
};


