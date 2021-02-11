package br.com.zup.proposta.proposta.analisarproposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnalisarPropostaRequest {

    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotNull
    private Long idProposta;

    public AnalisarPropostaRequest(@NotBlank String documento, @NotBlank String nome, @NotNull Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
