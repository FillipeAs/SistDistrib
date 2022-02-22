import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class Billing extends UnicastRemoteObject implements BillingInterface{
    private static final long serialVersionUID=1;
    ArrayList<Produto> cart=new ArrayList();

    public Billing() throws RemoteException{}
    
    public static void main(String[] args) throws RemoteException{
        if(args.length != 1){
            System.out.println("Usage: java Billing <machine>");
            System.exit(1);
        }

        try{
            System.setProperty("java.rmi.server.hostname",args[0]);
            LocateRegistry.createRegistry(2345); 
            System.out.println("java RMI registry created.");
        }catch(RemoteException e){
            System.out.println("java RMI registry already exists");
        }

        try{
            String server="rmi://"+args[0]+":2345/server";  
            Naming.rebind(server,new Billing()); 

            System.out.println("Server Billing is ready.");
        }catch(Exception e){
            System.out.println("Server failed:"+e);
        }


    }

    public void AdicionarItem(String nome,int quantidade,int preco) throws RemoteException{ 
        int flag=1;
        for(int i=0;i<cart.size();i++){ 
            if(nome.contains(cart.get(i).getNome())){
                flag=0;
                cart.get(i).setQuantia(cart.get(i).getQuantia()+quantidade); 
                break;
            }
        }  
        if(flag==1){
            cart.add(new Produto(nome,quantidade,preco));
        }
        System.out.println("Inserido produto "+nome+" com quantia "+quantidade+" no carrinho de produtos"); 
    }

    public void RemoveAllItem()throws RemoteException{
        cart=new ArrayList();
    }

    public int TotalAPagar()throws RemoteException{
        int total=0;
        for(int i=0;i<cart.size();i++){
            total+=cart.get(i).getPreco()*cart.get(i).getQuantia();
        }
        return total;
    }

    public void ImprimeNotaFiscal()throws RemoteException{
        System.out.println("------NOTA FISCAL------");
        for(int i=0;i<cart.size();i++){
            System.out.println(cart.get(i).getQuantia()+" "+cart.get(i).getNome()+".........R$"+cart.get(i).getPreco());
        }
        System.out.println("_____________________________________");
        System.out.println("-----Total: R$"+TotalAPagar()+"-----");
    }
}