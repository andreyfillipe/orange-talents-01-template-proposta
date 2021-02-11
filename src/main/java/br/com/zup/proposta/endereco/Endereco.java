package br.com.zup.proposta.endereco;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco {

    @NotBlank
    @Column(nullable = false)
    private String logradouro;
    @NotBlank
    @Column(nullable = false)
    private String numero;
    @NotBlank
    @Column(nullable = false)
    private String bairro;
    private String complemento;
    @NotBlank
    @Column(nullable = false)
    private String cidade;
    @NotBlank
    @Column(nullable = false)
    private String estado;
    @NotBlank
    @Column(nullable = false)
    private String cep;

    @Deprecated
    public Endereco(){
    }

    public Endereco(@NotBlank String logradouro, @NotBlank String numero, @NotBlank String bairro, @NotBlank String complemento, @NotBlank String cidade, @NotBlank @Size(min = 2, max = 2) String estado, @NotBlank @Size(min = 9, max = 9) String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public EnderecoResponse toEnderecoResponse() {
        return new EnderecoResponse(this.logradouro, this.numero, this.bairro, this.complemento, this.cidade, this.estado, this.cep);
    }
}
