package br.com.zup.proposta.bloqueio.bloquear;

import javax.validation.constraints.NotBlank;

public class BloquearRequest {

    @NotBlank
    private String sistemaResponsavel;

    public BloquearRequest(@NotBlank String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
