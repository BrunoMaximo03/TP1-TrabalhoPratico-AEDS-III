package TP1.AEDS.III.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import TP1.AEDS.III.repository.Registro;

public class Boleto implements Registro {

    private int id;
    private int idCliente;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private String descricao;
    private BigDecimal valor;
    private BoletoStatus status;

    public Boleto() {
        
    }

    public Boleto(int id, int idCliente, LocalDate dataEmissao, LocalDate dataVencimento, String descricao, BigDecimal valor, BoletoStatus status) {
        this.id = id;
        this.idCliente = idCliente;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    //setters
    public void setId(int id) { this.id = id; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }

    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public void setStatus(BoletoStatus status) { this.status = status; }

    //getters
    public int getId() { return id; }

    public int getIdCliente() { return idCliente; }

    public LocalDate getDataEmissao() { return dataEmissao; }

    public LocalDate getDataVencimento() { return dataVencimento; }

    public String getDescricao() { return descricao; }

    public BigDecimal getValor() { return valor; }

    public BoletoStatus getStatus() { return status; }

    // Métodos da interface Registro
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        dos.writeInt(id);
        dos.writeInt(idCliente);
        dos.writeUTF(descricao != null ? descricao : "");
        
        // Serializar valor BigDecimal
        if (valor != null) {
            dos.writeUTF(valor.toString());
        } else {
            dos.writeUTF("0");
        }
        
        // Serializar datas
        if (dataEmissao != null) {
            dos.writeLong(dataEmissao.toEpochDay());
        } else {
            dos.writeLong(0);
        }
        
        if (dataVencimento != null) {
            dos.writeLong(dataVencimento.toEpochDay());
        } else {
            dos.writeLong(0);
        }
        
        // Serializar status
        if (status != null) {
            dos.writeUTF(status.name());
        } else {
            dos.writeUTF("PENDENTE");
        }
        
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        
        this.id = dis.readInt();
        this.idCliente = dis.readInt();
        this.descricao = dis.readUTF();
        
        // Deserializar valor BigDecimal
        String valorStr = dis.readUTF();
        this.valor = new BigDecimal(valorStr);
        
        // Deserializar datas
        long dataEmissaoEpoch = dis.readLong();
        if (dataEmissaoEpoch > 0) {
            this.dataEmissao = LocalDate.ofEpochDay(dataEmissaoEpoch);
        }
        
        long dataVencimentoEpoch = dis.readLong();
        if (dataVencimentoEpoch > 0) {
            this.dataVencimento = LocalDate.ofEpochDay(dataVencimentoEpoch);
        }
        
        // Deserializar status
        String statusStr = dis.readUTF();
        this.status = BoletoStatus.valueOf(statusStr);
    }

    @Override
    public String toString() {
        return "\nID........: " + this.id +
               "\nID Cliente: " + this.idCliente +
               "\nDescrição.: " + this.descricao +
               "\nValor.....: " + this.valor +
               "\nEmissão...: " + this.dataEmissao +
               "\nVencimento: " + this.dataVencimento +
               "\nStatus....: " + this.status;
    }

}
