package TP1.AEDS.III;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import TP1.AEDS.III.models.ClienteDAO;
import TP1.AEDS.III.models.Cliente;

public class MenuClientes {
    private ClienteDAO clienteDAO;
    private Scanner console = new Scanner(System.in);

    public MenuClientes() throws Exception {
        clienteDAO = new ClienteDAO();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Clientes");
            System.out.println("\n1 - Buscar por CPF");
            System.out.println("2 - Buscar por ID");
            System.out.println("3 - Incluir");
            System.out.println("4 - Alterar");
            System.out.println("5 - Excluir");
            System.out.println("6 - Listar Todos");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarClientePorCPF();
                    break;
                case 2:
                    buscarClientePorID();
                    break;
                case 3:
                    incluirCliente();
                    break;
                case 4:
                    alterarCliente();
                    break;
                case 5:
                    excluirCliente();
                    break;
                case 6:
                    listarTodosClientes();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private void buscarClientePorCPF() {
        System.out.print("\nCPF do cliente (11 dígitos): ");
        String cpf = console.nextLine();
        try {
            Cliente cliente = clienteDAO.buscarClientePorCPF(cpf);
            if (cliente != null) {
                System.out.println("\n✅ Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.out.println("\n❌ Cliente não encontrado com CPF: " + cpf);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Erro ao buscar cliente: " + e.getMessage());
        }
    }

    private void buscarClientePorID() {
        System.out.print("\nID do cliente: ");
        int id = console.nextInt();
        console.nextLine();
        try {
            Cliente cliente = clienteDAO.buscarCliente(id);
            if (cliente != null) {
                System.out.println("\n✅ Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.out.println("\n❌ Cliente não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("\n❌ Erro ao buscar cliente: " + e.getMessage());
        }
    }

    private void listarTodosClientes() {
        try {
            java.util.List<Cliente> clientes = clienteDAO.listarTodosClientes();
            if (clientes.isEmpty()) {
                System.out.println("\n📝 Nenhum cliente cadastrado.");
            } else {
                System.out.println("\n📋 Lista de todos os clientes:");
                System.out.println("═══════════════════════════════════════");
                for (Cliente cliente : clientes) {
                    System.out.println("ID: " + cliente.getId() + " | CPF: " + cliente.getCPF() + " | Nome: " + cliente.getNome());
                    System.out.println("───────────────────────────────────────");
                }
                System.out.println("Total: " + clientes.size() + " cliente(s)");
            }
        } catch (Exception e) {
            System.out.println("\n❌ Erro ao listar clientes: " + e.getMessage());
        }
    }

    @Deprecated
    private void buscarCliente() {
        // Método mantido para compatibilidade, redireciona para busca por ID
        buscarClientePorID();
    }

    private void incluirCliente() {
        System.out.println("\nInclusão de cliente");

        System.out.print("\nNome: ");
        String nome = console.nextLine();
        System.out.print("CPF (11 dígitos): ");
        String cpf = console.nextLine();
        System.out.print("Salário: ");
        float salario = console.nextFloat();
        console.nextLine();
        System.out.print("Data de nascimento (DD/MM/AAAA): ");
        String dataStr = console.nextLine();
        LocalDate nascimento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            Cliente cliente = new Cliente(nome, cpf, salario, nascimento);
            if (clienteDAO.incluirCliente(cliente)) {
                System.out.println("Cliente incluído com sucesso.");
            } else {
                System.out.println("Erro ao incluir cliente.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir cliente.");
        }
    }

    private void alterarCliente() {
        System.out.print("\nCPF do cliente a ser alterado: ");
        String cpf = console.nextLine();

        try {
            Cliente cliente = clienteDAO.buscarClientePorCPF(cpf);
            if (cliente == null) {
                System.out.println("\n❌ Cliente não encontrado com CPF: " + cpf);
                return;
            }

            System.out.println("\n📝 Cliente encontrado:");
            System.out.println("Nome atual: " + cliente.getNome());
            System.out.println("CPF atual: " + cliente.getCPF());
            System.out.println("Salário atual: " + cliente.getSalario());
            System.out.println("Data nascimento atual: " + cliente.getNascimento());

            System.out.print("\nNovo nome (ENTER para manter): ");
            String nome = console.nextLine();
            if (!nome.trim().isEmpty()) cliente.setNome(nome);

            System.out.print("Novo telefone (ENTER para manter): ");
            String telefone = console.nextLine();
            if (!telefone.trim().isEmpty()) cliente.setTelefone(telefone);

            System.out.print("Novo endereço (ENTER para manter): ");
            String endereco = console.nextLine();
            if (!endereco.trim().isEmpty()) cliente.setEndereco(endereco);

            System.out.print("Novo salário (ENTER para manter): ");
            String salarioStr = console.nextLine();
            if (!salarioStr.trim().isEmpty()) cliente.setSalario(Float.parseFloat(salarioStr));

            System.out.print("Nova data de nascimento (DD/MM/AAAA, ENTER para manter): ");
            String dataStr = console.nextLine();
            if (!dataStr.trim().isEmpty()) {
                cliente.setNascimento(LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            if (clienteDAO.alterarCliente(cliente)) {
                System.out.println("\n✅ Cliente alterado com sucesso!");
            } else {
                System.out.println("\n❌ Erro ao alterar cliente.");
            }
        } catch (Exception e) {
            System.out.println("\n❌ Erro ao alterar cliente: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        System.out.println("\n🗑️ Exclusão de Cliente");
        System.out.println("1 - Excluir por CPF");
        System.out.println("2 - Excluir por ID");
        System.out.print("Opção: ");
        
        int opcao;
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        try {
            Cliente cliente = null;
            String identificador = "";
            
            if (opcao == 1) {
                System.out.print("\nCPF do cliente a ser excluído: ");
                String cpf = console.nextLine();
                cliente = clienteDAO.buscarClientePorCPF(cpf);
                identificador = "CPF " + cpf;
                
                if (cliente != null) {
                    System.out.println("\n📋 Cliente encontrado:");
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.println("CPF: " + cliente.getCPF());
                    
                    System.out.print("\nConfirma exclusão? (S/N): ");
                    char resp = console.next().charAt(0);
                    console.nextLine(); // Limpar buffer
                    
                    if (resp == 'S' || resp == 's') {
                        if (clienteDAO.excluirClientePorCPF(cpf)) {
                            System.out.println("\n✅ Cliente excluído com sucesso!");
                        } else {
                            System.out.println("\n❌ Erro ao excluir cliente.");
                        }
                    } else {
                        System.out.println("\n🚫 Exclusão cancelada.");
                    }
                }
            } else if (opcao == 2) {
                System.out.print("\nID do cliente a ser excluído: ");
                int id = console.nextInt();
                console.nextLine();
                cliente = clienteDAO.buscarCliente(id);
                identificador = "ID " + id;
                
                if (cliente != null) {
                    System.out.println("\n📋 Cliente encontrado:");
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.println("CPF: " + cliente.getCPF());
                    
                    System.out.print("\nConfirma exclusão? (S/N): ");
                    char resp = console.next().charAt(0);
                    console.nextLine(); // Limpar buffer
                    
                    if (resp == 'S' || resp == 's') {
                        if (clienteDAO.excluirCliente(id)) {
                            System.out.println("\n✅ Cliente excluído com sucesso!");
                        } else {
                            System.out.println("\n❌ Erro ao excluir cliente.");
                        }
                    } else {
                        System.out.println("\n🚫 Exclusão cancelada.");
                    }
                }
            } else {
                System.out.println("\n❌ Opção inválida!");
                return;
            }

            if (cliente == null) {
                System.out.println("\n❌ Cliente não encontrado com " + identificador);
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Erro ao excluir cliente: " + e.getMessage());
        }
    }
}
