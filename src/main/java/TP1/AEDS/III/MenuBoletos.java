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
            // Buscar todos os clientes (implementação simples)
            boolean encontrouClientes = false;
            for (int id = 1; id <= 100; id++) {
                try {
                    Cliente cliente = clienteDAO.buscarCliente(id);
                    if (cliente != null) {
                        System.out.println("ID: " + cliente.getId() + " - " + cliente.getNome() + " (CPF: " + cliente.getCPF() + ")");
                        encontrouClientes = true;
                    }
                } catch (Exception e) {
                    // Cliente não encontrado, continua
                }
            }
            
            if (!encontrouClientes) {
                System.out.println("Nenhum cliente cadastrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private void gerenciarBoletosPorCliente() {
        System.out.print("\nID do cliente: ");
        int idCliente = Integer.parseInt(console.nextLine());
        
        try {
            Cliente cliente = clienteDAO.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }
            
            System.out.println("\n=== BOLETOS DO CLIENTE: " + cliente.getNome() + " ===");
            List<Boleto> boletos = boletoDAO.listarBoletosPorCliente(idCliente);
            
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
        System.out.println("\nInclusão de boleto");
        
        // Primeiro, mostrar clientes disponíveis
        listarClientes();
        
        System.out.print("\nID do cliente: ");
        int idCliente = Integer.parseInt(console.nextLine());
        
        try {
            Cliente cliente = clienteDAO.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }
            
            System.out.print("Descrição: ");
            String descricao = console.nextLine();
            
            System.out.print("Valor: ");
            BigDecimal valor = new BigDecimal(console.nextLine());
            
            System.out.print("Data de emissão (DD/MM/AAAA): ");
            String dataEmissaoStr = console.nextLine();
            LocalDate dataEmissao = LocalDate.parse(dataEmissaoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            System.out.print("Data de vencimento (DD/MM/AAAA): ");
            String dataVencimentoStr = console.nextLine();
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            Boleto boleto = new Boleto(0, idCliente, dataEmissao, dataVencimento, descricao, valor, BoletoStatus.PENDENTE);
            
            if (boletoDAO.incluirBoleto(boleto)) {
                System.out.println("Boleto incluído com sucesso.");
            } else {
                System.out.println("Erro ao incluir boleto.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir boleto: " + e.getMessage());
        }
    }

    private void buscarBoleto() {
        System.out.print("\nID do boleto: ");
        int id = Integer.parseInt(console.nextLine());
        
        try {
            Boleto boleto = boletoDAO.buscarBoleto(id);
            if (boleto != null) {
                // Buscar dados do cliente
                Cliente cliente = clienteDAO.buscarCliente(boleto.getIdCliente());
                System.out.println(boleto);
                if (cliente != null) {
                    System.out.println("Cliente...: " + cliente.getNome());
                }
            } else {
                System.out.println("Boleto não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar boleto: " + e.getMessage());
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