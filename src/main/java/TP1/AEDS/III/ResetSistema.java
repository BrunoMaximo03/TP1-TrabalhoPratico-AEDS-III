package TP1.AEDS.III;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ResetSistema {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== RESET DO SISTEMA ===");
        System.out.println("Este utilitário irá limpar todos os dados e índices.");
        System.out.println();
        
        // Mostrar status atual
        mostrarStatusAtual();
        
        System.out.println();
        System.out.print("Deseja continuar com o reset? (S/N): ");
        String resposta = scanner.next().toUpperCase();
        
        if (resposta.equals("S") || resposta.equals("SIM")) {
            resetarDados();
            System.out.println();
            System.out.println("✅ Sistema resetado com sucesso!");
            System.out.println("Os próximos clientes e boletos começarão do ID 1.");
        } else {
            System.out.println("❌ Reset cancelado.");
        }
        
        scanner.close();
    }
    
    public static void mostrarStatusAtual() {
        System.out.println("📊 STATUS ATUAL DOS ARQUIVOS:");
        
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
                System.out.println("📁 " + arquivo + " - " + f.length() + " bytes");
            } else {
                System.out.println("❌ " + arquivo + " - não existe");
            }
        }
    }
    
    public static void resetarDados() {
        System.out.println("🧹 Limpando dados...");
        
        // Lista de arquivos para deletar
        String[] arquivos = {
            "./dados/clientes/clientes.db",
            "./dados/boletos/boletos.db",
            "./dados/indices/clientes_diretorio.hash_d",
            "./dados/indices/clientes_cestos.hash_c",
            "./dados/indices/boletos_diretorio.hash_d",
            "./dados/indices/boletos_cestos.hash_c"
        };
        
        int arquivosRemovidos = 0;
        
        for (String arquivo : arquivos) {
            try {
                Path path = Paths.get(arquivo);
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("🗑️ Removido: " + arquivo);
                    arquivosRemovidos++;
                }
            } catch (IOException e) {
                System.out.println("❌ Erro ao remover " + arquivo + ": " + e.getMessage());
            }
        }
        
        System.out.println("📊 Total de arquivos removidos: " + arquivosRemovidos);
    }
    
    // Método para resetar apenas clientes
    public static void resetarClientes() {
        System.out.println("🧹 Limpando apenas dados de clientes...");
        
        String[] arquivos = {
            "./dados/clientes/clientes.db",
            "./dados/indices/clientes_diretorio.hash_d",
            "./dados/indices/clientes_cestos.hash_c"
        };
        
        for (String arquivo : arquivos) {
            try {
                Path path = Paths.get(arquivo);
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("🗑️ Removido: " + arquivo);
                }
            } catch (IOException e) {
                System.out.println("❌ Erro ao remover " + arquivo + ": " + e.getMessage());
            }
        }
    }
    
    // Método para resetar apenas boletos
    public static void resetarBoletos() {
        System.out.println("🧹 Limpando apenas dados de boletos...");
        
        String[] arquivos = {
            "./dados/boletos/boletos.db",
            "./dados/indices/boletos_diretorio.hash_d",
            "./dados/indices/boletos_cestos.hash_c"
        };
        
        for (String arquivo : arquivos) {
            try {
                Path path = Paths.get(arquivo);
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("🗑️ Removido: " + arquivo);
                }
            } catch (IOException e) {
                System.out.println("❌ Erro ao remover " + arquivo + ": " + e.getMessage());
            }
        }
    }
}