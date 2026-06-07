package arvore_rubro_negra;

import java.util.Scanner;

public class MenuArvore {
    private RBTree<Integer> arvore;
    private Scanner scanner;

    public MenuArvore() {
        this.arvore = new RBTree<>();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("1. Inserir novo dado");
            System.out.println("2. Buscar dado");
            System.out.println("3. Remover dado");
            System.out.println("4. Visualizar Árvore");
            System.out.println("0. Voltar / Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: inserirDado(); break;
                case 2: buscarDado(); break;
                case 3: removerDado(); break;
                case 4: visualizarArvore(); break;
                case 0: System.out.println("Saindo do menu Árvore..."); break;
                default: System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void inserirDado() {
        System.out.print("Digite a chave (Número inteiro): ");
        int chave = scanner.nextInt();
        scanner.nextLine();

        arvore.rbInsert(chave);
        System.out.println("Dado inserido com sucesso!");
    }

    private void buscarDado() {
        System.out.print("Digite a chave para busca: ");
        int chave = scanner.nextInt();

        Integer resultado = arvore.buscarValor(chave);

        if (resultado != null) {
            System.out.println("Encontrado: " + resultado);
        } else {
            System.out.println("Chave não encontrada na árvore.");
        }
    }

    private void removerDado() {
        System.out.print("Digite a chave para remover: ");
        int chave = scanner.nextInt();

        Integer existe = arvore.buscarValor(chave);

        if (existe != null) {
            arvore.rbDelete(chave);
            System.out.println("Sucesso! Removido: " + existe);
        } else {
            System.out.println("Erro: Chave não encontrada para remoção.");
        }
    }

    private void visualizarArvore() {
        arvore.imprimirArvore();
    }
}
