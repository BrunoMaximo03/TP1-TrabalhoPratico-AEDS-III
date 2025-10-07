package TP1.AEDS.III.models;
import TP1.AEDS.III.repository.ArquivoBD;
import TP1.AEDS.III.repository.HashExtensivel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAO {
    private ArquivoBD<Boleto> arqBoletos;
    private HashExtensivel<RegistroHashBoleto> indiceBoletos;

    public BoletoDAO() throws Exception {
        // Criar diretório para índices se não existir
        File dirIndices = new File("./dados/indices");
        if (!dirIndices.exists()) dirIndices.mkdirs();
        
        arqBoletos = new ArquivoBD<>("boletos", Boleto.class.getConstructor());
        
        // Inicializar índice hash para boletos
        indiceBoletos = new HashExtensivel<>(
            RegistroHashBoleto.class.getConstructor(),
            4, // 4 registros por cesto
            "./dados/indices/boletos_diretorio.hash_d",
            "./dados/indices/boletos_cestos.hash_c"
        );
    }

    public Boleto buscarBoleto(int id) throws Exception {
        // Busca primeiro no índice hash
        RegistroHashBoleto regHash = indiceBoletos.read(id);
        if (regHash == null) {
            return null; // Boleto não encontrado no índice
        }
        
        // Usa o endereço do índice para buscar diretamente no arquivo
        return arqBoletos.readAtAddress(regHash.getEndereco());
    }

    public boolean incluirBoleto(Boleto boleto) throws Exception {
        // Salva o boleto no arquivo de dados e obtém o endereço
        long endereco = arqBoletos.create(boleto);
        
        if (endereco > 0) {
            // Adiciona o registro no índice hash
            RegistroHashBoleto regHash = new RegistroHashBoleto(boleto.getId(), endereco);
            indiceBoletos.create(regHash);
            return true;
        }
        return false;
    }

    public boolean alterarBoleto(Boleto boleto) throws Exception {
        // Primeiro verifica se o boleto existe no índice
        RegistroHashBoleto regHash = indiceBoletos.read(boleto.getId());
        if (regHash == null) {
            return false; // Boleto não encontrado
        }
        
        // Atualiza o registro no arquivo de dados
        return arqBoletos.update(boleto);
    }

    public boolean excluirBoleto(int id) throws Exception {
        // Remove do arquivo de dados
        boolean removido = arqBoletos.delete(id);
        
        if (removido) {
            // Remove do índice hash
            indiceBoletos.delete(id);
        }
        
        return removido;
    }

    public List<Boleto> listarBoletosPorCliente(int idCliente) throws Exception {
        List<Boleto> boletosDoCLiente = new ArrayList<>();
        
        // Primeiro, vamos verificar o último ID usado no arquivo
        // para não tentar IDs que não existem
        System.out.println("DEBUG: Buscando boletos para cliente ID: " + idCliente);
        
        int id = 1;
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS_VAZIAS = 50; // Parar após 50 IDs consecutivos não encontrados
        
        while (tentativasVazias < MAX_TENTATIVAS_VAZIAS && id <= 10000) {
            try {
                Boleto boleto = buscarBoleto(id); // Agora usa o índice hash
                if (boleto == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0; // Reset contador
                    System.out.println("DEBUG: Boleto encontrado - ID: " + boleto.getId() + ", Cliente: " + boleto.getIdCliente());
                    
                    if (boleto.getIdCliente() == idCliente) {
                        boletosDoCLiente.add(boleto);
                        System.out.println("DEBUG: Boleto adicionado à lista!");
                    }
                }
                id++;
            } catch (Exception e) {
                System.out.println("DEBUG: Erro ao ler boleto ID " + id + ": " + e.getMessage());
                tentativasVazias++;
                id++;
            }
        }
        
        System.out.println("DEBUG: Total de boletos encontrados para o cliente: " + boletosDoCLiente.size());
        return boletosDoCLiente;
    }

    public List<Boleto> listarTodosBoletos() throws Exception {
        List<Boleto> todosBoletos = new ArrayList<>();
        
        int id = 1;
        int tentativasVazias = 0;
        final int MAX_TENTATIVAS_VAZIAS = 50;
        
        while (tentativasVazias < MAX_TENTATIVAS_VAZIAS && id <= 10000) {
            try {
                Boleto boleto = buscarBoleto(id); // Agora usa o índice hash
                if (boleto == null) {
                    tentativasVazias++;
                } else {
                    tentativasVazias = 0;
                    todosBoletos.add(boleto);
                }
                id++;
            } catch (Exception e) {
                tentativasVazias++;
                id++;
            }
        }
        
        return todosBoletos;
    }

    public void close() throws Exception {
        arqBoletos.close();
    }
}