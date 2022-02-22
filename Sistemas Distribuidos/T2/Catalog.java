import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
//Implementar a interface de loja com Catalog,Business e catalog
public class Catalog extends UnicastRemoteObject implements CatalogInterface{
    private static final long serialVersionUID=1L;
    ArrayList<Produto> catalog=new ArrayList();
    public Catalog()throws RemoteException{
    }

    public static void main(String[] args) throws RemoteException{
        if(args.length != 1){
            System.out.println("Usage: java Catalog <machine>");
            System.exit(1);
        }

        try{
            System.setProperty("java.rmi.server.hostname",args[0]);
            LocateRegistry.createRegistry(1234); 
            System.out.println("java RMI registry created.");
        }catch(RemoteException e){
            System.out.println("java RMI registry already exists");
        }

        try{
            String server="rmi://"+args[0]+":1234/server";  
            Naming.rebind(server,new Catalog()); 

            System.out.println("Server sale is ready.");
        }catch(Exception e){
            System.out.println("Server failed:"+e);
        }


    }

    public void AddProduct(String nome,int quantidade) throws RemoteException{ 
        int flag=1;
        for(int i=0;i<catalog.size();i++){ 
            if(nome.contains(catalog.get(i).getNome())){
                flag=0;
                catalog.get(i).setQuantia(catalog.get(i).getQuantia()+quantidade); 
                break;
            }
        }  
        if(flag==1){
            catalog.add(new Produto(nome,quantidade,0));
        }
        System.out.println("Inserido produto "+nome+" com quantia "+quantidade+" no catalogo de produtos"); 
    }
    public void ImprimeProdutos()throws RemoteException{
        System.out.println("--------------PRODUTOS----------------");
        for(int i=0;i<catalog.size();i++){
            System.out.println("["+i+"]"+catalog.get(i).getNome()+" "+catalog.get(i).getQuantia());
        }
        System.out.println("--------------------------------------");
    }
    public void RetiraProduto(String nome,int quantidade){ 

        //Enquanto eu nao tiro a quantidade que eu quero 
            //Procuro na lista de produtos o PRIMEIRO produto correto 
            for(int i=0;i<catalog.size();i++){
                if(nome.contains(catalog.get(i).getNome())){
                    if((catalog.get(i).getQuantia()-quantidade)<=0){
                        catalog.remove(i);
                    }else{
                        catalog.get(i).setQuantia(catalog.get(i).getQuantia()-quantidade);
                    }
                }
            } 
            
        System.out.println("Retirado "+quantidade+" "+nome+"s");
    }
    public boolean ProdutoEmEstoque(String nome){ 
        for(int i=0;i<catalog.size();i++){
         if(nome.contains(catalog.get(i).getNome())){
             return true;
         }
     }  
     return false;
     }
}