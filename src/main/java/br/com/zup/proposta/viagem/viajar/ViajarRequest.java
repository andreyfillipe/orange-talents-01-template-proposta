package br.com.zup.proposta.viagem.viajar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViajarRequest {

    @NotBlank
    private String destino;
    @NotNull
    private LocalDate validoAte;

    public ViajarRequest(@NotBlank String destino, @NotNull LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
