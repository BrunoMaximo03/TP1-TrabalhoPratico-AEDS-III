package TP1.AEDS.III;

import TP1.AEDS.III.models.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class CriarDadosTeste {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== CRIANDO DADOS DE TESTE ===");
            criarClientesTeste();
            criarBoletosTeste();
            System.out.println("\n✅ Dados de teste criados com sucesso!");
            System.out.println("Execute o DebugSistema para verificar se foram salvos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void criarClientesTeste() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            
            // Cliente 1
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setCPF("12345678901");
            cliente1.setTelefone("11987654321");
            cliente1.setEndereco("Rua das Flores, 123");
            cliente1.setEmails(Arrays.asList("joao@email.com", "joao.silva@gmail.com"));
            cliente1.setSalario(5000.00f);
            cliente1.setNascimento(LocalDate.of(1993, 5, 15));
            
            boolean criado1 = clienteDAO.incluirCliente(cliente1);
            System.out.println("Cliente 1 criado: " + criado1 + " - ID: " + cliente1.getId());
            
            // Cliente 2
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Santos");
            cliente2.setCPF("98765432109");
            cliente2.setTelefone("11123456789");
            cliente2.setEndereco("Av. Central, 456");
            cliente2.setEmails(Arrays.asList("maria@email.com"));
            cliente2.setSalario(4500.00f);
            cliente2.setNascimento(LocalDate.of(1995, 8, 22));
            
            boolean criado2 = clienteDAO.incluirCliente(cliente2);
            System.out.println("Cliente 2 criado: " + criado2 + " - ID: " + cliente2.getId());
            
            // Cliente 3
            Cliente cliente3 = new Cliente();
            cliente3.setNome("Pedro Oliveira");
            cliente3.setCPF("55555555555");
            cliente3.setTelefone("11999888777");
            cliente3.setEndereco("Rua do Trabalho, 789");
            cliente3.setEmails(Arrays.asList("pedro@empresa.com", "pedro.oliveira@work.com"));
            cliente3.setSalario(7000.00f);
            cliente3.setNascimento(LocalDate.of(1988, 12, 10));
            
            boolean criado3 = clienteDAO.incluirCliente(cliente3);
            System.out.println("Cliente 3 criado: " + criado3 + " - ID: " + cliente3.getId());
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void criarBoletosTeste() {
        try {
            BoletoDAO boletoDAO = new BoletoDAO();
            
            // Boleto 1 - Cliente 1
            Boleto boleto1 = new Boleto();
            boleto1.setIdCliente(1);
            boleto1.setDescricao("Conta de Energia Elétrica");
            boleto1.setValor(new BigDecimal("150.75"));
            boleto1.setDataVencimento(LocalDate.now().plusDays(30));
            boleto1.setStatus(BoletoStatus.PENDENTE);
            
            boolean criado1 = boletoDAO.incluirBoleto(boleto1);
            System.out.println("Boleto 1 criado: " + criado1 + " - ID: " + boleto1.getId());
            
            // Boleto 2 - Cliente 1
            Boleto boleto2 = new Boleto();
            boleto2.setIdCliente(1);
            boleto2.setDescricao("Conta de Água");
            boleto2.setValor(new BigDecimal("85.30"));
            boleto2.setDataVencimento(LocalDate.now().plusDays(15));
            boleto2.setStatus(BoletoStatus.PENDENTE);
            
            boolean criado2 = boletoDAO.incluirBoleto(boleto2);
            System.out.println("Boleto 2 criado: " + criado2 + " - ID: " + boleto2.getId());
            
            // Boleto 3 - Cliente 2
            Boleto boleto3 = new Boleto();
            boleto3.setIdCliente(2);
            boleto3.setDescricao("Internet Banda Larga");
            boleto3.setValor(new BigDecimal("99.90"));
            boleto3.setDataVencimento(LocalDate.now().plusDays(20));
            boleto3.setStatus(BoletoStatus.PENDENTE);
            
            boolean criado3 = boletoDAO.incluirBoleto(boleto3);
            System.out.println("Boleto 3 criado: " + criado3 + " - ID: " + boleto3.getId());
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar boletos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}