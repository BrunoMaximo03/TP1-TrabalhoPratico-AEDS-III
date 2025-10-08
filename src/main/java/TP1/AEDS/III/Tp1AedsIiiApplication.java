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
                System.out.println("\nAEDsIII");
                System.out.println("-------");
                System.out.println("> Início");
                System.out.println("\n1 - Clientes");
                System.out.println("2 - Boletos");
                System.out.println("3 - Utilitários do Sistema");
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
                        menuUtilitarios(console);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
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

    public static void menuUtilitarios(Scanner console) {
        int opcao;
        
        do {
            System.out.println("\n=== UTILITÁRIOS DO SISTEMA ===");
            System.out.println("1 - Debug do Sistema");
            System.out.println("2 - Criar Dados de Teste");
            System.out.println("3 - Analisar Problemas");
            System.out.println("4 - Reset do Sistema");
            System.out.println("0 - Voltar ao Menu Principal");
            
            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            
            try {
                switch (opcao) {
                    case 1:
                        System.out.println("\n🔍 Executando Debug do Sistema...");
                        DebugSistema.main(new String[0]);
                        break;
                    case 2:
                        System.out.println("\n📝 Criando Dados de Teste...");
                        CriarDadosTeste.main(new String[0]);
                        break;
                    case 3:
                        System.out.println("\n🏥 Analisando Problemas...");
                        AnalisadorProblemas.main(new String[0]);
                        break;
                    case 4:
                        System.out.println("\n🧹 Reset do Sistema...");
                        ResetSistema.main(new String[0]);
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
                
                if (opcao != 0) {
                    System.out.println("\nPressione ENTER para continuar...");
                    console.nextLine();
                }
                
            } catch (Exception e) {
                System.err.println("❌ Erro ao executar utilitário: " + e.getMessage());
                e.printStackTrace();
                System.out.println("\nPressione ENTER para continuar...");
                console.nextLine();
            }
            
        } while (opcao != 0);
    }
}

