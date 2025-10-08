package TP1.AEDS.III;

import TP1.AEDS.III.models.*;
import java.time.LocalDate;
import java.util.Arrays;

public class TesteHashCPF {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== TESTE DO HASH POR CPF ===");
            
            // Primeiro, resetar os dados para teste limpo
            System.out.println("🧹 Limpando dados anteriores...");
            ResetSistema.resetarDados();
            
            // Criar instância do DAO
            ClienteDAO clienteDAO = new ClienteDAO();
            
            // Teste 1: Criar clientes com CPFs únicos
            System.out.println("\n📝 TESTE 1: Criando clientes com CPFs únicos");
            
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setCPF("12345678901");
            cliente1.setTelefone("11987654321");
            cliente1.setEndereco("Rua das Flores, 123");
            cliente1.setEmails(Arrays.asList("joao@email.com"));
            cliente1.setSalario(5000.00f);
            cliente1.setNascimento(LocalDate.of(1990, 1, 15));
            
            boolean criado1 = clienteDAO.incluirCliente(cliente1);
            System.out.println("✅ Cliente 1 (CPF: 12345678901): " + (criado1 ? "Criado" : "Erro"));
            
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Santos");
            cliente2.setCPF("98765432109");
            cliente2.setTelefone("11123456789");
            cliente2.setEndereco("Av. Central, 456");
            cliente2.setEmails(Arrays.asList("maria@email.com"));
            cliente2.setSalario(4500.00f);
            cliente2.setNascimento(LocalDate.of(1985, 8, 22));
            
            boolean criado2 = clienteDAO.incluirCliente(cliente2);
            System.out.println("✅ Cliente 2 (CPF: 98765432109): " + (criado2 ? "Criado" : "Erro"));
            
            Cliente cliente3 = new Cliente();
            cliente3.setNome("Pedro Oliveira");
            cliente3.setCPF("11111111111");
            cliente3.setTelefone("11999888777");
            cliente3.setEndereco("Rua do Trabalho, 789");
            cliente3.setEmails(Arrays.asList("pedro@empresa.com"));
            cliente3.setSalario(7000.00f);
            cliente3.setNascimento(LocalDate.of(1988, 12, 10));
            
            boolean criado3 = clienteDAO.incluirCliente(cliente3);
            System.out.println("✅ Cliente 3 (CPF: 11111111111): " + (criado3 ? "Criado" : "Erro"));
            
            // Teste 2: Buscar por CPF
            System.out.println("\n🔍 TESTE 2: Buscando clientes por CPF");
            
            Cliente encontrado1 = clienteDAO.buscarClientePorCPF("12345678901");
            System.out.println("Busca CPF 12345678901: " + 
                (encontrado1 != null ? "✅ " + encontrado1.getNome() : "❌ Não encontrado"));
            
            Cliente encontrado2 = clienteDAO.buscarClientePorCPF("98765432109");
            System.out.println("Busca CPF 98765432109: " + 
                (encontrado2 != null ? "✅ " + encontrado2.getNome() : "❌ Não encontrado"));
            
            Cliente encontrado3 = clienteDAO.buscarClientePorCPF("11111111111");
            System.out.println("Busca CPF 11111111111: " + 
                (encontrado3 != null ? "✅ " + encontrado3.getNome() : "❌ Não encontrado"));
            
            // Teste 3: Buscar CPF inexistente
            Cliente naoExiste = clienteDAO.buscarClientePorCPF("99999999999");
            System.out.println("Busca CPF 99999999999: " + 
                (naoExiste != null ? "❌ Encontrado (erro!)" : "✅ Não encontrado (correto)"));
            
            // Teste 4: Tentar criar cliente com CPF duplicado
            System.out.println("\n⚠️ TESTE 3: Tentando criar cliente com CPF duplicado");
            
            Cliente clienteDuplicado = new Cliente();
            clienteDuplicado.setNome("José Duplicado");
            clienteDuplicado.setCPF("12345678901"); // CPF já existe
            clienteDuplicado.setTelefone("11555555555");
            clienteDuplicado.setEndereco("Rua Duplicada, 999");
            clienteDuplicado.setEmails(Arrays.asList("jose@email.com"));
            clienteDuplicado.setSalario(3000.00f);
            clienteDuplicado.setNascimento(LocalDate.of(1992, 5, 10));
            
            try {
                boolean criadoDuplicado = clienteDAO.incluirCliente(clienteDuplicado);
                System.out.println("Cliente com CPF duplicado: " + 
                    (criadoDuplicado ? "❌ Criado (erro!)" : "✅ Rejeitado (correto)"));
            } catch (Exception e) {
                System.out.println("✅ Cliente com CPF duplicado rejeitado: " + e.getMessage());
            }
            
            // Teste 5: Listar todos os clientes
            System.out.println("\n📋 TESTE 4: Listando todos os clientes");
            
            java.util.List<Cliente> todosClientes = clienteDAO.listarTodosClientes();
            System.out.println("Total de clientes encontrados: " + todosClientes.size());
            
            for (Cliente c : todosClientes) {
                System.out.println("- ID: " + c.getId() + " | CPF: " + c.getCPF() + " | Nome: " + c.getNome());
            }
            
            // Teste 6: Performance
            System.out.println("\n⚡ TESTE 5: Teste de Performance");
            
            long inicio = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                clienteDAO.buscarClientePorCPF("12345678901");
            }
            long fim = System.currentTimeMillis();
            
            System.out.println("1000 buscas por CPF executadas em: " + (fim - inicio) + "ms");
            System.out.println("Performance: " + String.format("%.2f", 1000.0 / (fim - inicio)) + " buscas/ms");
            
            System.out.println("\n🎉 TODOS OS TESTES CONCLUÍDOS!");
            
        } catch (Exception e) {
            System.out.println("❌ Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}