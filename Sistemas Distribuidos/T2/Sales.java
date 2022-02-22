import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;

public class Sales{
    public static void main(String[] args){ 
           ArrayList<Produto> billing=new ArrayList();
           ArrayList<Produto> warehouse=new ArrayList();

           if(args.length!=2){
             System.out.println("Usage: java ShoppingClient <machine> <porta de 0 a 2>");
             System.exit(1);
           }

           String remoteHostName=args[0];
           //Servidor de Catalog
           String connectLocation1="rmi://"+remoteHostName+":1234/server";

           //Servidor de Billing
           String connectLocation2="rmi://"+remoteHostName+":2345/server";

           //Servidor de Warehouse
           String connectLocation3="rmi://"+remoteHostName+":3456/server";
           
           //Numero aleatorio entre 0 e 1 

           CatalogInterface catalogo=null;
           BillingInterface bill=null;
           WarehouseInterface ware=null;
           
           Scanner scan=new Scanner(System.in);
            try{ 
                      //Conecta ao catalogo
                      System.out.println("Connecting client at:"+connectLocation1);
                      catalogo=(CatalogInterface)Naming.lookup(connectLocation1); 
                      System.out.println("Connecting client at:"+connectLocation2);
                      bill=(BillingInterface) Naming.lookup(connectLocation2);
                      System.out.println("Connecting client at:"+connectLocation3);
                      ware=(WarehouseInterface) Naming.lookup(connectLocation3);
            }catch(Exception e){
              System.out.println ("Sales failed: ");
              e.printStackTrace();
            } 

            try { 
                //SE args[2] for 0, conecto com o warehouse
                if(args[1].contains("0")){
                    System.out.println("You are connected to the Warehouse server!");
                    System.out.println("What'd you like to do?");
                    System.out.println("1. Store products\n2. Remove products\n3. List products\n4. Chack if product is in stock");
                    int opt=scan.nextInt();
                    if(opt==1){
                        System.out.println("What product and how many would you like to add?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        System.out.print("\nQuantity:");
                        int qtd=scan.nextInt();
                        ware.AdicionaProdutos(nome,qtd);
                    }else if(opt==2){
                        System.out.println("What product and how many would you like to remove?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        System.out.print("\nQuantity:");
                        int qtd=scan.nextInt();
                        ware.RemoveProduto(nome,qtd);
                    }else if(opt==3){
                          ware.ImprimeProdutosEmEstoque();
                    }else if(opt==4){
                        System.out.println("What product you'd like to check?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        ware.ProdutoEmEstoque(nome);
                    }
                //Se args[2] for 1, conecto com o catalog
                }else if(args[1].contains("1")){
                    System.out.println("You are connected to the Catalog server!");
                    System.out.println("What'd you like to do?");
                    System.out.println("1. Put products on catalog\n2. Remove products from catalog\n3. List products in catalog\n4. Chack if product is in catalog");
                    int opt=scan.nextInt();
                    if(opt==1){
                        System.out.println("What product and how many would you like to add?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        System.out.print("\nQuantity:");
                        int qtd=scan.nextInt();
                        catalogo.AddProduct(nome,qtd);
                    }else if(opt==2){
                        System.out.println("What product and how many would you like to remove?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        System.out.print("\nQuantity:");
                        int qtd=scan.nextInt();
                        catalogo.RetiraProduto(nome,qtd);
                    }else if(opt==3){
                          catalogo.ImprimeProdutos();
                    }else if(opt==4){
                        System.out.println("What product you'd like to check?");
                        System.out.print("Product:");
                        String nome=scan.next();
                        catalogo.ProdutoEmEstoque(nome); 
                    }
                  //se args[2] for 2, conecto com o billing
                }else if(args[1].contains("2")){
                    System.out.println("You are connected to the Billing server!");
                    System.out.println("What'd you like to do?");
                    System.out.println("1. Register product \n2. Make note");
                    int opt=scan.nextInt();
                    if(opt==1){
                      System.out.println("What product,how many would you like to add and how much do they cost?");
                      System.out.print("Product:");
                      String nome=scan.next();
                      System.out.print("\nQuantity:");
                      int qtd=scan.nextInt();
                      System.out.print("\nPreco:");
                      int preco=scan.nextInt();
                      bill.AdicionarItem(nome,qtd,preco);
                    }else if(opt==2){
                        bill.ImprimeNotaFiscal();
                        bill.RemoveAllItem();
                    }
                }else{
                  System.out.println("ERROR! You didn't connect to any services" );
                  System.exit(1);
                }  
               
            } catch (RemoteException e) {
              e.printStackTrace();
            }  
    }

}