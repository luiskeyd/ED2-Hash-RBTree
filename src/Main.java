import arvore_rubro_negra.MenuArvore;
import hash.MenuHash;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int resposta = -1;

        while (resposta != 0) {
            mostrarMenu();
            resposta = scanner.nextInt();
            scanner.nextLine();

            switch (resposta) {
                case 1:
                    MenuArvore menuArvore = new MenuArvore();
                    menuArvore.iniciar();
                    break;

                case 2:
                    System.out.println("Digite o tamanho do hash");
                    int tamanhoInicial = scanner.nextInt();
                    MenuHash menuHash = new MenuHash(tamanhoInicial);
                    menuHash.iniciar();
                    break;

                case 0:
                    System.out.println("\nEncerrando");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("1. Acessar Árvore Rubro-Negra");
        System.out.println("2. Acessar Tabela Hash");
        System.out.println("0. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }
}