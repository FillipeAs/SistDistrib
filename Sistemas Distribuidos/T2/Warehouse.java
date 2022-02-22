import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class Warehouse extends UnicastRemoteObject implements WarehouseInterface{
    private static final long serialVersionUID=1;
    ArrayList<Produto> warehouse=new ArrayList();

    public Warehouse() throws RemoteException{}
    
    public static void main(String[] args) throws RemoteException{
        if(args.length != 1){
            System.out.println("Usage: java Warehouse <machine>");
            System.exit(1);
        }

        try{
            System.setProperty("java.rmi.server.hostname",args[0]);
            LocateRegistry.createRegistry(3456); 
            System.out.println("java RMI registry created.");
        }catch(RemoteException e){
            System.out.println("java RMI registry already exists");
        }

        try{
            String server="rmi://"+args[0]+":3456/server";  
            Naming.rebind(server,new Warehouse()); 

            System.out.println("Server Warehouse is ready.");
        }catch(Exception e){
            System.out.println("Server failed:"+e);
        }


    }
    public void AdicionaProdutos(String nome,int quantia)throws RemoteException{
        int flag=1;
        for(int i=0;i<warehouse.size();i++){ 
            if(nome.contains(warehouse.get(i).getNome())){
                flag=0;
                warehouse.get(i).setQuantia(warehouse.get(i).getQuantia()+quantia); 
                break;
            }
        }  
        if(flag==1){
            warehouse.add(new Produto(nome,quantia,0));
        }
        System.out.println("Inserido produto "+nome+" com quantia "+quantia+" no carrinho de produtos"); 

    }
    public void RemoveProduto(String nome,int quantia)throws RemoteException{ 
        //Enquanto eu nao tiro a quantia que eu quero 
            //Procuro na lista de produtos o PRIMEIRO produto correto 
            for(int i=0;i<warehouse.size();i++){
                if(nome.contains(warehouse.get(i).getNome())){
                    if((warehouse.get(i).getQuantia()-quantia)<=0){
                        warehouse.remove(i);
                    }else{
                        warehouse.get(i).setQuantia(warehouse.get(i).getQuantia()-quantia);
                    }
                }
            } 
            
        System.out.println("Retirado "+quantia+" "+nome+"s");

    }
    public void ImprimeProdutosEmEstoque()throws RemoteException{
        System.out.println("----WAREHOUSE----");
        for(int i=0;i<warehouse.size();i++){
            System.out.println(warehouse.get(i).getNome());
        }
        System.out.println("----------------");
    }
    
    public boolean ProdutoEmEstoque(String nome)throws RemoteException{ 
       for(int i=0;i<warehouse.size();i++){
        if(nome.contains(warehouse.get(i).getNome())){
            return true;
        }
    }  
    return false;
    }
}