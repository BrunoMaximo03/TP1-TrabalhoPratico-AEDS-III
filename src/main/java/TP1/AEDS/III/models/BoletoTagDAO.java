// Classe que gerencia o relacionamento N:N
package TP1.AEDS.III.models;

import TP1.AEDS.III.repository.ArquivoBD;
import TP1.AEDS.III.repository.HashExtensivel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BoletoTagDAO {
    private ArquivoBD<BoletoTag> arquivoBoletoTag;
    private HashExtensivel<RegistroHashBoletoTag> indiceBoletoTag;
    private TagDAO tagDAO;
    private BoletoDAO boletoDAO;
    
    public BoletoTagDAO() throws Exception {
        // Criar diretório para índices se não existir
        File dirIndices = new File("./dados/indices");
        if (!dirIndices.exists()) dirIndices.mkdirs();
        
        this.arquivoBoletoTag = new ArquivoBD<>("boleto_tag", BoletoTag.class.getConstructor());
        
        // Inicializar índice hash para boleto_tag
        indiceBoletoTag = new HashExtensivel<>(
            RegistroHashBoletoTag.class.getConstructor(),
            4, // 4 registros por cesto
            "./dados/indices/boleto_tag_diretorio.hash_d",
            "./dados/indices/boleto_tag_cestos.hash_c"
        );
        
        this.tagDAO = new TagDAO();
        this.boletoDAO = new BoletoDAO();
    }
    
    // Adicionar tag a um boleto
    public boolean adicionarTagAoBoleto(int idBoleto, int idTag) throws Exception {
        // Verificar se boleto e tag existem
        Boleto boleto = boletoDAO.buscarBoleto(idBoleto);
        Tag tag = tagDAO.buscarTag(idTag);
        
        if (boleto == null) {
            System.out.println("❌ Boleto não encontrado!");
            return false;
        }
        
        if (tag == null) {
            System.out.println("❌ Tag não encontrada!");
            return false;
        }
        
        // Verificar se a associação já existe
        if (verificarAssociacao(idBoleto, idTag)) {
            System.out.println("⚠️ Esta tag já está associada a este boleto!");
            return false;
        }
        
        BoletoTag bt = new BoletoTag(idBoleto, idTag);
        long endereco = arquivoBoletoTag.create(bt);
        
        if (endereco > 0) {
            // Adiciona no índice hash
            RegistroHashBoletoTag regHash = new RegistroHashBoletoTag(bt.getId(), endereco);
            indiceBoletoTag.create(regHash);
            return true;
        }
        return false;
    }
    
    // Verificar se associação já existe
    private boolean verificarAssociacao(int idBoleto, int idTag) throws Exception {
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS = 50;
        
        for (int i = 1; i <= 1000 && tentativasVazias < MAX_TENTATIVAS; i++) {
            try {
                RegistroHashBoletoTag regHash = indiceBoletoTag.read(i);
                if (regHash == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0;
                    BoletoTag bt = arquivoBoletoTag.readAtAddress(regHash.getEndereco());
                    if (bt != null && bt.getIdBoleto() == idBoleto && bt.getIdTag() == idTag) {
                        return true;
                    }
                }
            } catch (Exception e) {
                tentativasVazias++;
            }
        }
        return false;
    }
    
    // Remover tag de um boleto
    public boolean removerTagDoBoleto(int idBoleto, int idTag) throws Exception {
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS = 50;
        
        for (int i = 1; i <= 1000 && tentativasVazias < MAX_TENTATIVAS; i++) {
            try {
                RegistroHashBoletoTag regHash = indiceBoletoTag.read(i);
                if (regHash == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0;
                    BoletoTag bt = arquivoBoletoTag.readAtAddress(regHash.getEndereco());
                    if (bt != null && bt.getIdBoleto() == idBoleto && bt.getIdTag() == idTag) {
                        boolean removido = arquivoBoletoTag.delete(bt.getId());
                        if (removido) {
                            indiceBoletoTag.delete(bt.getId());
                        }
                        return removido;
                    }
                }
            } catch (Exception e) {
                tentativasVazias++;
            }
        }
        return false;
    }
    
    // Listar todas as tags de um boleto específico
    public List<Tag> listarTagsDoBoleto(int idBoleto) throws Exception {
        List<Tag> tags = new ArrayList<>();
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS = 50;
        
        for (int i = 1; i <= 1000 && tentativasVazias < MAX_TENTATIVAS; i++) {
            try {
                RegistroHashBoletoTag regHash = indiceBoletoTag.read(i);
                if (regHash == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0;
                    BoletoTag bt = arquivoBoletoTag.readAtAddress(regHash.getEndereco());
                    if (bt != null && bt.getIdBoleto() == idBoleto) {
                        Tag tag = tagDAO.buscarTag(bt.getIdTag());
                        if (tag != null) {
                            tags.add(tag);
                        }
                    }
                }
            } catch (Exception e) {
                tentativasVazias++;
            }
        }
        
        return tags;
    }
    
    // Listar todos os boletos com uma tag específica
    public List<Boleto> listarBoletosPorTag(int idTag) throws Exception {
        List<Boleto> boletos = new ArrayList<>();
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS = 50;
        
        for (int i = 1; i <= 1000 && tentativasVazias < MAX_TENTATIVAS; i++) {
            try {
                RegistroHashBoletoTag regHash = indiceBoletoTag.read(i);
                if (regHash == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0;
                    BoletoTag bt = arquivoBoletoTag.readAtAddress(regHash.getEndereco());
                    if (bt != null && bt.getIdTag() == idTag) {
                        Boleto boleto = boletoDAO.buscarBoleto(bt.getIdBoleto());
                        if (boleto != null) {
                            boletos.add(boleto);
                        }
                    }
                }
            } catch (Exception e) {
                tentativasVazias++;
            }
        }
        
        return boletos;
    }
    
    // Contar quantas tags um boleto tem
    public int contarTagsDoBoleto(int idBoleto) throws Exception {
        return listarTagsDoBoleto(idBoleto).size();
    }
    
    // Contar quantos boletos têm uma tag
    public int contarBoletosPorTag(int idTag) throws Exception {
        return listarBoletosPorTag(idTag).size();
    }
}
