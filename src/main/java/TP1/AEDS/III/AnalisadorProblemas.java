package TP1.AEDS.III;

import java.io.File;
import java.io.RandomAccessFile;

public class AnalisadorProblemas {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== ANALISADOR DE PROBLEMAS DO SISTEMA ===");
            analisarClientes();
            analisarBoletos();
            diagnosticarProblemas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void analisarClientes() {
        System.out.println("\n🔍 ANÁLISE DETALHADA DE CLIENTES:");
        
        try {
            File arquivo = new File("./dados/clientes/clientes.db");
            if (!arquivo.exists()) {
                System.out.println("❌ Arquivo de clientes não existe.");
                return;
            }
            
            RandomAccessFile arq = new RandomAccessFile(arquivo, "r");
            
            // Ler cabeçalho
            arq.seek(0);
            int ultimoId = arq.readInt();
            long listaExcluidos = arq.readLong();
            
            System.out.println("📊 CABEÇALHO:");
            System.out.println("  - Último ID usado: " + ultimoId);
            System.out.println("  - Lista de excluídos: " + listaExcluidos);
            System.out.println("  - Tamanho do arquivo: " + arquivo.length() + " bytes");
            
            // Analisar registros
            System.out.println("\n📋 ANÁLISE DE REGISTROS:");
            long posicao = 12; // Após o cabeçalho
            int registroEncontrado = 0;
            int registroExcluido = 0;
            
            while (posicao < arq.length()) {
                arq.seek(posicao);
                
                if (arq.length() - posicao < 5) break; // Não há dados suficientes
                
                byte lapide = arq.readByte();
                int tamanho = arq.readInt();
                
                System.out.printf("  Posição %d: ", posicao);
                
                if (lapide == ' ') {
                    System.out.println("✅ ATIVO - Tamanho: " + tamanho + " bytes");
                    registroEncontrado++;
                    
                    // Tentar ler o ID
                    if (tamanho > 0 && posicao + 5 + tamanho <= arq.length()) {
                        int id = arq.readInt();
                        System.out.println("     ID do registro: " + id);
                    }
                } else if (lapide == '*') {
                    System.out.println("❌ EXCLUÍDO - Tamanho: " + tamanho + " bytes");
                    registroExcluido++;
                } else {
                    System.out.println("⚠️ LÁPIDE INVÁLIDA: '" + (char)lapide + "' (0x" + 
                                     Integer.toHexString(lapide & 0xFF) + ")");
                }
                
                posicao += 5 + Math.max(0, tamanho); // 1 byte lápide + 4 bytes tamanho + dados
            }
            
            System.out.println("\n📈 RESUMO:");
            System.out.println("  - Registros ativos: " + registroEncontrado);
            System.out.println("  - Registros excluídos: " + registroExcluido);
            System.out.println("  - Total analisado: " + (registroEncontrado + registroExcluido));
            
            arq.close();
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao analisar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void analisarBoletos() {
        System.out.println("\n🔍 ANÁLISE DETALHADA DE BOLETOS:");
        
        try {
            File arquivo = new File("./dados/boletos/boletos.db");
            if (!arquivo.exists()) {
                System.out.println("❌ Arquivo de boletos não existe.");
                return;
            }
            
            RandomAccessFile arq = new RandomAccessFile(arquivo, "r");
            
            // Ler cabeçalho
            arq.seek(0);
            int ultimoId = arq.readInt();
            long listaExcluidos = arq.readLong();
            
            System.out.println("📊 CABEÇALHO:");
            System.out.println("  - Último ID usado: " + ultimoId);
            System.out.println("  - Lista de excluídos: " + listaExcluidos);
            System.out.println("  - Tamanho do arquivo: " + arquivo.length() + " bytes");
            
            // Analisar registros
            System.out.println("\n📋 ANÁLISE DE REGISTROS:");
            long posicao = 12; // Após o cabeçalho
            int registroEncontrado = 0;
            int registroExcluido = 0;
            
            while (posicao < arq.length()) {
                arq.seek(posicao);
                
                if (arq.length() - posicao < 5) break;
                
                byte lapide = arq.readByte();
                int tamanho = arq.readInt();
                
                System.out.printf("  Posição %d: ", posicao);
                
                if (lapide == ' ') {
                    System.out.println("✅ ATIVO - Tamanho: " + tamanho + " bytes");
                    registroEncontrado++;
                    
                    // Tentar ler o ID
                    if (tamanho > 0 && posicao + 5 + tamanho <= arq.length()) {
                        int id = arq.readInt();
                        System.out.println("     ID do registro: " + id);
                    }
                } else if (lapide == '*') {
                    System.out.println("❌ EXCLUÍDO - Tamanho: " + tamanho + " bytes");
                    registroExcluido++;
                } else {
                    System.out.println("⚠️ LÁPIDE INVÁLIDA: '" + (char)lapide + "' (0x" + 
                                     Integer.toHexString(lapide & 0xFF) + ")");
                }
                
                posicao += 5 + Math.max(0, tamanho);
            }
            
            System.out.println("\n📈 RESUMO:");
            System.out.println("  - Registros ativos: " + registroEncontrado);
            System.out.println("  - Registros excluídos: " + registroExcluido);
            System.out.println("  - Total analisado: " + (registroEncontrado + registroExcluido));
            
            arq.close();
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao analisar boletos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void diagnosticarProblemas() {
        System.out.println("\n🏥 DIAGNÓSTICO DE PROBLEMAS:");
        
        System.out.println("\n🔍 POSSÍVEIS CAUSAS DE IDs PULANDO:");
        System.out.println("1. ❌ Exclusões anteriores - IDs excluídos não são reutilizados");
        System.out.println("2. ❌ Erros durante criação - ID incrementado mas registro não salvo");
        System.out.println("3. ❌ Testes anteriores - Dados de teste criados e excluídos");
        System.out.println("4. ❌ Falha na serialização - Registro corrompido");
        
        System.out.println("\n💡 SOLUÇÕES:");
        System.out.println("1. ✅ Use ResetSistema.java para limpar dados");
        System.out.println("2. ✅ Execute CriarDadosTeste.java para dados limpos");
        System.out.println("3. ✅ Verifique logs de erro durante criação");
        System.out.println("4. ✅ Use DebugSistema.java para monitorar");
        
        System.out.println("\n🚨 COMANDOS ÚTEIS:");
        System.out.println("Reset completo: java -cp target\\classes TP1.AEDS.III.ResetSistema");
        System.out.println("Criar dados: java -cp target\\classes TP1.AEDS.III.CriarDadosTeste");
        System.out.println("Debug: java -cp target\\classes TP1.AEDS.III.DebugSistema");
    }
}