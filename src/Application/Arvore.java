package Application;

public class Arvore {
    private Folha folha;
    private Arvore esquerda;
    private Arvore direita;
    private int altura; 

    public Arvore() {
        this.folha = null;
        this.esquerda = null;
        this.direita = null;
        this.altura = 0;
    }

    public Arvore(Folha folha) {
        this.folha = folha;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }

    public void imprimir(String prefixo, boolean isEsquerda) {
        if (this.folha != null) {
            System.out.println(prefixo + (isEsquerda ? "├── " : "└── ") + this.folha.getValor());
            if (this.esquerda != null) {
                this.esquerda.imprimir(prefixo + (isEsquerda ? "│   " : "    "), true);
            } else if (this.direita != null) {
                System.out.println(prefixo + (isEsquerda ? "│   " : "    ") + "├── NULL");
            }
            
            if (this.direita != null) {
                this.direita.imprimir(prefixo + (isEsquerda ? "│   " : "    "), false);
            } else if (this.esquerda != null) {
                System.out.println(prefixo + (isEsquerda ? "│   " : "    ") + "└── NULL");
            }
        }
    }

    private int getAltura(Arvore a) {
        return (a == null) ? 0 : a.altura;
    }

    private int getFatorBalanceamento() {
        if (this.folha == null) return 0;
        return getAltura(this.esquerda) - getAltura(this.direita);
    }

    private void atualizarAltura() {
        this.altura = 1 + Math.max(getAltura(this.esquerda), getAltura(this.direita));
    }

    // --- Rotações ---

    private Arvore rotacaoDireita() {
        Arvore novaRaiz = this.esquerda;
        Arvore t2 = novaRaiz.direita;

        novaRaiz.direita = this;
        this.esquerda = t2;

        this.atualizarAltura();
        novaRaiz.atualizarAltura();

        return novaRaiz;
    }

    private Arvore rotacaoEsquerda() {
        Arvore novaRaiz = this.direita;
        Arvore t2 = novaRaiz.esquerda;

        novaRaiz.esquerda = this;
        this.direita = t2;

        this.atualizarAltura();
        novaRaiz.atualizarAltura();

        return novaRaiz;
    }
    public Arvore inserir(Folha novo) {
        if (this.folha == null) {
            return new Arvore(novo);
        }

        if (novo.getValor() < this.folha.getValor()) {
            this.esquerda = (this.esquerda == null)
                ? new Arvore(novo)
                : this.esquerda.inserir(novo);
        } 
        else if (novo.getValor() > this.folha.getValor()) {
            this.direita = (this.direita == null)
                ? new Arvore(novo)
                : this.direita.inserir(novo);
        } 
        else {
            return this; 
        }

        atualizarAltura();
        int fb = getFatorBalanceamento();

        // LL
        if (fb > 1 && novo.getValor() < this.esquerda.folha.getValor())
            return rotacaoDireita();

        // RR
        if (fb < -1 && novo.getValor() > this.direita.folha.getValor())
            return rotacaoEsquerda();

        // LR
        if (fb > 1 && novo.getValor() > this.esquerda.folha.getValor()) {
            this.esquerda = this.esquerda.rotacaoEsquerda();
            return rotacaoDireita();
        }

        // RL
        if (fb < -1 && novo.getValor() < this.direita.folha.getValor()) {
            this.direita = this.direita.rotacaoDireita();
            return rotacaoEsquerda();
        }

        return this;
    
            
        }
    }
