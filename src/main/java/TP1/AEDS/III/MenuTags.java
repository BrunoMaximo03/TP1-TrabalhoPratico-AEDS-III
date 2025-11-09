package TP1.AEDS.III;

import java.util.List;
import java.util.Scanner;
import TP1.AEDS.III.models.Tag;
import TP1.AEDS.III.models.TagDAO;
import TP1.AEDS.III.models.Boleto;
import TP1.AEDS.III.models.BoletoDAO;
import TP1.AEDS.III.models.BoletoTagDAO;

public class MenuTags {
    private TagDAO tagDAO;
    private BoletoDAO boletoDAO;
    private BoletoTagDAO boletoTagDAO;
    private Scanner console = new Scanner(System.in);

    public MenuTags() throws Exception {
        tagDAO = new TagDAO();
        boletoDAO = new BoletoDAO();
        boletoTagDAO = new BoletoTagDAO();
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n=== GESTÃO DE TAGS (N:N) ===");
            System.out.println("1 - Criar Tag");
            System.out.println("2 - Listar Tags");
            System.out.println("3 - Buscar Tag");
            System.out.println("4 - Alterar Tag");
            System.out.println("5 - Excluir Tag");
            System.out.println("6 - Adicionar Tag a Boleto");
            System.out.println("7 - Remover Tag de Boleto");
            System.out.println("8 - Ver Tags de um Boleto");
            System.out.println("9 - Ver Boletos por Tag");
            System.out.println("10 - Relatório Completo");
            System.out.println("0 - Voltar");
            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    criarTag();
                    break;
                case 2:
                    listarTodasTags();
                    break;
                case 3:
                    buscarTag();
                    break;
                case 4:
                    alterarTag();
                    break;
                case 5:
                    excluirTag();
                    break;
                case 6:
                    adicionarTagAoBoleto();
                    break;
                case 7:
                    removerTagDoBoleto();
                    break;
                case 8:
                    listarTagsDoBoleto();
                    break;
                case 9:
                    listarBoletosPorTag();
                    break;
                case 10:
                    relatorioCompleto();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n❌ Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private void criarTag() {
        System.out.println("\n=== CRIAR TAG ===");
        System.out.print("Nome: ");
        String nome = console.nextLine();
        
        if (nome.trim().isEmpty()) {
            System.out.println("Erro: Nome não pode ser vazio!");
            return;
        }
        
        try {
            Tag tag = new Tag(nome);
            if (tagDAO.criarTag(tag)) {
                System.out.println("Tag '" + nome + "' criada! ID: " + tag.getId());
            } else {
                System.out.println("Erro ao criar tag.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodasTags() {
        System.out.println("\n=== LISTA DE TAGS ===");
        
        try {
            List<Tag> tags = tagDAO.listarTodasTags();
            
            if (tags.isEmpty()) {
                System.out.println("Nenhuma tag cadastrada.");
            } else {
                for (Tag tag : tags) {
                    System.out.println("[" + tag.getId() + "] " + tag.getNome());
                }
                System.out.println("\nTotal: " + tags.size() + " tag(s)");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarTag() {
        System.out.print("\nID da Tag: ");
        try {
            int id = Integer.parseInt(console.nextLine());
            Tag tag = tagDAO.buscarTag(id);
            
            if (tag != null) {
                int count = boletoTagDAO.contarBoletosPorTag(id);
                System.out.println("\nTag: " + tag.getNome());
                System.out.println("Boletos associados: " + count);
            } else {
                System.out.println("Tag não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void alterarTag() {
        System.out.print("\nID da Tag: ");
        try {
            int id = Integer.parseInt(console.nextLine());
            Tag tag = tagDAO.buscarTag(id);
            
            if (tag == null) {
                System.out.println("Tag não encontrada.");
                return;
            }
            
            System.out.println("Tag atual: " + tag.getNome());
            System.out.print("Novo nome (ENTER para manter): ");
            String novoNome = console.nextLine();
            
            if (!novoNome.trim().isEmpty()) {
                tag.setNome(novoNome);
                if (tagDAO.alterarTag(tag)) {
                    System.out.println("Tag alterada!");
                } else {
                    System.out.println("Erro ao alterar tag.");
                }
            } else {
                System.out.println("Nenhuma alteração.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void excluirTag() {
        System.out.print("\nID da Tag: ");
        try {
            int id = Integer.parseInt(console.nextLine());
            Tag tag = tagDAO.buscarTag(id);
            
            if (tag == null) {
                System.out.println("Tag não encontrada.");
                return;
            }
            
            int count = boletoTagDAO.contarBoletosPorTag(id);
            System.out.println("Tag: " + tag.getNome());
            System.out.println("Associada a " + count + " boleto(s)");
            System.out.print("Confirma exclusão? (S/N): ");
            
            char resp = console.next().charAt(0);
            console.nextLine();
            
            if (resp == 'S' || resp == 's') {
                if (tagDAO.excluirTag(id)) {
                    System.out.println("Tag excluída!");
                } else {
                    System.out.println("Erro ao excluir tag.");
                }
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarTagAoBoleto() {
        System.out.println("\n=== ADICIONAR TAG A BOLETO ===");
        
        try {
            List<Tag> tags = tagDAO.listarTodasTags();
            if (tags.isEmpty()) {
                System.out.println("Nenhuma tag cadastrada.");
                return;
            }
            
            System.out.println("Tags disponíveis:");
            for (Tag tag : tags) {
                System.out.println("  [" + tag.getId() + "] " + tag.getNome());
            }
            
            System.out.print("\nID do Boleto: ");
            int idBoleto = Integer.parseInt(console.nextLine());
            
            Boleto boleto = boletoDAO.buscarBoleto(idBoleto);
            if (boleto == null) {
                System.out.println("Boleto não encontrado!");
                return;
            }
            
            System.out.println("Boleto: " + boleto.getDescricao());
            System.out.print("ID da Tag: ");
            int idTag = Integer.parseInt(console.nextLine());
            
            if (boletoTagDAO.adicionarTagAoBoleto(idBoleto, idTag)) {
                Tag tag = tagDAO.buscarTag(idTag);
                System.out.println("Tag '" + tag.getNome() + "' adicionada!");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerTagDoBoleto() {
        System.out.println("\n=== REMOVER TAG DE BOLETO ===");
        
        try {
            System.out.print("ID do Boleto: ");
            int idBoleto = Integer.parseInt(console.nextLine());
            
            List<Tag> tags = boletoTagDAO.listarTagsDoBoleto(idBoleto);
            if (tags.isEmpty()) {
                System.out.println("Este boleto não possui tags.");
                return;
            }
            
            System.out.println("Tags do boleto:");
            for (Tag tag : tags) {
                System.out.println("  [" + tag.getId() + "] " + tag.getNome());
            }
            
            System.out.print("\nID da Tag a remover: ");
            int idTag = Integer.parseInt(console.nextLine());
            
            if (boletoTagDAO.removerTagDoBoleto(idBoleto, idTag)) {
                System.out.println("Tag removida!");
            } else {
                System.out.println("Erro ao remover tag.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTagsDoBoleto() {
        System.out.print("\nID do Boleto: ");
        try {
            int idBoleto = Integer.parseInt(console.nextLine());
            
            Boleto boleto = boletoDAO.buscarBoleto(idBoleto);
            if (boleto == null) {
                System.out.println("Boleto não encontrado!");
                return;
            }
            
            List<Tag> tags = boletoTagDAO.listarTagsDoBoleto(idBoleto);
            
            System.out.println("\n=== TAGS DO BOLETO #" + idBoleto + " ===");
            System.out.println("Boleto: " + boleto.getDescricao());
            System.out.println("Valor: R$ " + boleto.getValor());
            
            if (tags.isEmpty()) {
                System.out.println("Sem tags.");
            } else {
                System.out.println("Tags (" + tags.size() + "):");
                for (Tag tag : tags) {
                    System.out.println("  - " + tag.getNome());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarBoletosPorTag() {
        try {
            List<Tag> todasTags = tagDAO.listarTodasTags();
            if (todasTags.isEmpty()) {
                System.out.println("\nNenhuma tag cadastrada.");
                return;
            }
            
            System.out.println("\nTags disponíveis:");
            for (Tag tag : todasTags) {
                int count = boletoTagDAO.contarBoletosPorTag(tag.getId());
                System.out.println("  [" + tag.getId() + "] " + tag.getNome() + " (" + count + " boleto(s))");
            }
            
            System.out.print("\nID da Tag: ");
            int idTag = Integer.parseInt(console.nextLine());
            
            Tag tag = tagDAO.buscarTag(idTag);
            if (tag == null) {
                System.out.println("Tag não encontrada!");
                return;
            }
            
            List<Boleto> boletos = boletoTagDAO.listarBoletosPorTag(idTag);
            
            System.out.println("\n=== BOLETOS COM TAG: " + tag.getNome() + " ===");
            
            if (boletos.isEmpty()) {
                System.out.println("Nenhum boleto com esta tag.");
            } else {
                System.out.println("Total: " + boletos.size() + " boleto(s)\n");
                for (Boleto boleto : boletos) {
                    System.out.println("ID: " + boleto.getId() + " | " + boleto.getDescricao());
                    System.out.println("Valor: R$ " + boleto.getValor() + " | Venc: " + boleto.getDataVencimento());
                    System.out.println("Status: " + boleto.getStatus());
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void relatorioCompleto() {
        System.out.println("\n=== RELATÓRIO DE TAGS ===");
        
        try {
            List<Tag> tags = tagDAO.listarTodasTags();
            
            if (tags.isEmpty()) {
                System.out.println("Nenhuma tag cadastrada.");
                return;
            }
            
            System.out.println("Total de tags: " + tags.size());
            
            int totalAssociacoes = 0;
            System.out.println("\nDetalhamento:\n");
            
            for (Tag tag : tags) {
                int count = boletoTagDAO.contarBoletosPorTag(tag.getId());
                totalAssociacoes += count;
                
                System.out.println("Tag: " + tag.getNome() + " (ID: " + tag.getId() + ")");
                System.out.println("Boletos: " + count);
                
                if (count > 0) {
                    List<Boleto> boletos = boletoTagDAO.listarBoletosPorTag(tag.getId());
                    for (Boleto b : boletos) {
                        System.out.println("  - #" + b.getId() + ": " + b.getDescricao());
                    }
                }
                System.out.println();
            }
            
            System.out.println("Total de relacionamentos N:N: " + totalAssociacoes);
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
