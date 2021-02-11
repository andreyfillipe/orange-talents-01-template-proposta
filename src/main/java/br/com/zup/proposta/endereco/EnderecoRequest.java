package br.com.zup.proposta.endereco;

import javax.validation.constraints.NotBlank;

public class EnderecoRequest {

    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    @NotBlank
    private String bairro;
    private String complemento;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotBlank
    private String cep;

    public EnderecoRequest(@NotBlank String logradouro, @NotBlank String numero, @NotBlank String bairro, String complemento, @NotBlank String cidade, @NotBlank String estado, @NotBlank String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco toEndereco() {
        return new Endereco(this.logradouro, this.numero, this.bairro, this.complemento, this.cidade, this.estado, this.cep);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }
}
