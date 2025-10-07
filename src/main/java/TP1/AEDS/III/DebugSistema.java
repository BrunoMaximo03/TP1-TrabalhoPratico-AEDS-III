package TP1.AEDS.III;

import java.io.File;
import java.io.RandomAccessFile;
import TP1.AEDS.III.models.*;
import TP1.AEDS.III.repository.HashExtensivel;

public class DebugSistema {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== DEBUG DO SISTEMA ===");
            verificarArquivos();
            verificarClientes();
            verificarBoletos();
            verificarIndicesHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void verificarArquivos() {
        System.out.println("\n1. VERIFICANDO ARQUIVOS:");
        
        String[] arquivos = {
            "./dados/clientes/clientes.db",
            "./dados/boletos/boletos.db",
            "./dados/indices/clientes_diretorio.hash_d",
            "./dados/indices/clientes_cestos.hash_c",
            "./dados/indices/boletos_diretorio.hash_d",
            "./dados/indices/boletos_cestos.hash_c"
        };
        
        for (String arquivo : arquivos) {
            File f = new File(arquivo);
            if (f.exists()) {
                System.out.println("✅ " + arquivo + " - Tamanho: " + f.length() + " bytes");
            } else {
                System.out.println("❌ " + arquivo + " - NÃO EXISTE");
            }
        }
    }
    
    public static void verificarClientes() {
        System.out.println("\n2. VERIFICANDO DADOS DE CLIENTES:");
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            
            // Verificar cabeçalho do arquivo
            File f = new File("./dados/clientes/clientes.db");
            if (f.exists() && f.length() >= 12) {
                RandomAccessFile arq = new RandomAccessFile("./dados/clientes/clientes.db", "r");
                arq.seek(0);
                int ultimoId = arq.readInt();
                long listaExcluidos = arq.readLong();
                System.out.println("Último ID usado: " + ultimoId);
                System.out.println("Lista excluídos: " + listaExcluidos);
                
                // Tentar ler registros
                System.out.println("\nTentando ler clientes do ID 1 ao 5:");
                for (int i = 1; i <= 5; i++) {
                    try {
                        Cliente cliente = clienteDAO.buscarCliente(i);
                        if (cliente != null) {
                            System.out.println("✅ Cliente ID " + i + ": " + cliente.getNome());
                        } else {
                            System.out.println("❌ Cliente ID " + i + ": não encontrado");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Cliente ID " + i + ": erro - " + e.getMessage());
                    }
                }
                arq.close();
            } else {
                System.out.println("❌ Arquivo de clientes não existe ou está vazio");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao verificar clientes: " + e.getMessage());
        }
    }
    
    public static void verificarBoletos() {
        System.out.println("\n3. VERIFICANDO DADOS DE BOLETOS:");
        try {
            BoletoDAO boletoDAO = new BoletoDAO();
            
            // Verificar cabeçalho do arquivo
            File f = new File("./dados/boletos/boletos.db");
            if (f.exists() && f.length() >= 12) {
                RandomAccessFile arq = new RandomAccessFile("./dados/boletos/boletos.db", "r");
                arq.seek(0);
                int ultimoId = arq.readInt();
                long listaExcluidos = arq.readLong();
                System.out.println("Último ID usado: " + ultimoId);
                System.out.println("Lista excluídos: " + listaExcluidos);
                
                // Tentar ler registros
                System.out.println("\nTentando ler boletos do ID 1 ao 5:");
                for (int i = 1; i <= 5; i++) {
                    try {
                        Boleto boleto = boletoDAO.buscarBoleto(i);
                        if (boleto != null) {
                            System.out.println("✅ Boleto ID " + i + ": " + boleto.getDescricao());
                        } else {
                            System.out.println("❌ Boleto ID " + i + ": não encontrado");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Boleto ID " + i + ": erro - " + e.getMessage());
                    }
                }
                arq.close();
            } else {
                System.out.println("❌ Arquivo de boletos não existe ou está vazio");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao verificar boletos: " + e.getMessage());
        }
    }
    
    public static void verificarIndicesHash() {
        System.out.println("\n4. VERIFICANDO ÍNDICES HASH:");
        
        try {
            // Verificar hash de clientes
            System.out.println("\n--- HASH DE CLIENTES ---");
            HashExtensivel<RegistroHashCliente> hashClientes = new HashExtensivel<>(
                RegistroHashCliente.class.getConstructor(),
                4,
                "./dados/indices/clientes_diretorio.hash_d",
                "./dados/indices/clientes_cestos.hash_c"
            );
            hashClientes.print();
            
            // Testar busca no hash
            System.out.println("\nTestando busca no hash de clientes:");
            for (int i = 1; i <= 3; i++) {
                try {
                    RegistroHashCliente reg = hashClientes.read(i);
                    if (reg != null) {
                        System.out.println("✅ Hash Cliente ID " + i + ": endereço " + reg.getEndereco());
                    } else {
                        System.out.println("❌ Hash Cliente ID " + i + ": não encontrado");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Hash Cliente ID " + i + ": erro - " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao verificar hash de clientes: " + e.getMessage());
        }
        
        try {
            // Verificar hash de boletos
            System.out.println("\n--- HASH DE BOLETOS ---");
            HashExtensivel<RegistroHashBoleto> hashBoletos = new HashExtensivel<>(
                RegistroHashBoleto.class.getConstructor(),
                4,
                "./dados/indices/boletos_diretorio.hash_d",
                "./dados/indices/boletos_cestos.hash_c"
            );
            hashBoletos.print();
            
            // Testar busca no hash
            System.out.println("\nTestando busca no hash de boletos:");
            for (int i = 1; i <= 3; i++) {
                try {
                    RegistroHashBoleto reg = hashBoletos.read(i);
                    if (reg != null) {
                        System.out.println("✅ Hash Boleto ID " + i + ": endereço " + reg.getEndereco());
                    } else {
                        System.out.println("❌ Hash Boleto ID " + i + ": não encontrado");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Hash Boleto ID " + i + ": erro - " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao verificar hash de boletos: " + e.getMessage());
        }
    }
}