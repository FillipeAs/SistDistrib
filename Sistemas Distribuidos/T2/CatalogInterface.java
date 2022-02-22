import java.rmi.*;
public interface CatalogInterface extends Remote{
    public void AddProduct(String nome,int quantidade) throws RemoteException;
    public void ImprimeProdutos()throws RemoteException;
    public void RetiraProduto(String nome,int quantidade) throws RemoteException;
    public boolean ProdutoEmEstoque(String nome) throws RemoteException;
}