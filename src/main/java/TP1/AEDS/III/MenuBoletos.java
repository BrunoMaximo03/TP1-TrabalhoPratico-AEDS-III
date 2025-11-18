package TP1.AEDS.III;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import TP1.AEDS.III.models.BoletoDAO;
import TP1.AEDS.III.models.ClienteDAO;
import TP1.AEDS.III.models.Boleto;
import TP1.AEDS.III.models.Cliente;
import TP1.AEDS.III.models.BoletoStatus;

public class MenuBoletos {
    private BoletoDAO boletoDAO;
    private ClienteDAO clienteDAO;
    private Scanner console = new Scanner(System.in);

    public MenuBoletos() throws Exception {
        boletoDAO = new BoletoDAO();
        clienteDAO = new ClienteDAO();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Boletos");
            System.out.println("\n1 - Listar Clientes");
            System.out.println("2 - Gerenciar Boletos por Cliente");
            System.out.println("3 - Incluir Boleto");
            System.out.println("4 - Buscar Boleto");
            System.out.println("5 - Alterar Boleto");
            System.out.println("6 - Excluir Boleto");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    gerenciarBoletosPorCliente();
                    break;
                case 3:
                    incluirBoleto();
                    break;
                case 4:
                    buscarBoleto();
                    break;
                case 5:
                    alterarBoleto();
                    break;
                case 6:
                    excluirBoleto();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private void listarClientes() {
        System.out.println("\n=== CLIENTES CADASTRADOS ===");
        try {
            java.util.List<Cliente> clientes = clienteDAO.listarTodosClientes();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
            } else {
                for (Cliente cliente : clientes) {
                    System.out.println("ID: " + cliente.getId() + " - " + cliente.getNome() + " (CPF: " + cliente.getCPF() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private void gerenciarBoletosPorCliente() {
        System.out.print("\nCPF do cliente: ");
        String cpfCliente = console.nextLine();
        
        try {
            Cliente cliente = clienteDAO.buscarClientePorCPF(cpfCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado com CPF: " + cpfCliente);
                return;
            }
            
            System.out.println("\n=== BOLETOS DO CLIENTE: " + cliente.getNome() + " ===");
            List<Boleto> boletos = boletoDAO.listarBoletosPorCPF(cpfCliente);
            
            if (boletos.isEmpty()) {
                System.out.println("Este cliente não possui boletos.");
            } else {
                for (Boleto boleto : boletos) {
                    System.out.println(boleto);
                    System.out.println("----------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar boletos do cliente: " + e.getMessage());
        }
    }

    private void incluirBoleto() {
        System.out.println("\nInclusao de boleto");
        
        try {
            // Verificar se há clientes cadastrados antes
            ClienteDAO verificacao = new ClienteDAO();
            List<Cliente> clientesExistentes = verificacao.listarTodosClientes();
            
            if (clientesExistentes.isEmpty()) {
                System.out.println("\nE necessario ter clientes cadastrados primeiro.");
                System.out.println("Por favor, cadastre um cliente antes de criar boletos.");
                return;
            }
            
            // Mostrar clientes disponíveis
            listarClientes();
            
            System.out.print("\nCPF do cliente: ");
            String cpfCliente = console.nextLine();
            
            Cliente cliente = clienteDAO.buscarClientePorCPF(cpfCliente);
            if (cliente == null) {
                System.out.println("Cliente nao encontrado com CPF: " + cpfCliente);
                return;
            }
            
            System.out.println("[OK] Cliente encontrado: " + cliente.getNome());
            
            System.out.print("Descricao: ");
            String descricao = console.nextLine();
            
            System.out.print("Valor: ");
            BigDecimal valor = new BigDecimal(console.nextLine());
            
            System.out.print("Data de emissao (DD/MM/AAAA): ");
            String dataEmissaoStr = console.nextLine();
            LocalDate dataEmissao = LocalDate.parse(dataEmissaoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            System.out.print("Data de vencimento (DD/MM/AAAA): ");
            String dataVencimentoStr = console.nextLine();
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            Boleto boleto = new Boleto(0, cpfCliente, dataEmissao, dataVencimento, descricao, valor, BoletoStatus.PENDENTE);
            
            if (boletoDAO.incluirBoleto(boleto)) {
                System.out.println("[OK] Boleto incluido com sucesso.");
            } else {
                System.out.println("[ERRO] Erro ao incluir boleto.");
            }
        } catch (Exception e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private void buscarBoleto() {
        System.out.print("\nID do boleto: ");
        int id = Integer.parseInt(console.nextLine());
        
        try {
            Boleto boleto = boletoDAO.buscarBoleto(id);
            if (boleto != null) {
                // Buscar dados do cliente pelo CPF
                Cliente cliente = clienteDAO.buscarClientePorCPF(boleto.getCPFCliente());
                System.out.println("✅ Boleto encontrado:");
                System.out.println(boleto);
                if (cliente != null) {
                    System.out.println("Cliente...: " + cliente.getNome());
                } else {
                    System.out.println("⚠️ Cliente não encontrado para CPF: " + boleto.getCPFCliente());
                }
            } else {
                System.out.println("❌ Boleto não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar boleto: " + e.getMessage());
        }
    }

    private void alterarBoleto() {
        System.out.print("\nID do boleto a ser alterado: ");
        int id = Integer.parseInt(console.nextLine());
        
        try {
            Boleto boleto = boletoDAO.buscarBoleto(id);
            if (boleto == null) {
                System.out.println("Boleto não encontrado.");
                return;
            }
            
            System.out.print("\nNova descrição (vazio para manter): ");
            String descricao = console.nextLine();
            if (!descricao.isEmpty()) boleto.setDescricao(descricao);
            
            System.out.print("Novo valor (vazio para manter): ");
            String valorStr = console.nextLine();
            if (!valorStr.isEmpty()) boleto.setValor(new BigDecimal(valorStr));
            
            System.out.print("Novo status (PAGO/PENDENTE/CANCELADO, vazio para manter): ");
            String statusStr = console.nextLine();
            if (!statusStr.isEmpty()) {
                try {
                    boleto.setStatus(BoletoStatus.valueOf(statusStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido. Mantendo status atual.");
                }
            }
            
            if (boletoDAO.alterarBoleto(boleto)) {
                System.out.println("Boleto alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar boleto.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar boleto: " + e.getMessage());
        }
    }

    private void excluirBoleto() {
        System.out.print("\nID do boleto a ser excluído: ");
        int id = Integer.parseInt(console.nextLine());
        
        try {
            Boleto boleto = boletoDAO.buscarBoleto(id);
            if (boleto == null) {
                System.out.println("Boleto não encontrado.");
                return;
            }
            
            System.out.println("\nBoleto encontrado:");
            System.out.println(boleto);
            System.out.print("Confirma exclusão? (S/N): ");
            char resp = console.next().charAt(0);
            console.nextLine(); // Limpar buffer
            
            if (resp == 'S' || resp == 's') {
                if (boletoDAO.excluirBoleto(id)) {
                    System.out.print("Boleto excluído com sucesso.");
                } else {
                    System.out.print("Erro ao excluir boleto.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir boleto: " + e.getMessage());
        }
    }
}