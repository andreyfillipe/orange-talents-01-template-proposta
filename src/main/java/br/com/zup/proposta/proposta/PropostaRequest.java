package br.com.zup.proposta.proposta;

import br.com.zup.proposta.config.validacao.beanValidation.CpfCnpj;
import br.com.zup.proposta.endereco.EnderecoRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank
    @CpfCnpj
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotNull
    private EnderecoRequest endereco;
    @NotNull
    @Positive
    private BigDecimal salario;

    public PropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotNull EnderecoRequest endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toProposta() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco.toEndereco(), this.salario);
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

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
