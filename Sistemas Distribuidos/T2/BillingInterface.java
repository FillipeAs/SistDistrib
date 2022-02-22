import java.rmi.*;

public interface BillingInterface extends Remote{
    public void AdicionarItem(String nome,int quantia,int preco) throws RemoteException;
    public void RemoveAllItem() throws RemoteException;
    public int TotalAPagar() throws RemoteException;
    public void ImprimeNotaFiscal() throws RemoteException;
}