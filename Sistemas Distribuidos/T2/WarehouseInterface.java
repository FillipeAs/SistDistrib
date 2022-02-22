import java.rmi.*;

public interface WarehouseInterface extends Remote{
    public void AdicionaProdutos(String nome,int quantia) throws RemoteException;
    public void RemoveProduto(String nome,int quantia) throws RemoteException;
    public void ImprimeProdutosEmEstoque() throws RemoteException;
    public boolean ProdutoEmEstoque(String nome) throws RemoteException;
}