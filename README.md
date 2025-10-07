# TP-1-AEDS-III - Sistema de Gerenciamento de Clientes e Boletos

Este é um projeto desenvolvido para a disciplina de Algoritmos e Estruturas de Dados III, implementando um sistema completo de gerenciamento de clientes e boletos com persistência em arquivos e indexação hash extensível.

## 🚀 Características

### Funcionalidades Principais
- ✅ **CRUD Completo** para Clientes e Boletos
- ✅ **Persistência em Arquivos** usando RandomAccessFile
- ✅ **Indexação Hash Extensível** para performance O(1)
- ✅ **Interface Console** com menus interativos
- ✅ **Relacionamento Cliente-Boleto** (1:N)

### Tecnologias Utilizadas
- **Java 17** - Linguagem principal
- **Spring Boot** - Framework base
- **Maven** - Gerenciamento de dependências
- **RandomAccessFile** - Persistência customizada
- **Hash Extensível** - Estrutura de dados para indexação

## 📁 Estrutura do Projeto

```
src/main/java/TP1/AEDS/III/
├── Tp1AedsIiiApplication.java          # Aplicação principal
├── MenuPrincipal.java                  # Menu principal do sistema
├── DebugSistema.java                   # Utilitário de debug
├── CriarDadosTeste.java               # Criação de dados de teste
├── models/
│   ├── Cliente.java                    # Entidade Cliente
│   ├── Boleto.java                     # Entidade Boleto
│   ├── BoletoStatus.java              # Enum de status
│   ├── ClienteDAO.java                # DAO para clientes
│   ├── BoletoDAO.java                 # DAO para boletos
│   ├── MenuClientes.java              # Menu de clientes
│   ├── MenuBoletos.java               # Menu de boletos
│   ├── RegistroHashCliente.java       # Registro hash para clientes
│   └── RegistroHashBoleto.java        # Registro hash para boletos
└── repository/
    ├── Registro.java                   # Interface para serialização
    ├── ArquivoBD.java                 # Engine de banco de dados
    ├── HashExtensivel.java            # Implementação hash extensível
    └── RegistroHashExtensivel.java    # Interface para registros hash
```

## 🎯 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Comandos
```bash
# Compilar o projeto
mvn compile

# Executar aplicação principal
java -cp target\classes TP1.AEDS.III.Tp1AedsIiiApplication

# Criar dados de teste
java -cp target\classes TP1.AEDS.III.CriarDadosTeste

# Executar debug do sistema
java -cp target\classes TP1.AEDS.III.DebugSistema
```

## 🏗️ Arquitetura

### Persistência
- **ArquivoBD.java**: Engine genérica para CRUD em arquivos
- **Cabeçalho**: 12 bytes (4 int + 8 long) para metadados
- **Registros**: Serialização customizada via interface `Registro`

### Indexação Hash Extensível
- **Performance O(1)** para buscas por chave primária
- **Diretório dinâmico** que cresce conforme necessário
- **Cestos** com capacidade configurável
- **Rehashing automático** quando cestos ficam cheios

### Modelo de Dados

#### Cliente
- ID (int)
- Nome (String)
- CPF (String)
- Telefone (String)
- Endereço (String)
- Emails (List<String>)
- Salário (float)
- Data Nascimento (LocalDate)

#### Boleto
- ID (int)
- ID Cliente (int) - Foreign Key
- Descrição (String)
- Valor (BigDecimal)
- Data Vencimento (LocalDate)
- Status (BoletoStatus: PAGO/PENDENTE/CANCELADO)

## 🔧 Funcionalidades

### Menu Clientes
1. **Incluir Cliente** - Cadastro com validação
2. **Buscar Cliente** - Busca O(1) via hash
3. **Alterar Cliente** - Atualização com manutenção de índices
4. **Excluir Cliente** - Remoção lógica com limpeza de índices
5. **Listar Clientes** - Listagem completa

### Menu Boletos
1. **Incluir Boleto** - Cadastro vinculado a cliente
2. **Buscar Boleto** - Busca O(1) via hash
3. **Alterar Boleto** - Atualização com validações
4. **Excluir Boleto** - Remoção lógica
5. **Listar Boletos por Cliente** - Filtro por cliente

## 🚀 Performance

- **Busca por ID**: O(1) através de hash extensível
- **Inserção**: O(1) amortizado
- **Atualização**: O(1) para localização + escrita
- **Exclusão**: O(1) para localização + marcação

## 📊 Estrutura de Arquivos

```
./dados/
├── clientes/
│   └── clientes.db              # Dados dos clientes
├── boletos/
│   └── boletos.db               # Dados dos boletos
└── indices/
    ├── clientes_diretorio.hash_d # Diretório hash clientes
    ├── clientes_cestos.hash_c    # Cestos hash clientes
    ├── boletos_diretorio.hash_d  # Diretório hash boletos
    └── boletos_cestos.hash_c     # Cestos hash boletos
```

## 🧪 Testes

Execute `CriarDadosTeste.java` para gerar dados de exemplo e `DebugSistema.java` para verificar a integridade dos dados e performance do sistema.

## 👨‍💻 Autor

Desenvolvido para a disciplina de Algoritmos e Estruturas de Dados III.

## 📝 Licença

Projeto acadêmico - AEDS III