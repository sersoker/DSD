import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculadora_I extends Remote {
  public int suma (int op1, int op2) throws RemoteException;
  public int resta (int op1, int op2) throws RemoteException;
  ...
}
