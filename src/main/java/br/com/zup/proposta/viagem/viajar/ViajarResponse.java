package br.com.zup.proposta.viagem.viajar;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ViajarResponse {

    private ViajarStatus resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ViajarResponse(ViajarStatus resultado) {
        this.resultado = resultado;
    }

    public ViajarStatus getResultado() {
        return resultado;
    }
}
