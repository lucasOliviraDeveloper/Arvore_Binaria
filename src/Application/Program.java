package Application;

public class Program {
    public static void main(String[] args) {
        Arvore minhaArvore = new Arvore();
        
        int[] valores = {30, 15, 45, 7, 19, 3, 17, 24, 22, 18, 49};
        
        System.out.println("Inserindo valores e balanceando...");
        for (int v : valores) {
        	minhaArvore = minhaArvore.inserir(new Folha(v));
        }

        System.out.println("\nEstrutura final da Árvore AVL:");
        minhaArvore.imprimir("", false);
    }
}