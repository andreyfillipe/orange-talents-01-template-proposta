package br.com.zup.proposta.proposta;

import br.com.zup.proposta.endereco.EnderecoResponse;

import java.math.BigDecimal;

public class PropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private EnderecoResponse endereco;
    private BigDecimal salario;
    private PropostaStatus status;

    public PropostaResponse(String documento, String email, String nome, EnderecoResponse endereco, BigDecimal salario, PropostaStatus status) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = status;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public EnderecoResponse getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public PropostaStatus getStatus() {
        return status;
    }
}
