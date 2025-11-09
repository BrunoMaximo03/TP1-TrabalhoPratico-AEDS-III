package TP1.AEDS.III;

import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tp1AedsIiiApplication {

	public static void main(String[] args) {
		// Comentar o Spring Boot para rodar apenas o sistema de arquivos
		// SpringApplication.run(Tp1AedsIiiApplication.class, args);

		Scanner console = new Scanner(System.in);
        int opcao;

        try {
            do {
                System.out.println("\n=== SISTEMA DE GESTÃO DE BOLETOS ===");
                System.out.println("1 - Clientes");
                System.out.println("2 - Boletos");
                System.out.println("3 - Tags (N:N)");
                System.out.println("0 - Sair");
                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        MenuClientes menuClientes = new MenuClientes();
                        menuClientes.menu();
                        break;
                    case 2:
                        MenuBoletos menuBoletos = new MenuBoletos();
                        menuBoletos.menu();
                        break;
                    case 3:
                        MenuTags menuTags = new MenuTags();
                        menuTags.menu();
                        break;
                    case 0:
                        System.out.println("\nEncerrando sistema...");
                        break;
                    default:
                        System.out.println("\nOpção inválida!");
                        break;
                }
            } while (opcao != 0);

        } catch (Exception e) {
            System.err.println("Erro fatal no sistema:");
            e.printStackTrace();
        } finally {
            console.close();
        }
    }
}

