public class Produto{
    private String nome;
    private int quantia,preco;

    public Produto(String n,int q,int p){
        nome=n;
        quantia=q;
        preco=p;
    }
    public String getNome(){
        return nome;
    }
    public int getQuantia(){
        return quantia;
    }

    public int getPreco(){
        return preco;
    }

    public void setQuantia(int q){
        quantia=q;
    }
    
    public void setPreco(int p){
        preco=p;
    }
}