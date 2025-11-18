package TP1.AEDS.III;

import TP1.AEDS.III.models.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe para demonstração na apresentação do trabalho.
 * Cria dados organizados para mostrar:
 * 1) CRUD básico
 * 2) Relacionamento 1:N (Cliente → Boletos)
 * 3) Relacionamento N:N (Boleto ↔ Tag)
 */
public class DemonstracaoApresentacao {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("   DEMONSTRACAO - SISTEMA DE GESTAO DE BOLETOS");
        System.out.println("   Trabalho Pratico - AEDS III");
        System.out.println("===============================================================\n");
        
        try {
            demonstrarCRUD();
            demonstrarRelacionamento1N();
            demonstrarRelacionamentoNN();
            exibirResumoFinal();
            
        } catch (Exception e) {
            System.err.println("\n❌ ERRO na demonstração:");
            e.printStackTrace();
        }
    }
    
    /**
     * PARTE 1: Demonstra CRUD completo de uma tabela
     */
    private static void demonstrarCRUD() throws Exception {
        System.out.println("===============================================================");
        System.out.println("PARTE 1: CRUD COMPLETO - TABELA CLIENTES");
        System.out.println("===============================================================\n");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        // CREATE
        System.out.println("📝 [CREATE] Incluindo cliente...");
        Cliente cliente1 = new Cliente(
            "João Silva", 
            "12345678901", 
            3500.00f, 
            LocalDate.of(1990, 5, 15)
        );
        clienteDAO.incluirCliente(cliente1);
        System.out.println("[OK] Cliente incluido com sucesso!");
        System.out.println("  ID: " + cliente1.getId());
        System.out.println("  Nome: " + cliente1.getNome());
        System.out.println("  CPF: " + cliente1.getCPF());
        System.out.println("  Arquivo: dados/clientes/clientes.db");
        System.out.println("  Indice: dados/indices/clientes_cpf_diretorio.hash_d\n");
        
        // READ
        System.out.println("🔍 [READ] Buscando cliente por CPF (Hash O(1))...");
        Cliente clienteBuscado = clienteDAO.buscarClientePorCPF("12345678901");
        System.out.println("[OK] Cliente encontrado:");
        System.out.println("  " + clienteBuscado.getNome() + " - CPF: " + clienteBuscado.getCPF());
        System.out.println("  Salário: R$ " + String.format("%.2f", clienteBuscado.getSalario()));
        System.out.println("  Nascimento: " + clienteBuscado.getNascimento().format(DATE_FORMAT) + "\n");
        
        // UPDATE
        System.out.println("✏️ [UPDATE] Alterando salário do cliente...");
        clienteBuscado.setSalario(4200.00f);
        clienteDAO.alterarCliente(clienteBuscado);
        System.out.println("[OK] Salario atualizado para: R$ 4.200,00\n");
        
        // Verificar UPDATE
        Cliente clienteAtualizado = clienteDAO.buscarClientePorCPF("12345678901");
        System.out.println("[OK] Confirmacao - Novo salario: R$ " + String.format("%.2f", clienteAtualizado.getSalario()));
        System.out.println("  Dado persistido no arquivo .db\n");
        
        // Não fazer DELETE para manter dados para próximas demonstrações
        System.out.println("[INFO] [DELETE] - Nao executado (mantendo dados para demonstracoes seguintes)\n");
        
        pausar();
    }
    
    /**
     * PARTE 2: Demonstra relacionamento 1:N (Cliente → Boletos)
     */
    private static void demonstrarRelacionamento1N() throws Exception {
        System.out.println("===============================================================");
        System.out.println("PARTE 2: RELACIONAMENTO 1:N (Cliente -> Boletos)");
        System.out.println("===============================================================\n");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        BoletoDAO boletoDAO = new BoletoDAO();
        
        // Criar mais um cliente para demonstração
        System.out.println("[INFO] Criando segundo cliente...");
        Cliente cliente2 = new Cliente(
            "Maria Santos", 
            "98765432100", 
            5200.00f, 
            LocalDate.of(1988, 8, 22)
        );
        clienteDAO.incluirCliente(cliente2);
        System.out.println("[OK] Cliente criado: " + cliente2.getNome() + " (CPF: " + cliente2.getCPF() + ")\n");
        
        // Criar 3 boletos para o primeiro cliente (João Silva - CPF: 12345678901)
        System.out.println("[INFO] Criando 3 boletos para Joao Silva (CPF: 12345678901)...\n");
        
        Boleto boleto1 = new Boleto(
            0,
            "12345678901",
            LocalDate.now(),
            LocalDate.now().plusDays(10),
            "Conta de Luz - Novembro",
            new BigDecimal("185.50"),
            BoletoStatus.PENDENTE
        );
        boletoDAO.incluirBoleto(boleto1);
        System.out.println("  ✓ Boleto #" + boleto1.getId() + ": " + boleto1.getDescricao() + 
                          " - R$ " + boleto1.getValor());
        
        Boleto boleto2 = new Boleto(
            0,
            "12345678901",
            LocalDate.now(),
            LocalDate.now().plusDays(15),
            "Conta de Água - Novembro",
            new BigDecimal("95.80"),
            BoletoStatus.PENDENTE
        );
        boletoDAO.incluirBoleto(boleto2);
        System.out.println("  ✓ Boleto #" + boleto2.getId() + ": " + boleto2.getDescricao() + 
                          " - R$ " + boleto2.getValor());
        
        Boleto boleto3 = new Boleto(
            0,
            "12345678901",
            LocalDate.now(),
            LocalDate.now().plusDays(20),
            "Internet - Novembro",
            new BigDecimal("129.90"),
            BoletoStatus.PENDENTE
        );
        boletoDAO.incluirBoleto(boleto3);
        System.out.println("  ✓ Boleto #" + boleto3.getId() + ": " + boleto3.getDescricao() + 
                          " - R$ " + boleto3.getValor());
        
        System.out.println("\n  📁 Arquivo: dados/boletos/boletos.db");
        System.out.println("  📁 Índice: dados/indices/boletos_diretorio.hash_d\n");
        
        // Criar 1 boleto para o segundo cliente
        System.out.println("📝 Criando 1 boleto para Maria Santos (CPF: 98765432100)...\n");
        
        Boleto boleto4 = new Boleto(
            0,
            "98765432100",
            LocalDate.now(),
            LocalDate.now().plusDays(25),
            "Aluguel - Novembro",
            new BigDecimal("1800.00"),
            BoletoStatus.PENDENTE
        );
        boletoDAO.incluirBoleto(boleto4);
        System.out.println("  ✓ Boleto #" + boleto4.getId() + ": " + boleto4.getDescricao() + 
                          " - R$ " + boleto4.getValor() + "\n");
        
        // Demonstrar relacionamento 1:N
        System.out.println("🔗 DEMONSTRAÇÃO DO RELACIONAMENTO 1:N:");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        System.out.println("Cliente: João Silva (CPF: 12345678901)");
        System.out.println("Boletos vinculados (1 Cliente → N Boletos):\n");
        
        List<Boleto> boletosJoao = boletoDAO.listarBoletosPorCPF("12345678901");
        for (Boleto b : boletosJoao) {
            System.out.println("  → Boleto #" + b.getId() + ": " + b.getDescricao() + 
                              " - R$ " + b.getValor() + 
                              " (Venc: " + b.getDataVencimento().format(DATE_FORMAT) + ")");
        }
        
        System.out.println("\n✓ Total de boletos de João Silva: " + boletosJoao.size());
        System.out.println("✓ Relacionamento 1:N comprovado no arquivo boletos.db");
        System.out.println("  (Campo 'cpfCliente' liga cada boleto ao seu cliente)\n");
        
        pausar();
    }
    
    /**
     * PARTE 3: Demonstra relacionamento N:N (Boleto ↔ Tag)
     */
    private static void demonstrarRelacionamentoNN() throws Exception {
        System.out.println("===============================================================");
        System.out.println("PARTE 3: RELACIONAMENTO N:N (Boleto <-> Tag)");
        System.out.println("===============================================================\n");
        
        TagDAO tagDAO = new TagDAO();
        BoletoTagDAO boletoTagDAO = new BoletoTagDAO();
        BoletoDAO boletoDAO = new BoletoDAO();
        
        // Criar Tags
        System.out.println("📝 Criando Tags para categorização...\n");
        
        Tag tagUrgente = new Tag("Urgente");
        tagDAO.criarTag(tagUrgente);
        System.out.println("  ✓ Tag #" + tagUrgente.getId() + ": " + tagUrgente.getNome());
        
        Tag tagRecorrente = new Tag("Recorrente");
        tagDAO.criarTag(tagRecorrente);
        System.out.println("  ✓ Tag #" + tagRecorrente.getId() + ": " + tagRecorrente.getNome());
        
        Tag tagResidencial = new Tag("Residencial");
        tagDAO.criarTag(tagResidencial);
        System.out.println("  ✓ Tag #" + tagResidencial.getId() + ": " + tagResidencial.getNome());
        
        System.out.println("\n  📁 Arquivo: dados/tags/tags.db");
        System.out.println("  📁 Índice: dados/indices/tags_diretorio.hash_d\n");
        
        // Criar relacionamentos N:N
        System.out.println("🔗 Criando relacionamentos N:N (Boleto ↔ Tag)...\n");
        
        // Boleto 1 (Luz) → Tags: Urgente, Recorrente, Residencial
        System.out.println("Boleto #1 (Conta de Luz):");
        boletoTagDAO.adicionarTagAoBoleto(1, tagUrgente.getId());
        System.out.println("  → Tag 'Urgente' adicionada");
        boletoTagDAO.adicionarTagAoBoleto(1, tagRecorrente.getId());
        System.out.println("  → Tag 'Recorrente' adicionada");
        boletoTagDAO.adicionarTagAoBoleto(1, tagResidencial.getId());
        System.out.println("  → Tag 'Residencial' adicionada\n");
        
        // Boleto 2 (Água) → Tags: Recorrente, Residencial
        System.out.println("Boleto #2 (Conta de Água):");
        boletoTagDAO.adicionarTagAoBoleto(2, tagRecorrente.getId());
        System.out.println("  → Tag 'Recorrente' adicionada");
        boletoTagDAO.adicionarTagAoBoleto(2, tagResidencial.getId());
        System.out.println("  → Tag 'Residencial' adicionada\n");
        
        // Boleto 3 (Internet) → Tag: Recorrente
        System.out.println("Boleto #3 (Internet):");
        boletoTagDAO.adicionarTagAoBoleto(3, tagRecorrente.getId());
        System.out.println("  → Tag 'Recorrente' adicionada\n");
        
        System.out.println("  📁 Arquivo: dados/boleto_tag/boleto_tag.db");
        System.out.println("  📁 Índice: dados/indices/boleto_tag_diretorio.hash_d\n");
        
        // Demonstrar navegação BOLETO → TAGS
        System.out.println("🔗 NAVEGAÇÃO: Boleto → Tags (N:N)");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        Boleto boleto1 = boletoDAO.buscarBoleto(1);
        List<Tag> tagsBoleto1 = boletoTagDAO.listarTagsDoBoleto(1);
        System.out.println("Boleto #1: " + boleto1.getDescricao());
        System.out.println("Tags associadas (" + tagsBoleto1.size() + "):");
        for (Tag tag : tagsBoleto1) {
            System.out.println("  → " + tag.getNome());
        }
        
        System.out.println("\n✓ Um boleto pode ter VÁRIAS tags (N)\n");
        
        // Demonstrar navegação TAG → BOLETOS
        System.out.println("🔗 NAVEGAÇÃO: Tag → Boletos (N:N)");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        List<Boleto> boletosRecorrente = boletoTagDAO.listarBoletosPorTag(tagRecorrente.getId());
        System.out.println("Tag: Recorrente");
        System.out.println("Boletos com esta tag (" + boletosRecorrente.size() + "):");
        for (Boleto b : boletosRecorrente) {
            System.out.println("  → Boleto #" + b.getId() + ": " + b.getDescricao() + 
                              " - R$ " + b.getValor());
        }
        
        System.out.println("\n✓ Uma tag pode estar em VÁRIOS boletos (N)");
        System.out.println("✓ Relacionamento N:N comprovado!");
        System.out.println("✓ Tabela intermediária 'boleto_tag.db' gerencia as associações\n");
        
        pausar();
    }
    
    /**
     * Exibe resumo final da demonstração
     */
    private static void exibirResumoFinal() throws Exception {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("RESUMO FINAL - ARQUIVOS GERADOS");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        BoletoDAO boletoDAO = new BoletoDAO();
        TagDAO tagDAO = new TagDAO();
        
        System.out.println("📊 DADOS CRIADOS:\n");
        
        System.out.println("CLIENTES (dados/clientes/clientes.db):");
        List<Cliente> clientes = clienteDAO.listarTodosClientes();
        for (Cliente c : clientes) {
            System.out.println("  • " + c.getNome() + " (CPF: " + c.getCPF() + ")");
        }
        System.out.println("  Total: " + clientes.size() + " clientes\n");
        
        System.out.println("BOLETOS (dados/boletos/boletos.db):");
        List<Boleto> boletos = boletoDAO.listarTodosBoletos();
        for (Boleto b : boletos) {
            System.out.println("  • Boleto #" + b.getId() + ": " + b.getDescricao() + 
                              " - Cliente CPF: " + b.getCPFCliente());
        }
        System.out.println("  Total: " + boletos.size() + " boletos\n");
        
        System.out.println("TAGS (dados/tags/tags.db):");
        List<Tag> tags = tagDAO.listarTodasTags();
        for (Tag t : tags) {
            System.out.println("  • Tag #" + t.getId() + ": " + t.getNome());
        }
        System.out.println("  Total: " + tags.size() + " tags\n");
        
        System.out.println("RELACIONAMENTOS N:N (dados/boleto_tag/boleto_tag.db):");
        BoletoTagDAO boletoTagDAO = new BoletoTagDAO();
        int totalRelacionamentos = 0;
        for (Tag tag : tags) {
            int count = boletoTagDAO.contarBoletosPorTag(tag.getId());
            System.out.println("  • Tag '" + tag.getNome() + "': " + count + " boleto(s)");
            totalRelacionamentos += count;
        }
        System.out.println("  Total: " + totalRelacionamentos + " relacionamentos\n");
        
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("📁 ARQUIVOS .DB PARA DEMONSTRAR:");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        System.out.println("1️⃣  CRUD + Estrutura Base:");
        System.out.println("   dados/clientes/clientes.db");
        System.out.println("   → Cabeçalho (12 bytes) + Registros com lápide\n");
        
        System.out.println("2️⃣  Relacionamento 1:N:");
        System.out.println("   dados/boletos/boletos.db");
        System.out.println("   → Campo 'cpfCliente' liga boleto ao cliente\n");
        
        System.out.println("3️⃣  Relacionamento N:N:");
        System.out.println("   dados/boleto_tag/boleto_tag.db");
        System.out.println("   → Pares (idBoleto, idTag) na tabela intermediária\n");
        
        System.out.println("4️⃣  Índices Hash Extensível (Performance O(1)):");
        System.out.println("   dados/indices/clientes_cpf_diretorio.hash_d");
        System.out.println("   dados/indices/boletos_diretorio.hash_d");
        System.out.println("   dados/indices/tags_diretorio.hash_d");
        System.out.println("   dados/indices/boleto_tag_diretorio.hash_d\n");
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║               ✓ DEMONSTRAÇÃO CONCLUÍDA!                   ║");
        System.out.println("║   Todos os arquivos .db foram criados com sucesso.        ║");
        System.out.println("║   Pronto para apresentação!                               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Pausa para o apresentador explicar
     */
    private static void pausar() {
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("Pressione ENTER para continuar...");
        System.out.println("─────────────────────────────────────────────────────────\n");
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignora
        }
    }
}
