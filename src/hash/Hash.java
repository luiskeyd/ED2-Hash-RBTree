package hash;

public class Hash<K, V> {
    private enum Status {
        Livre, Ocupado, Removido
    }

    // Estrutura de cada bloco do Hash
    private static class Entrada<K, V> {
        K chave;
        V valor;
        Status status;

        public Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
            this.status = Status.Livre;
        }
    }

    private Entrada<K, V>[] tabela;
    private int capacidade; // tamanho total
    private int ocupacao; // quantos blocos têm valores dentro

    private static final double A = 0.6180339887;

    public Hash(int tamanhoInicial) {
        this.capacidade = tamanhoInicial;
        this.tabela = new Entrada[capacidade];
        this.ocupacao = 0;

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new Entrada<>(null, null);
        }
    }

    private int mapeamentoMultiplicacao(K chave) {
        if (chave == null) return 0;

        int k = Math.abs(chave.hashCode());

        double parteFracionaria = (k * A) % 1.0;
        return (int) (capacidade * parteFracionaria);
    }

    public void put(K chave, V valor) {
        if (ocupacao >= capacidade * 0.7) {
            redimencionar();
        }

        int k = 0;
        int indice = mapeamentoMultiplicacao(chave);

        while (tabela[indice].status == Status.Ocupado) {
            if (tabela[indice].chave.equals(chave)) {
                tabela[indice].valor = valor;
                return;
            }

            k++;
            indice = (indice + k) % capacidade;
        }

        tabela[indice].chave = chave;
        tabela[indice].valor = valor;
        tabela[indice].status = Status.Ocupado;
        ocupacao++;
    }

    public V get(K chave) {
        int k = 0;
        int indice = mapeamentoMultiplicacao(chave);

        while (tabela[indice].status != Status.Livre) {
            if (tabela[indice].status == Status.Ocupado && tabela[indice].chave.equals(chave)) {
                return tabela[indice].valor;
            }

            k++;
            indice = (indice + k) % capacidade;
        }

        return null;
    }

    public V remove(K chave) {
        int k = 0;
        int indice = mapeamentoMultiplicacao(chave);

        while (tabela[indice].status != Status.Livre) {
            if (tabela[indice].status == Status.Ocupado && tabela[indice].chave.equals(chave)) {
                V valorRemovido = tabela[indice].valor;

                tabela[indice].status = Status.Removido;
                tabela[indice].chave = null;
                tabela[indice].valor = null;
                ocupacao--;

                return valorRemovido;
            }

            k++;
            indice = (indice + k) % capacidade;
        }

        return null;
    }

    private void redimencionar() {
        int novaCapacidade = capacidade * 3;
        Entrada<K, V>[] tabelaAntiga = this.tabela;

        this.capacidade = novaCapacidade;
        this.tabela = new Entrada[novaCapacidade];
        this.ocupacao = 0;

        for (int i = 0; i < novaCapacidade; i++) {
            tabela[i] = new Entrada<>(null, null);
        }

        for (Entrada<K, V> entrada : tabelaAntiga) {
            if (entrada.status == Status.Ocupado) {
                put(entrada.chave, entrada.valor);
            }
        }
    }

    public void imprimirTabela() {
        System.out.println("Capacidade: " + capacidade + " | Ocupados: " + ocupacao);

        for (int i = 0; i < capacidade; i++) {
            Entrada<K, V> slot = tabela[i];

            System.out.print("[" + i + "] -> ");
            if (slot.status == Status.Livre) {
                System.out.println("(Livre)");
            } else if (slot.status == Status.Removido) {
                System.out.println("(Removido)");
            } else {
                System.out.println("Chave: " + slot.chave + " | Valor: " + slot.valor);
            }
        }
    }
}
