package hash;

import java.util.Scanner;

public class MenuHash {
    private Hash<Integer, String> tabelaHash;
    private Scanner scanner;

    public MenuHash(int tamanhoInicial) {
        this.tabelaHash = new Hash<>(tamanhoInicial);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("1. Inserir novo dado");
            System.out.println("2. Buscar dado");
            System.out.println("3. Remover dado");
            System.out.println("4. Visualizar Tabela");
            System.out.println("0. Voltar / Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: inserirDado(); break;
                case 2: buscarDado(); break;
                case 3: removerDado(); break;
                case 4: visualizarHash(); break;
                case 0: System.out.println("saindo..."); break;
                default: System.out.println("opção inválida, tente novamente.");
            }
        }
    }

    private void inserirDado() {
        System.out.print("Digite a chave (Número inteiro): ");
        int chave = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o valor: (String de preferência)");
        String valor = scanner.nextLine();

        tabelaHash.put(chave, valor);
        System.out.println("Dado inserido com sucesso!");
    }

    private void buscarDado() {
        System.out.print("Digite a chave para busca: ");
        int chave = scanner.nextInt();

        String resultado = tabelaHash.get(chave);
        if (resultado != null) {
            System.out.println("Encontrado: " + resultado);
        } else {
            System.out.println("Chave não encontrada na tabela.");
        }
    }

    private void removerDado() {
        System.out.print("Digite a chave para remover: ");
        int chave = scanner.nextInt();

        String removido = tabelaHash.remove(chave);
        if (removido != null) {
            System.out.println("Sucesso! Removido: " + removido);
        } else {
            System.out.println("Erro: Chave não encontrada para remoção.");
        }
    }

    private void visualizarHash() {
        tabelaHash.imprimirTabela();
    }
}
