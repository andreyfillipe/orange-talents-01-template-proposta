package br.com.zup.proposta.bloqueio.bloquear;

public class BloquearResponse {

    private BloquearStatus resultado;

    @Deprecated
    public BloquearResponse(){
    }

    public BloquearResponse(BloquearStatus resultado) {
        this.resultado = resultado;
    }

    public BloquearStatus getResultado() {
        return resultado;
    }
}
