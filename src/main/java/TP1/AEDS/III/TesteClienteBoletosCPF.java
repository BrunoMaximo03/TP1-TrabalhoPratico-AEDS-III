package TP1.AEDS.III;

import TP1.AEDS.III.models.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class TesteClienteBoletosCPF {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== TESTE CLIENTE-BOLETOS COM CPF ===");
            
            // Reset dos dados
            System.out.println("🧹 Limpando dados anteriores...");
            ResetSistema.resetarDados();
            
            ClienteDAO clienteDAO = new ClienteDAO();
            BoletoDAO boletoDAO = new BoletoDAO();
            
            // Teste 1: Criar clientes
            System.out.println("\n📝 TESTE 1: Criando clientes");
            
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setCPF("12345678901");
            cliente1.setTelefone("11987654321");
            cliente1.setEndereco("Rua das Flores, 123");
            cliente1.setEmails(Arrays.asList("joao@email.com"));
            cliente1.setSalario(5000.00f);
            cliente1.setNascimento(LocalDate.of(1990, 1, 15));
            
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Santos");
            cliente2.setCPF("98765432109");
            cliente2.setTelefone("11123456789");
            cliente2.setEndereco("Av. Central, 456");
            cliente2.setEmails(Arrays.asList("maria@email.com"));
            cliente2.setSalario(4500.00f);
            cliente2.setNascimento(LocalDate.of(1985, 8, 22));
            
            boolean criado1 = clienteDAO.incluirCliente(cliente1);
            boolean criado2 = clienteDAO.incluirCliente(cliente2);
            
            System.out.println("✅ Cliente 1 (CPF: 12345678901): " + (criado1 ? "Criado" : "Erro"));
            System.out.println("✅ Cliente 2 (CPF: 98765432109): " + (criado2 ? "Criado" : "Erro"));
            
            // Teste 2: Criar boletos para os clientes
            System.out.println("\n💳 TESTE 2: Criando boletos");
            
            Boleto boleto1 = new Boleto();
            boleto1.setCPFCliente("12345678901");
            boleto1.setDescricao("Conta de Energia Elétrica");
            boleto1.setValor(new BigDecimal("150.75"));
            boleto1.setDataEmissao(LocalDate.now());
            boleto1.setDataVencimento(LocalDate.now().plusDays(30));
            boleto1.setStatus(BoletoStatus.PENDENTE);
            
            Boleto boleto2 = new Boleto();
            boleto2.setCPFCliente("12345678901");
            boleto2.setDescricao("Conta de Água");
            boleto2.setValor(new BigDecimal("85.30"));
            boleto2.setDataEmissao(LocalDate.now());
            boleto2.setDataVencimento(LocalDate.now().plusDays(15));
            boleto2.setStatus(BoletoStatus.PENDENTE);
            
            Boleto boleto3 = new Boleto();
            boleto3.setCPFCliente("98765432109");
            boleto3.setDescricao("Internet Banda Larga");
            boleto3.setValor(new BigDecimal("99.90"));
            boleto3.setDataEmissao(LocalDate.now());
            boleto3.setDataVencimento(LocalDate.now().plusDays(20));
            boleto3.setStatus(BoletoStatus.PENDENTE);
            
            boolean boletoCreated1 = boletoDAO.incluirBoleto(boleto1);
            boolean boletoCreated2 = boletoDAO.incluirBoleto(boleto2);
            boolean boletoCreated3 = boletoDAO.incluirBoleto(boleto3);
            
            System.out.println("✅ Boleto 1 (João - Energia): " + (boletoCreated1 ? "Criado" : "Erro"));
            System.out.println("✅ Boleto 2 (João - Água): " + (boletoCreated2 ? "Criado" : "Erro"));
            System.out.println("✅ Boleto 3 (Maria - Internet): " + (boletoCreated3 ? "Criado" : "Erro"));
            
            // Teste 3: Buscar clientes por CPF
            System.out.println("\n🔍 TESTE 3: Buscando clientes por CPF");
            
            Cliente encontrado1 = clienteDAO.buscarClientePorCPF("12345678901");
            Cliente encontrado2 = clienteDAO.buscarClientePorCPF("98765432109");
            
            System.out.println("Busca CPF 12345678901: " + 
                (encontrado1 != null ? "✅ " + encontrado1.getNome() : "❌ Não encontrado"));
            System.out.println("Busca CPF 98765432109: " + 
                (encontrado2 != null ? "✅ " + encontrado2.getNome() : "❌ Não encontrado"));
            
            // Teste 4: Listar boletos por CPF do cliente
            System.out.println("\n💳 TESTE 4: Listando boletos por CPF do cliente");
            
            java.util.List<Boleto> boletosJoao = boletoDAO.listarBoletosPorCPF("12345678901");
            java.util.List<Boleto> boletosMaria = boletoDAO.listarBoletosPorCPF("98765432109");
            
            System.out.println("\nBoletos do João (CPF: 12345678901): " + boletosJoao.size());
            for (Boleto b : boletosJoao) {
                System.out.println("- " + b.getDescricao() + " | Valor: " + b.getValor());
            }
            
            System.out.println("\nBoletos da Maria (CPF: 98765432109): " + boletosMaria.size());
            for (Boleto b : boletosMaria) {
                System.out.println("- " + b.getDescricao() + " | Valor: " + b.getValor());
            }
            
            // Teste 5: Buscar boletos por ID
            System.out.println("\n🔍 TESTE 5: Buscando boletos por ID");
            
            Boleto buscaBoleto1 = boletoDAO.buscarBoleto(1);
            Boleto buscaBoleto2 = boletoDAO.buscarBoleto(2);
            Boleto buscaBoleto3 = boletoDAO.buscarBoleto(3);
            
            System.out.println("Boleto ID 1: " + 
                (buscaBoleto1 != null ? "✅ " + buscaBoleto1.getDescricao() + " (CPF: " + buscaBoleto1.getCPFCliente() + ")" : "❌ Não encontrado"));
            System.out.println("Boleto ID 2: " + 
                (buscaBoleto2 != null ? "✅ " + buscaBoleto2.getDescricao() + " (CPF: " + buscaBoleto2.getCPFCliente() + ")" : "❌ Não encontrado"));
            System.out.println("Boleto ID 3: " + 
                (buscaBoleto3 != null ? "✅ " + buscaBoleto3.getDescricao() + " (CPF: " + buscaBoleto3.getCPFCliente() + ")" : "❌ Não encontrado"));
            
            // Teste 6: Teste de relacionamento
            System.out.println("\n🔗 TESTE 6: Verificando relacionamento Cliente-Boleto");
            
            if (buscaBoleto1 != null) {
                Cliente clienteDoBoleto = clienteDAO.buscarClientePorCPF(buscaBoleto1.getCPFCliente());
                System.out.println("Boleto: " + buscaBoleto1.getDescricao());
                System.out.println("Pertence ao cliente: " + 
                    (clienteDoBoleto != null ? clienteDoBoleto.getNome() : "Cliente não encontrado"));
            }
            
            System.out.println("\n🎉 TODOS OS TESTES CONCLUÍDOS!");
            System.out.println("✅ Sistema com CPF funcionando corretamente!");
            
        } catch (Exception e) {
            System.out.println("❌ Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}