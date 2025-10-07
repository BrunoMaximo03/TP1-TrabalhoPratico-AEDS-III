package TP1.AEDS.III.models;
import TP1.AEDS.III.repository.ArquivoBD;
import TP1.AEDS.III.repository.HashExtensivel;
import java.io.File;

public class ClienteDAO {
    private ArquivoBD<Cliente> arqClientes;
    private HashExtensivel<RegistroHashCliente> indiceClientes;

    public ClienteDAO() throws Exception {
        // Criar diretório para índices se não existir
        File dirIndices = new File("./dados/indices");
        if (!dirIndices.exists()) dirIndices.mkdirs();
        
        arqClientes = new ArquivoBD<>("clientes", Cliente.class.getConstructor());
        
        // Inicializar índice hash para clientes
        indiceClientes = new HashExtensivel<>(
            RegistroHashCliente.class.getConstructor(),
            4, // 4 registros por cesto
            "./dados/indices/clientes_diretorio.hash_d",
            "./dados/indices/clientes_cestos.hash_c"
        );
    }

    public Cliente buscarCliente(int id) throws Exception {
        // Busca primeiro no índice hash
        RegistroHashCliente regHash = indiceClientes.read(id);
        if (regHash == null) {
            return null; // Cliente não encontrado no índice
        }
        
        // Usa o endereço do índice para buscar diretamente no arquivo
        return lerClienteNoEndereco(regHash.getEndereco());
    }

    public boolean incluirCliente(Cliente cliente) throws Exception {
        // Salva o cliente no arquivo de dados e obtém o endereço
        long endereco = arqClientes.create(cliente);
        
        if (endereco > 0) {
            // Adiciona o registro no índice hash
            RegistroHashCliente regHash = new RegistroHashCliente(cliente.getId(), endereco);
            indiceClientes.create(regHash);
            return true;
        }
        return false;
    }

    public boolean alterarCliente(Cliente cliente) throws Exception {
        // Primeiro verifica se o cliente existe no índice
        RegistroHashCliente regHash = indiceClientes.read(cliente.getId());
        if (regHash == null) {
            return false; // Cliente não encontrado
        }
        
        // Atualiza o registro no arquivo de dados
        boolean atualizado = arqClientes.update(cliente);
        
        // Nota: Não precisa atualizar o índice pois o endereço não muda na atualização
        return atualizado;
    }

    public boolean excluirCliente(int id) throws Exception {
        // Remove do arquivo de dados
        boolean removido = arqClientes.delete(id);
        
        if (removido) {
            // Remove do índice hash
            indiceClientes.delete(id);
        }
        
        return removido;
    }
    
    // Método auxiliar para ler cliente em um endereço específico
    private Cliente lerClienteNoEndereco(long endereco) throws Exception {
        return arqClientes.readAtAddress(endereco);
    }
}
