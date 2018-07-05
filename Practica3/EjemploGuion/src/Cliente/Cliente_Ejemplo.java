/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Interfaces.Ejemplo_I;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente_Ejemplo {
    public static void main(String[] args){
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.security.policy", "./src/server.policy");
        }
        try{
            Registry registry = LocateRegistry.getRegistry("localhost");
            System.out.println("Buscando el objeto remoto");
            String objetoRemoto = "un_Nombre";  
            Ejemplo_I instancia_local=(Ejemplo_I)registry.lookup(objetoRemoto);
            System.out.println("Invocando el objeto remoto");
            instancia_local.escribir_mensaje(Integer.parseInt(args[1]));
        } catch(Exception e){
            System.err.println("EJEMPLO_I EXCEPCION:");
                    e.printStackTrace();
        }
    }
    
}
